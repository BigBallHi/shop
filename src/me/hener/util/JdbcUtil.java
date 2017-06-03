package me.hener.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtil {
	public static Connection getConnection(){
		 String url = "jdbc:mysql://localhost:3306/shop" ;    
	     String user = "root" ;   
	     String password = "****" ; 
	     Connection conn = null; 
	     try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			System.out.println("包有问题");
		}
	     try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("url不存在");
		}
		return conn;
	}
	
}
