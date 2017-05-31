package imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import calf.sql.SQLMethod;

public class Insert extends SQLMethod {
	@Override
	protected String[] implement(String tableName, String[] columns, String[] keys, Map<String, String>... data) {
		int num = data.length;
		List<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			StringBuilder sql = new StringBuilder();
			String[] colText = getColText(false, true, columns, data[i], tableConfig.get(tableName));
			sql.append("insert into ");
			sql.append(tableName);
			sql.append(" (");
			sql.append(colText[0]);
			sql.append(") values (");
			sql.append(colText[1]);
			sql.append(")");
			sqlList.add(sql.toString());
		}
		return sqlList.toArray(new String[0]);
	}
}
