package com.littlezheng.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.littlezheng.dbutils.handler.ResultSetHandler;

public class Query {

	protected final Connection connection;
	
	public Query(Connection connection) {
		this.connection = connection;
	}

	public <T> T select(String sql, ResultSetHandler<T> rsh) throws SQLException{
		PreparedStatement ps = connection.prepareStatement(sql);
		return rsh.handle(ps.executeQuery());
	}
	
}
