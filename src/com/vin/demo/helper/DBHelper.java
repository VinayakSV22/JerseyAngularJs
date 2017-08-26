package com.vin.demo.helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

	private static DBHelper INSTANCE;

	public static synchronized DBHelper getI(){
		if(INSTANCE == null)
			INSTANCE = new DBHelper();
		
		return INSTANCE;
	}
	
	public DBHelper(){
		
	}
	
	public Connection createConnection(){
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/simpledemo","root","vinayak");
			connection.setAutoCommit(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return connection;
	}
}
