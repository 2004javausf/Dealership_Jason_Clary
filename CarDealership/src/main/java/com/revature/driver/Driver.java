package com.revature.driver;

import java.sql.SQLException;

import com.revature.beans.User;
import com.revature.util.DAOImp;
import com.revature.util.Menu;

public class Driver {
//Global Fields-----
static User user;
//Menus
static Menu main;
//SQL 
static DAOImp imp = new DAOImp();

	public static void main(String[] args) {
		main = new Menu("Welcome to the Dealership", "Log in", "Sign up");
		main.Display();
		do {
			try {
				user = imp.login();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (user.getId() > 0);
		
		if (user.getUserType() .equalsIgnoreCase("Customer")) {
			main = new Menu("");
		}
		else if(user.getUserType() .equalsIgnoreCase("Employee")) {
			main = new Menu("");
		}
		else {System.out.println("Don't know how you got here bud.");}
		
	}

	//Menus Selectors
	public void MainSelector(int input) {
		switch (input) {
		case 1:
			
			break;
		case 2:
			
			break;

		default:
			break;
		}
	}
}
