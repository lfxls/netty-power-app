package calf.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import tangdi.context.Context;
//import tangdi.db.DBUtil;
import tangdi.engine.context.DB;
import tangdi.engine.context.Log;

public class SQLManager {
	private static Map<String, SQLMethod> methodMap = new HashMap<String, SQLMethod>();

	static {
		try {
			methodMap.put(
					"INSERT",
					(SQLMethod) Class.forName(
							"imp.Insert")
							.newInstance());
			methodMap.put(
					"DELETE",
					(SQLMethod) Class.forName(
							"imp.Delete")
							.newInstance());
			methodMap.put(
					"SELECT",
					(SQLMethod) Class.forName(
							"imp.Select")
							.newInstance());
			methodMap.put(
					"UPDATE",
					(SQLMethod) Class.forName(
							"imp.Update")
							.newInstance());
		} catch (Exception e) {
			Log.error(e, "SQL语句生成类不存在");
		}
	}

	public static String[] getSQLText(String method, String tableName,
			String[] columns, String[] keys, Map<String, String>... data) {
		String[] sqls = methodMap.get(method.toUpperCase()).getSQLText(
				tableName, columns, keys, data);
		Log.info(Arrays.asList(sqls).toString());
		if (sqls.length == 0)
			return null;
		return sqls;
	}

	/**
	 * 多条语句的执行
	 * 
	 * @param sqlMap
	 * @return
	 */
	public static int submit(String... sql) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			// conn =
			// ((DBUtil)Context.getInstance(DBUtil.class)).getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (int i = 0; i < sql.length; i++) {
				stmt.addBatch(sql[i]);
				if (i % 1000 == 0)
					stmt.executeBatch();
			}
			int[] rst = stmt.executeBatch();
			for (int i = 0; i < rst.length; i++) {
				if (rst[i] <= 0)
					return -4;
			}

			conn.commit();
			return 0;
		} catch (SQLException e) {
			int rst = -1;
			SQLException se = e;
			while (se != null) {
				Log.error(se, "执行sql[%s]时发生异常", sql);
				if (se.getMessage().contains("ORA-00001"))
					rst = -3;
				se = e.getNextException();
			}
			return rst;
		} catch (Throwable t) {
			Log.error(t, "执行sql[%s]时发生异常", sql.toString());
			return -1;
		} finally {
			try {
				conn.rollback();
			} catch (SQLException e) {
				Log.error(e, "发生异常后，回滚失败。", new Object[0]);
			}
			DB.close(stmt, rs);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String>[] getSelect(String sql) {
		List<Map<String, String>> ans = new ArrayList<Map<String, String>>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			// conn =
			// ((DBUtil)Context.getInstance(DBUtil.class)).getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();

			for (; rs.next();) {
				HashMap<String, String> dataLine = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					String value = rs.getString(i);
					if (value == null)
						continue;
					dataLine.put(rsmd.getColumnName(i), value.trim());
				}
				ans.add(dataLine);
			}
		} catch (Throwable t) {
			Log.error(t, "执行sql[%s]时发生异常", sql.toString());
			return null;
		} finally {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 DB.close(stmt, rs);
		}
		return ans.toArray(new HashMap[0]);
	}
}
