package com.littlezheng.dbutils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JDBCUtils {

	private static String URL;
	private static String USER;
	private static String PASSWORD;
	
	static {
		try (InputStream in = JDBCUtils.class.getClassLoader()
				.getResourceAsStream("jdbc.properties")) {
			if(in != null){
				Properties prop = new Properties();
				prop.load(in);
				URL = prop.getProperty("jdbcUrl");
				USER = prop.getProperty("user");
				PASSWORD = prop.getProperty("password");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getLines(ResultSet rs)
			throws SQLException {
		if (rs == null) {
			return Collections.EMPTY_LIST;
		}
		List<Map<String, Object>> lines = new ArrayList<Map<String, Object>>();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> lineMap = new HashMap<String, Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				lineMap.put(metaData.getColumnLabel(columnIndex),
						rs.getObject(columnIndex));
			}
			lines.add(lineMap);
		}
		return lines;
	}
	
	public static List<Object> getFirstLines(ResultSet rs) throws SQLException{
		return getLines(rs, 1);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> getLines(ResultSet rs, int columnIndex) throws SQLException{
		if (rs == null) {
			return Collections.EMPTY_LIST;
		}
		
		List<Object> lines = new ArrayList<Object>();
		while(rs.next()){
			Object val = rs.getObject(columnIndex);
			lines.add(val);
		}
		return lines;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> getLines(ResultSet rs, String columnLabel) throws SQLException{
		if (rs == null) {
			return Collections.EMPTY_LIST;
		}
		
		List<Object> lines = new ArrayList<Object>();
		while(rs.next()){
			Object val = rs.getObject(columnLabel);
			lines.add(val);
		}
		return lines;
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	public static Connection getConnection(String url, String user, String password) throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}
	
	public static void closeQuietly(AutoCloseable resourse){
		if(resourse != null){
			try {
				resourse.close();
			} catch (Exception e) {
				// Quiet
				e.printStackTrace();
			}
		}
	}
	
}
