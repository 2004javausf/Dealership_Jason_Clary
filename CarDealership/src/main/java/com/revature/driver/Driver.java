package com.revature.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.User;
import com.revature.beans.Vehicle;
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
//Lists
static List<Vehicle> lot = new ArrayList<Vehicle>();

	public static void main(String[] args) {
		//Initial sign in options
		main = new Menu("Welcome to the Dealership", "Log in", "Sign up");
		main.Display();
		input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
		MainSelector(input);
		
		//Checks if the menu should display to a customer or employee
		if (user.getUserType() .equalsIgnoreCase("Customer")) {
			main = new Menu("Customer Menu", "view cars in lot", "view cars in garage");
		}
		else if(user.getUserType() .equalsIgnoreCase("Employee")) {
			main = new Menu("Employee Menu", "add a car to the car lot", "remove a car to the car lot", "approve/deny offers");
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
	
	public static void CustomerSelector(int input) {
		switch (input) {
		case 1:
			try {
				System.out.println("\n\n\n");
				//Retrieve the cars to lot and present to customer
				lot = imp.viewCars();
				main = new Menu("Car Lot", lot.toString(), "exit to main menu");				
				main.Display();
				
				//Prompt customer to make an offer on a car or exit lot
				System.out.println("Would you like to make an offer on a vehicle? (or type 'exit' to return to the main menu)");
				//Inputs to check whether customer typed quit or not
				String userInput = sc.nextLine();
				input= Validate.CheckInt(userInput, "Please use a whole number format");
				
				do {
				//If the user decides to quit, return to customer menu
				if (userInput.equalsIgnoreCase("exit")) {
					System.out.println("\n\n\n");
					main = new Menu("Customer Menu", "view cars in lot", "view cars in garage");
					main.Display();
					input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
					CustomerSelector(input);
				}	
				//If user chooses out of range of options, loop
				else if (input < 0 || input > lot.size()) {
					System.out.println("\nPlease enter a valid option");
					input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				}
				//If the user makes a correct choice prompt to make offer
				else {
					System.out.println();
				}
				} while(input < 0 || input > lot.size());
			} catch (SQLException e) {
				//If log in failed prompt the user and rerun from beginning
				System.out.println("\n\n\nCar lot is empty");
				main.Display();
				input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				CustomerSelector(input);
			}
			break;
        case 2:
			
			break;

		default:
			break;
		}
	}
	
	
}
