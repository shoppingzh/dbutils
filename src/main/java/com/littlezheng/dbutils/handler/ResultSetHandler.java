package com.littlezheng.dbutils.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {

	/**
	 * 处理ResultSet, 返回对象
	 * 
	 * @param rs
	 * @return 当结果集没有内容时返回null
	 * @throws SQLException
	 */
	T handle(ResultSet rs) throws SQLException;
	
}
