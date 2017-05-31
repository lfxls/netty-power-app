package imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import calf.sql.SQLMethod;

public class Delete extends SQLMethod {
	@Override
	protected String[] implement(String tableName, String[] columns, String[] keys, Map<String, String>... data) {
		int num = data.length;
		List<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			StringBuilder sql = new StringBuilder();
			String[] keyText = getColText(true, false, keys, data[i], tableConfig.get(tableName));
			if (keyText == null || keyText.length == 0)
				throw new RuntimeException("delete语句没有where条件");
			sql.append("delete from ");
			sql.append(tableName);
			sql.append(" where ");
			sql.append(keyText[0]);
			sqlList.add(sql.toString());
		}
		return sqlList.toArray(new String[0]);
	}
}
