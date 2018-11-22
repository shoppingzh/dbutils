package com.littlezheng.dbutils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.littlezheng.dbutils.handler.MapHandler;
import com.littlezheng.dbutils.handler.MapsHandler;

public class Test {

	public static void main(String[] args) throws SQLException {
		Query query = new Query(JDBCUtils.getConnection());
		Map<String, Object> result = query.select("SELECT name '姓名', age FROM T_Person WHERE id = 3", new MapHandler());
		List<Map<String, Object>> results = query.select("SELECT * FROM T_Person", new MapsHandler());
		System.out.println(result);
		System.out.println(results);
	}
	
}
