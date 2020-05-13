package com.revature.driver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.beans.Brand;
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
			main = new Menu("Employee Menu", "add a car to the car lot", "remove a car from the car lot", "approve/deny offers", "exit");
			
			//Put menu to console
			System.out.println("\n\n\n");
			main.Display();
			input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
			EmployeeSelector(input);
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
				} while(input < 1 || input > lot.size());
			} catch (SQLException e) {
				//If log in failed prompt the user and rerun from beginning
				System.out.println("\n\n\nCar lot is empty");
				main.Display();
				input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				CustomerSelector(input);
			}
			break;
        case 2:
        	try {
				imp.viewGarage(user);
				System.out.println("\nPress enter to go back to menu");
	    		sc.nextLine();
			} catch (SQLException e) {
				//If fails there are no cars in the users garage
				System.out.println("\n\n\n");
				main = new Menu("Customer Menu", "view cars in lot", "view cars in garage", "exit");
				main.Display();
				input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				CustomerSelector(input);
			}   	
    		
    		System.out.println("\n\n\n");
			main = new Menu("Customer Menu", "view cars in lot", "view cars in garage", "exit");
			main.Display();
			input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
			CustomerSelector(input);
			break;
        case 3:
			System.exit(0);
			break;

		default:
			System.out.println("\n\n\nPlease select a valid option");
			main = new Menu("Customer Menu", "view cars in lot", "view cars in garage", "exit");
			main.Display();
			input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
			CustomerSelector(input);
			break;
		}
	}

	public static void EmployeeSelector(int input) {
		switch (input) {
		case 1:
			//Brand Object to info
			Brand brand = new Brand();
			
			//Get the brand
			System.out.println("\n\n\n");
			try {
				imp.getMakes();
				
				System.out.println("\nSelect a make");
				input= Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				
				//If the user puts in faulty input will loop
				do {
				if (input < 1 || input > Brand.Makes.size()) {
					System.out.println("\nPlease enter a valid option");
					input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				}
				else {
					//If the input is correct will store brand in object and continue
					brand.setMake(Brand.Makes.get(input - 1));
					
					//Get the models
					System.out.println("\n\n\n");
					imp.getModels(brand.getMake());
					
					System.out.println("\nSelect a model");
					input= Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
					
					//If the user puts in faulty input will loop
					do {
						if (input < 1 || input > Brand.Models.size()) {
							System.out.println("\nPlease enter a valid option");
							input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
						}
						else {
							//If the input is correct will store brand in object and continue
							brand.setId(Brand.IDs.get(input - 1));
							brand.setModel(Brand.Makes.get(input - 1));
							
							//Get year of car
							System.out.println("\nWhat year is the vehicle/");
							int year = 0; 
							while (year < 1) {
								year = Validate.CheckInt(sc.nextLine(), "Please enter a whole number greater than 0");	
							}

							//Get color of car
							System.out.println("\nWhat color is the vehicle/");
							String color = sc.nextLine();
							
							//Get price of car
							System.out.println("\nWhat price is the vehicle/");
							double price = 0; 
							while (price < 1) {
								price = Validate.CheckDouble(sc.nextLine(), "Please enter a double greater than 0");	
							}
							
							//Insert the car into the database
							imp.insertVehicle(year, brand.getId(), color, price);
							
							//Return to the employee menu
							System.out.println("\n\n\nVehicle Created!");
							main = new Menu("Employee Menu", "add a car to the car lot", "remove a car from the car lot", "approve/deny offers", "exit");
							main.Display();
							input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
							EmployeeSelector(input);
						}
					} while(input < 1 || input > Brand.Models.size());
				}
				} while(input < 1 || input > Brand.Makes.size());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			//Get the cars
			
			System.out.println("\n\n\n");
			
			try {
				lot = imp.viewCars();
				
				//display the menu after getting the cars list
				main = new Menu("Car Lot", lot);
				main.Display();
				
				//Prompt employee which car to delete
				System.out.println("\n Which car would you like to remove?");
				input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				
				do {
					if (input < 1 || input > lot.size()) {
						System.out.println("\nPlease enter a valid option");
						input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
					}
					else {
					//Delete car if input is correct
					imp.deleteCar(lot.get(input - 1).getId());
					
					//Loop back to the main menu
					System.out.println("\n\n\nVehicle was removed");
					main = new Menu("Employee Menu", "add a car to the car lot", "remove a car from the car lot", "approve/deny offers", "exit");
					main.Display();
					input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
					EmployeeSelector(input);
					}
				} while(input < 1 || input > lot.size());
			} catch (SQLException e) {
				System.out.println("\n\n\nThe car lot is empty");
				main = new Menu("Employee Menu", "add a car to the car lot", "remove a car from the car lot", "approve/deny offers", "exit");
				main.Display();
				input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
				EmployeeSelector(input);
			}
			break;
		case 3:
	
			break;
		case 4:
			System.exit(0);
			break;

		default:
			System.out.println("\n\n\nPlease select a valid option");
			main = new Menu("Employee Menu", "add a car to the car lot", "remove a car from the car lot", "approve/deny offers", "exit");
			main.Display();
			input = Validate.CheckInt(sc.nextLine(), "Please use a whole number format");
			EmployeeSelector(input);
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
