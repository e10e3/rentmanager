package com.epf.rentmanager.persistence;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String DB_CONNECTION = "jdbc:h2:~/RentManagerDatabase";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	private static JdbcDataSource datasource = null;

	private static void init() {
		if (datasource == null) {
			datasource = new JdbcDataSource();
			datasource.setURL(DB_CONNECTION);
			datasource.setUser(DB_USER);
			datasource.setPassword(DB_PASSWORD);
		}
	}

	public static Connection getConnection() throws SQLException {
		init();
		return datasource.getConnection();
	}

}