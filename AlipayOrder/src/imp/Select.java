package imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import calf.sql.SQLMethod;

public class Select extends SQLMethod {
	@SuppressWarnings("unchecked")
	@Override
	protected String[] implement(String tableName, String[] columns,
			String[] keys,
			Map<String, String>... data) {
		int num = data.length;
		if (num == 0) {
			num = 1;
			data = new HashMap[] { null };
		}
		List<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			StringBuilder sql = new StringBuilder();
			String[] colText = getColText(false, true, columns, null,
					tableConfig.get(tableName));
			String[] keyText = getColText(true, false, keys, data[i],
					tableConfig.get(tableName));
			sql.append("select ");
			if (colText == null)
				colText = new String[]{"*"};
			sql.append(colText[0]);
			sql.append(" from ");
			sql.append(tableName);
			if (keyText != null && keyText.length != 0) {
				sql.append(" where ");
				sql.append(keyText[0]);
			}
			sqlList.add(sql.toString());
		}
		return sqlList.toArray(new String[0]);
	}
}
