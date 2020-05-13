package com.revature.util;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	// Menu variables
		private String title;
		private List<String> menuItems = new ArrayList<String>();
		
		// Strings to change the color of other Strings printed to the screen
		private static final String cyanString = "\u001B[36m";
		private static final String resetString = "\u001B[0m";
		
		// Constructor for a very basic menu
		public Menu(String title) {
			this.title = title;

		}
		
	    // Constructor for custom menu taking in a title and items to display in menu
		public Menu(String title, String...menuItems) {
			this.title = title;
			
			// Cycles through the user inputs to add the list
			for (String item : menuItems) {
				this.menuItems.add(item);
			}
		}
		
		// Adds an item to the menu list 
		public void AddMenuItem (String item) {
			menuItems.add(item);
		}
		
		// Updates the menu title
		public void newTitle(String title) {
			this.title = title;
		}
		
		// Retrieves the menu title
		public String getTitle() {
			return title;
		}
		
		// Displays the menu and all its items to the user
		public void Display() {
			System.out.println("=============================");
			System.out.println(cyanString + "\t" + title + resetString);
			System.out.println("=============================");
			
			int counter = 1;
			for (String item : menuItems) {
				System.out.println(cyanString + counter++ + ") " + item + resetString);
			}
		}

}
