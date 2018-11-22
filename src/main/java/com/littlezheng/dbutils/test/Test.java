package com.littlezheng.dbutils.test;

import java.sql.SQLException;

import com.littlezheng.dbutils.JDBCUtils;
import com.littlezheng.dbutils.Query;
import com.littlezheng.dbutils.handler.BeanHandler;

public class Test {
	
	private static final String URL = "jdbc:mysql://localhost:3306/test2?useUnicode=true&characterEncoding=UTF8";
	private static final String USER = "xpzheng";
	private static final String PASSWORD = "123456";

	public static void main(String[] args) throws SQLException {
		Query query = new Query(JDBCUtils.getConnection(URL, USER, PASSWORD));
		/*Map<String, Object> result = query.select("SELECT name '姓名', age FROM T_Person WHERE id = 3", new MapHandler());
		List<Map<String, Object>> results = query.select("SELECT * FROM T_Person", new MapsHandler());
		System.out.println(result);
		System.out.println(results);*/
		
		Person person = query.select("SELECT * FROM T_Person", new BeanHandler<Person>(Person.class));
		System.out.println(person);
	}
	
}
