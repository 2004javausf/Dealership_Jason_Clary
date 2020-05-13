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
		main = new Menu("Welcome to the Dealership", "Log in", "Sign up", "exit");
		main.Display();
		input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");		
		MainSelector(input);

		//Checks if the menu should display to a customer or employee
		if (user.getUserType() .equalsIgnoreCase("Customer")) {
			main = new Menu("Customer Menu", "view cars in lot", "view cars in garage", "exit");
			
			//Put menu to console
			System.out.println("\n\n\n");
			main.Display();
			input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
			CustomerSelector(input);
		}
		else if(user.getUserType() .equalsIgnoreCase("Employee")) {
			main = new Menu("Employee Menu", "add a car to the car lot", "remove a car to the car lot", "approve/deny offers", "exit");
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
		case 3:
			System.exit(0);
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
				
				//Splits the lot arraylist for the menu
				//String[] carLot = MenuItemConverter(lot);
				
				//Display Menu
				main = new Menu("Car Lot", lot);
				main.AddMenuItem("exit to main menu");
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
					main = new Menu("Customer Menu", "view cars in lot", "view cars in garage", "exit");
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
					//Stores the down payment
					double amount;
					
					System.out.println("\n" + "\u001B[36m" +lot.get(input - 1)+ "\u001B[0m");
					System.out.println("How much would you like to pay in a down payment?");
					amount = Validate.CheckDouble(sc.nextLine(), "Please use dollar format");
					
					//If user tries to put in amount greater than price, return to customer menu
					if (amount > lot.get(input - 1).getPrice() || amount < 0) {
						System.out.println("\n\n\nNice try...");
						main = new Menu("Customer Menu", "view cars in lot", "view cars in garage", "exit");
						main.Display();
						input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
						CustomerSelector(input);
					}
					//Otherwise prompt the user for their payment plan
					else {
						main = new Menu("Payment Plans", "12 month", "36 month", "60 month");
						System.out.println("\n");
						main.Display();
						//Get the users desired plan
						int plan = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
						int payments = PlanSelector(plan);
						
						//Submit the offer to the database
						imp.insertOffer(user.getId(), lot.get(input - 1).getId(), amount, payments);
						
						//Loop to main menu
						System.out.println("\n\n\nSubmitted Offer!");
						main = new Menu("Customer Menu", "view cars in lot", "view cars in garage", "exit");
						main.Display();
						input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
						CustomerSelector(input);
					}
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
        case 3:
			System.exit(0);
			break;

		default:
			break;
		}
	}
	
	//Selects the users payment plan and returns it
	public static int PlanSelector(int input) {
		switch (input) {
		case 1:
			return 12;
        case 2:
        	return 36;
        case 3:
        	return 60;
		default:
			System.out.println("\nPlease select a valid option");
			main.Display();
			int plan = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");			
			return PlanSelector(plan);
		}
	}
	

}
