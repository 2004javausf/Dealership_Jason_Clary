package com.revature.util;

import java.util.Scanner;

public class Validate {
	//Scanner to take in user input
		public static Scanner userInput = new Scanner(System.in);

		//Checks the user input and will try and parse it to an int
		public static int CheckInt(String input, String errorMessage) {
			int numReturn;
			try {
				return Integer.parseInt(input);		
			}
			catch (NumberFormatException e) {
				System.out.println(errorMessage);
				
				// New user input and reset prompts
				String newInput = userInput.nextLine();
				numReturn = CheckInt(newInput, errorMessage);
			}	
			
			return numReturn;
		}
	  
	    //Checks the user input and will try and parse it to an double
		public static double CheckDouble(String input, String errorMessage) {
			try {
				return Double.parseDouble(input);		
			}
			catch (NumberFormatException e) {
				System.out.println(errorMessage);
				
				// New user input and reset prompts
				String newInput = userInput.nextLine();
				CheckDouble(newInput, errorMessage);
			}	
			
			return 0;
		}

	    //Checks if the user chose yes or no
		public static boolean CheckYesNo(String prompt, String errorMessage) {
			//prompts the user and gets their input
			System.out.println("\n" + prompt);
			String newInput = userInput.nextLine();
			
			//if the user gives incorrect input will loop till its correct
			while (!(newInput.equalsIgnoreCase("y") || newInput.equalsIgnoreCase("yes") || newInput.equalsIgnoreCase("n") || newInput.equalsIgnoreCase("no"))) {
				System.out.println("\n" + errorMessage + "\n" + prompt);
				newInput = userInput.nextLine();
			}
		
			//if yes will return true, if no will return false
			if (newInput.equalsIgnoreCase("y") || newInput.equalsIgnoreCase("yes"))
				return true;
			else
				return false;
		}

		//Checks if the user chose approve or deny
		public static boolean CheckApproveDeny(String prompt, String errorMessage) {
				//prompts the user and gets their input
				System.out.println("\n" + prompt);
				String newInput = userInput.nextLine();
				
				//if the user gives incorrect input will loop till its correct
				while (!(newInput.equalsIgnoreCase("approve") || newInput.equalsIgnoreCase("approves") || newInput.equalsIgnoreCase("deny") || newInput.equalsIgnoreCase("denies") || newInput.equalsIgnoreCase("denys"))) {
					System.out.println("\n" + errorMessage + "\n" + prompt);
					newInput = userInput.nextLine();
				}
			
				//if yes will return true, if no will return false
				if (newInput.equalsIgnoreCase("approve") || newInput.equalsIgnoreCase("approves"))
					return true;
				else
					return false;
			}
}
