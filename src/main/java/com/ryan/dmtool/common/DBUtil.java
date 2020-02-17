package com.ryan.dmtool.common;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Slf4j
public class ConnectionUtil {
	private static String JDBC_Driver;
	private static String JDBC_Url;
	private static String JDBC_Username;
	private static String JDBC_Password;

	public static Connection getConnection(){

		Connection conn = null;
		try {
			loadJDBC("oracle");
			Class.forName(JDBC_Driver);
			conn = DriverManager.getConnection(JDBC_Url,JDBC_Username,JDBC_Password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return conn;
	}

	private static void loadJDBC(String DBType) throws IOException {
		DBType = "jdbc." + DBType.toLowerCase();
		InputStream in = ConnectionUtil.class.getResourceAsStream(
				"/jdbc.properties");
		Properties propers = new Properties();
		propers.load(in);
		JDBC_Driver = propers.getProperty(DBType + ".class");
		JDBC_Url = propers.getProperty(DBType + ".url");
		JDBC_Username = propers.getProperty(DBType + ".uesrname");
		JDBC_Password = propers.getProperty(DBType + ".password");
	}
}
