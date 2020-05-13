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
	Scanner input;
	
	
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
		//Instantiate scanner
		input = new Scanner(System.in);
		
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
		
		//Close scanner and return user
		input.close();
		return user;
	}
	
	public User createUser() throws SQLException {
		//Instantiate scanner
		input = new Scanner(System.in);
				
		//Instance of user to return to program
		User user = new User();
		
		//Create a menu and display for login
		Menu loginMenu = new Menu("Sign up");
		loginMenu.Display();
		
		//Prompt and store the first name from user
		System.out.println("What is your first name?");
		user.setFirstname(input.nextLine());
		
		//Prompt and store the last name from user
		System.out.println("\nWhat is your last name?");
		user.setLastname(input.nextLine());
		
		//Prompt and store the username from user
		System.out.println("\nWhat is your username?");
		user.setUsername(input.nextLine());
		
		//Prompt and store the password from user
		System.out.println("\nWhat is your password");
		user.setPassword(input.nextLine());
		
		do {
		//Prompt and store the user type from user
		System.out.println("\nAre you a customer or employee?");
		user.setUserType(input.nextLine());
		
		//If user misspells inputs loops till correct
		if (!(user.getUserType().equalsIgnoreCase("Customer") || user.getUserType().equalsIgnoreCase("Employee"))) {
			System.out.println("Spelling Error!");
			user.setUserType(input.nextLine());
		}
		} while(!(user.getUserType().equalsIgnoreCase("Customer") || user.getUserType().equalsIgnoreCase("Employee")));
		
		//Insert into the database the new user
				String sql= "INSERT INTO USERS VALUES (USERS_SEQ.NEXTVAL,?,?,?,?,?)";
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setString(1, user.getFirstname());
				ps.setString(2, user.getLastname());
				ps.setString(3, user.getUsername());
				ps.setString(4, user.getPassword());
				if (user.getUserType().equalsIgnoreCase("Customer")) {
					ps.setInt(5, 1);
				} else {ps.setInt(5, 2);}				
				ps.executeUpdate();
		
				//Close scanner and return user
				input.close();
				return user;
	}
}
