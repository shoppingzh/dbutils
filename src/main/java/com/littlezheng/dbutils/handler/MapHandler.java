package com.littlezheng.dbutils.handler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapHandler implements ResultSetHandler<Map<String, Object>> {

	@Override
	public Map<String, Object> handle(ResultSet rs) throws SQLException {
		if (rs == null || !rs.next()) {
			return null;
		}

		ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount();
		if (cols <= 0) {
			return null;
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		for (int i = 1; i <= cols; i++) {
			String name = metaData.getColumnLabel(i);
			if (name == null || name.isEmpty()) {
				name = metaData.getColumnName(i);
			}

			Object value = rs.getObject(i);
			result.put(name, value);
		}

		return result;
	}

}
