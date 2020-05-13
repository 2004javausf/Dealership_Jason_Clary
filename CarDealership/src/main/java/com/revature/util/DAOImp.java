package com.revature.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.revature.beans.User;

public class DAOImp {
	//Global Variable for Connection
	public static ConnFactory cf=ConnFactory.getInstance();
	Connection conn= cf.getConnection();
	//Global scanner for input
	Scanner input = new Scanner(System.in);
	
	
//	public List<Student> getStudentList() throws SQLException {
//		List<Student> studentList= new ArrayList<Student>();
//		Connection conn=cf.getConnection();
//		Statement stmt = conn.createStatement();
//		ResultSet rs=stmt.executeQuery("SELECT * FROM STUDENT");
//		Student s=null;
//		while(rs.next()) {
//			s= new Student(rs.getInt(1),rs.getString(2),rs.getInt(3));
//			studentList.add(s);
//		}
//		return studentList;
//	}
	
	public void createPowers(String powerName) throws SQLException {
	//Prepared Statement- pre compiled - compiled in Java/ Statement is complied in sql
		
		String sql= "INSERT INTO POWERS VALUES (POWSEQ.NEXTVAL,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, powerName);
		ps.executeUpdate();
	}
	
	public User login() throws SQLException {
		//Instance of user to return to program
		User user = new User();
		
		//Create a menu and display for login
		Menu loginMenu = new Menu("Login");
		loginMenu.Display();
		
		//Prompt and store the username from user
		System.out.println("What is your username?");
		String username = input.nextLine();
		
		//Prompt and store the username from user
		System.out.println("What is your username?");
		String password = input.nextLine();
		
		//Check database for user and correct credentials
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT USERS.ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, TYPE "
				                     + "FROM USERS "
				                     + "JOIN USERTYPE ON usertype.id = users.usertype_id "
				                     + "WHERE USERNAME = " + username + " AND PASSWORD = " + password);
		while(rs.next()) {
			user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));

		}
		
		if (user.getUsername().equalsIgnoreCase("")) {
			return null;
		}
		else {return user;}
	}
}
