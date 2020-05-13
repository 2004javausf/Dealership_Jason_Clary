package com.revature.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.Brand;
import com.revature.beans.Offer;
import com.revature.beans.User;
import com.revature.beans.Vehicle;

public class DAOImp {
	//Global Variable for Connection
	public static ConnFactory cf=ConnFactory.getInstance();
	Connection conn= cf.getConnection();
	//Global scanner for input
	Scanner input;
	//Global menu
	Menu menu;
	
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
	
//------------------------LOGIN METHODS------------------------
	public User login() throws SQLException {
		//Instantiate scanner
		input = new Scanner(System.in);
		
		//Instance of user to return to program
		User user = new User();
		
		//Create a menu and display for login
		menu = new Menu("Login");
		menu.Display();
		
		//Prompt and store the username from user
		System.out.println("What is your username?");
		String username = input.nextLine();
		
		//Prompt and store the username from user
		System.out.println("\nWhat is your password?");
		String password = input.nextLine();
		
		//Check database for user and correct credentials
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT USERS.ID, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, TYPE "
				                     + "FROM USERS "
				                     + "JOIN USERTYPE ON usertype.id = users.usertype_id "
				                     + "WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'");
		while(rs.next()) {
			user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
		}	
		
		//Close scanner and return user
		//input.close();
		return user;
	}
	
	public User createUser() throws SQLException {
		//Instantiate scanner
		input = new Scanner(System.in);
				
		//Instance of user to return to program
		User user = new User();
		
		//Create a menu and display for login
		menu = new Menu("Sign up");
		menu.Display();
		
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
				//input.close();
				return user;
	}
//-------------------------------------------------------------
	
//-----------------------CAR VIEW METHODS----------------------
	public List<Vehicle> viewCars() throws SQLException {
		//Stored cars to return 
		List<Vehicle> cars= new ArrayList<Vehicle>();
		Vehicle car;
		
		//Pulls only available cars from the lot
		Connection conn=cf.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT VEHICLES.ID, YEAR, MAKE, MODEL, COLOR, PRICE, AVAILABILITY "
				                     + "FROM VEHICLES "
				                     + "JOIN VEHICLEBRAND ON vehiclebrand.id = vehicles.brand_id "
				                     + "JOIN STATUS ON status.id = vehicles.status_id "
				                     + "WHERE AVAILABILITY = 'Available'");
		
		while(rs.next()) {
			car= new Vehicle(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6),rs.getString(7));
			cars.add(car);
		}
		return cars;
	}
	
	public void viewGarage(User user) throws SQLException {
		//Stored cars to return 
		Offer car = new Offer();
				
		//Pulls only available cars from the lot
		Connection conn=cf.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT FIRSTNAME, LASTNAME, COLOR, YEAR, "
						             + "(SELECT MAKE FROM VEHICLES JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id WHERE VEHICLES.ID = offers.vehicles_id) AS MAKE, "
						             + "(SELECT MODEL FROM VEHICLES JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id WHERE VEHICLES.ID = offers.vehicles_id) AS MODEL, "
						             + "PRICE, DOWNPAYMENT, PAYMENTS, ROUND(((PRICE - DOWNPAYMENT) / PAYMENTS),2) AS PAYAMOUNT "
						             + "FROM OFFERS "
						             + "JOIN users ON USERS.ID = offers.users_id "
						             + "JOIN VEHICLES ON VEHICLES.ID = offers.vehicles_id "
						             + "WHERE USERS.USERNAME = '"+user.getUsername()+"' "
						             + "AND (SELECT AVAILABILITY FROM VEHICLES " 
						             + "JOIN STATUS ON STATUS.ID = VEHICLES.STATUS_ID " 
						             + "WHERE VEHICLES.ID = offers.vehicles_id) = 'Not Available'");
				
		while(rs.next()) {			
			car= new Offer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),
					         rs.getString(6),rs.getDouble(7),rs.getDouble(8),rs.getInt(9),rs.getDouble(10));
			car.Offers.add(car);
		}
		
		System.out.println(car.Offers);
		//Create menu to display the offers
		Menu menu = new Menu("Garage", car.Offers);
		menu.Display();	
	}
	
	public void viewOffers() throws SQLException {
		//Stored cars to return 
		Offer car;
				
		//Pulls only available cars from the lot
		Connection conn=cf.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT FIRSTNAME, LASTNAME, COLOR, YEAR, "
						             + "(SELECT MAKE FROM VEHICLES JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id WHERE VEHICLES.ID = offers.vehicles_id) AS MAKE, "
						             + "(SELECT MODEL FROM VEHICLES JOIN vehiclebrand ON vehiclebrand.id = vehicles.brand_id WHERE VEHICLES.ID = offers.vehicles_id) AS MODEL, "
						             + "PRICE, DOWNPAYMENT, PAYMENTS, ROUND(((PRICE - DOWNPAYMENT) / PAYMENTS),2) AS PAYAMOUNT, USERS_ID, VEHICLES_ID "
						             + "FROM OFFERS "
						             + "JOIN users ON USERS.ID = offers.users_id "
						             + "JOIN VEHICLES ON VEHICLES.ID = offers.vehicles_id");
				
		while(rs.next()) {			
			car= new Offer(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),
					         rs.getString(6),rs.getDouble(7),rs.getDouble(8),rs.getInt(9),rs.getDouble(10));
			car.setUserID(rs.getInt(11));
			car.setVehiclesID(rs.getInt(12));
			Offer.Offers.add(car);
		}
		
		//Create menu to display the offers
		Menu menu = new Menu("Offers", Offer.Offers);
		menu.Display();	
	}
