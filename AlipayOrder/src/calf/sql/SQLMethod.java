package calf.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import tangdi.context.Context;
import tangdi.db.DBUtil;
import tangdi.engine.context.Log;

public abstract class SQLMethod {
	/** key: table name, value: [key:colName, value: column type] */
	protected static Map<String, Map<String, Integer>> tableConfig = new ConcurrentHashMap<String, Map<String, Integer>>();

	public SQLMethod() {
	}

	public static String[] getCols(String tableName) {
		if (!tableConfig.containsKey(tableName.toUpperCase())) {
			try {
				addTableInfo(tableName.toUpperCase(),
						((DBUtil) Context.getInstance(DBUtil.class))
								.getConnection());
			} catch (SQLException e) {
				Log.error(e, "获取[%s]表结构失败", tableName);
			}
		}
		Map<String, Integer> cfg = tableConfig.get(tableName.toUpperCase());
		return cfg.keySet().toArray(new String[0]);
	}

	public String[] getSQLText(String tableName, String[] columns,
			String[] keys, Map<String, String>... data) {
		if (!tableConfig.containsKey(tableName.toUpperCase())) {
			try {
				addTableInfo(tableName.toUpperCase(),
						((DBUtil) Context.getInstance(DBUtil.class))
								.getConnection());
			} catch (SQLException e) {
				Log.error(e, "获取[%s]表结构失败", tableName);
			}
		}
		String[] ans = null;
		try {
			ans = implement(tableName, columns, keys, data);
		} catch (Throwable t) {
			Log.error(t, tableName + "表语句生成失败！");
		}
		return ans;
	}

	protected abstract String[] implement(String tableName, String[] columns,
			String[] keys, Map<String, String>... data);

	/**
	 * 把表的列名信息保存到tableConfig中
	 * 
	 * @param tableName
	 *            表名
	 * @param conn
	 *            数据库连接
	 * @return 是否成功添加到 tableConfig中
	 * @throws SQLException
	 */
	// ！失败的处理，健壮性策略
	private static boolean addTableInfo(String tableName, Connection conn)
			throws SQLException {
		boolean b = false;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ");
			sql.append(tableName);
			sql.append(" where 1=2");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());

			ResultSetMetaData rsmd = rs.getMetaData();
			int colcount = rsmd.getColumnCount();
			Map<String, Integer> t = new HashMap<String, Integer>();
			for (int i = 1; i <= colcount; i++) {
				t.put(rsmd.getColumnName(i), rsmd.getColumnType(i));
			}
			tableConfig.put(tableName, t);
			b = true;
		} finally {
			// DB.close(stmt, rs);
			rs.close();
			stmt.close();
		}
		return b;
	}

	/**
	 * 
	 * @param key
	 *            是否where子句
	 * @param detach
	 *            是否键值分离
	 * @param colNames
	 * @param data
	 * @param colTypes
	 * @return
	 */
	protected static String[] getColText(boolean key, boolean detach,
			String[] colNames, Map<String, String> data,
			Map<String, Integer> colTypes) {
		if (colNames == null || colNames.length == 0)
			return null;
		StringBuilder colText = new StringBuilder();
		StringBuilder colValue = null;
		if (detach && data != null) {
			colValue = new StringBuilder();
		}
		if (colNames.length == 1 && colNames[0].equals("*"))
			return new String[] { "*" };
		for (String colName : colNames) 
			if (detach && data == null) {
				colText.append(colName);
				colText.append(", ");
				// continue;
			}
			else {
				String value = getColValue(key, colName, data, colTypes);
				if (value == null)
					continue;
				colText.append(colName);
				if (detach) {
					colValue.append(value);
					colValue.append(", ");
					colText.append(", ");
				} else if (!key) {
					colText.append("=");
					colText.append(value);
					colText.append(", ");
				} else {
					colText.append(" ");
					colText.append(value);
					colText.append(" and ");
				}
			}
		
		if (detach && data != null) {
			colValue.delete(colValue.length() - 2, colValue.length());
			colText.delete(colText.length() - 2, colText.length());
			return new String[] { colText.toString(), colValue.toString() };
		}
		if (!key)
			colText.delete(colText.length() - 2, colText.length());
		else
			colText.delete(colText.length() - 5, colText.length());
		return new String[] { colText.toString() };
	}

	private static String getColValue(boolean key, String colName,
			Map<String, String> data, Map<String, Integer> colTypes) {
		if (!colTypes.containsKey(colName))
			throw new RuntimeException("该表不含有字段：" + colName);
		int type = colTypes.get(colName.toUpperCase());
		String value = data.get(colName);
		if (value == null || value.trim().length() == 0)
			value = null;
		else
			value = value.trim();
		String dfn = data.get("DFN_" + colName);
		if (dfn == null || dfn.trim().length() == 0)
			dfn = null;
		else
			dfn = dfn.trim();

		if (type == Types.BOOLEAN || type == Types.INTEGER
				|| type == Types.DOUBLE || type == Types.FLOAT
				|| type == Types.BIGINT || type == Types.DECIMAL
				|| type == Types.NUMERIC) {
			 Log.info("测试 value=%s", new Object[] { value });
			if (!isNumeric(value))
				throw new RuntimeException(colName + ":数字类型数据错误:" + value);
			if (key) {
				if (value != null)
					return "=" + data.get(colName);
				return dfn;
			}
			return value;
		}
		// for Oracle
		if (type == Types.DATE || type == Types.TIMESTAMP) {
			if (key) {
				if (value != null)
					return "='" + value + "'";
				return dfn;
			}
			return value != null ? "'" + value + "'" : null;
		}

		value = toSafeStr(value);
		if (key) {
			if (value != null)
				return "='" + value + "'";
			return dfn;
		} else {
			if (value == null)
				return null;
			return "'" + value + "'";
		}
	}

	private static boolean isNumeric(String s) {
		if (s == null)
			return true;
		if (s.equals("NULL") || s.endsWith(".NEXTVAL") || (s.endsWith(".sqlSequence()"))) {
			return true;
		}
		int i = 1;
		int len = s.length();
		char firstChar = s.charAt(0);
		if (firstChar < '0') { // Possible leading "+" or "-"
			if (firstChar != '-' && firstChar != '+')
				return false;

			if (len == 1) // Cannot have lone "+" or "-"
				return false;
			i++;
		}
		while (i < len) {
			if (s.charAt(i) < '0' || s.charAt(i) > '9')
				return false;
			i++;
		}
		return true;
	}

	private static String toSafeStr(String value) {
		if (value == null)
			return null;
		value.replace("'", "''");
		return value;
	}
}
