package com.revature.driver;

import java.sql.SQLException;
import java.util.Scanner;

import com.revature.beans.User;
import com.revature.util.DAOImp;
import com.revature.util.Menu;
import com.revature.util.Validate;

public class Driver {
//Global Fields-----
static User user;
static Scanner sc = new Scanner(System.in);
static int input;
//Menus
static Menu main;
//SQL 
static DAOImp imp = new DAOImp();

	public static void main(String[] args) {
		//Initial sign in options
		main = new Menu("Welcome to the Dealership", "Log in", "Sign up");
		main.Display();
		input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
		MainSelector(input);
		
		
		if (user.getUserType() .equalsIgnoreCase("Customer")) {
			main = new Menu("");
		}
		else if(user.getUserType() .equalsIgnoreCase("Employee")) {
			main = new Menu("");
		}
		else {System.out.println("Don't know how you got here bud.");}
		
	}

	//Menus Selectors
	public static void MainSelector(int input) {
		switch (input) {
		case 1:
			try {
				//Upon successful login will store user in an object
				user = imp.login();
				System.out.println(user.toString());
			} catch (SQLException e) {
				//If log in failed prompt the user and rerun from beginning
				System.out.println("\n\n\nUsername/Password were incorrect");
				main.Display();
				input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				MainSelector(input);
			}
			break;
		case 2:
			try {
				//Upon successful login will store user in an object
				user = imp.createUser();
				System.out.println(user.toString());
			} catch (SQLException e) {
				//If log in failed prompt the user and rerun from beginning
				System.out.println("\n\n\nUsername already exists");
				main.Display();
				input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				MainSelector(input);
			}
			break;

		default:
			System.out.println("\n\n\nPlease select a valid option");
			main.Display();
			input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
			MainSelector(input);
			break;
		}
	}
}