//-------------------------------------------------------------
	
//----------------------INSERTION METHODS----------------------
	public void insertOffer(int userID, int vehID, double downPay, int payments) throws SQLException {
		//Insert into the database the new user
		String sql= "INSERT INTO OFFERS VALUES (?,?,?,?)";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1, userID);
		ps.setInt(2, vehID);
		ps.setDouble(3, downPay);
		ps.setInt(4, payments);			
		ps.executeUpdate();
	}
	
	public void insertVehicle(int year, int brandID, String color, double price) throws SQLException {
		//Insert into the database the new user
		String sql= "INSERT INTO VEHICLES VALUES (VEHICLES_SEQ.NEXTVAL + 5,?,?,?,?,1)";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setString(1, "" + year);
		ps.setInt(2, brandID);
		ps.setString(3, color);
		ps.setDouble(4, price);
		ps.executeUpdate();
	}
//-------------------------------------------------------------
	
//-----------------------DELETION METHODS----------------------
	public void deleteCar(int carID) throws SQLException{
		//Delete car from database
				String sql= "DELETE FROM VEHICLES WHERE ID = ?";
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setInt(1, carID);		
				ps.executeUpdate();
	}
	
	public void rejectOtherOffers(int userID, int carID) throws SQLException {
		String sql= "{ call RejectOtherPendingOffers(?,?)";
		CallableStatement call=conn.prepareCall(sql);
		call.setInt(1, userID);
		call.setInt(2, carID);
		call.execute();
	}
	
	public void rejectOffer(int userID, int carID) throws SQLException {
		//Delete offer from database
		String sql= "DELETE FROM OFFERS WHERE USERS_ID = ? AND VEHICLES_ID = ?";
		PreparedStatement ps=conn.prepareStatement(sql);
		ps.setInt(1, userID);	
		ps.setInt(2, carID);
		ps.executeUpdate();
	}
//-------------------------------------------------------------
	
//------------------------BRAND METHODS------------------------
	public void getMakes() throws SQLException {
		//Clear the list for each iteration
				Brand.Makes.clear();
				
				//Pulls only available cars from the lot
				Connection conn=cf.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs=stmt.executeQuery("SELECT UNIQUE MAKE "
						                     + "FROM VEHICLEBRAND");
						
				while(rs.next()) {			
					Brand.Makes.add(rs.getString(1));
				}
				
				//Create menu to display the offers
				Menu menu = new Menu("Makes", Brand.Makes);
				menu.Display();	
	}
	
	public void getModels(String make) throws SQLException {
		//Clear the list for each iteration
		Brand.Models.clear();
		
		//Pulls only available cars from the lot
		Connection conn=cf.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT ID, MODEL "
				                     + "FROM VEHICLEBRAND "
				                     + "WHERE MAKE = '"+make+"'");
				
		while(rs.next()) {
			Brand.IDs.add(rs.getInt(1));
			Brand.Models.add(rs.getString(2));
		}
		
		//Create menu to display the offers
		Menu menu = new Menu("Models", Brand.Models);
		menu.Display();	
}
//-------------------------------------------------------------
}
