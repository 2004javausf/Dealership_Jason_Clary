package com.revature.util;

import java.util.ArrayList;
import java.util.List;

import com.revature.beans.User;
import com.revature.beans.Vehicle;

public class Offer {
//Class Fields
private User user;
private Vehicle vehicle;
private double downPayment;
private String payPlan;
private int payments;
//Collection of Offers
public List<Offer> Offers = new ArrayList<Offer>();

public Offer() {
	
}

//Getters & Setters
public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Vehicle getVehicle() {
	return vehicle;
}

public void setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
}

public double getDownPayment() {
	return downPayment;
}

public void setDownPayment(double downPayment) {
	this.downPayment = downPayment;
}

public String getPayPlan() {
	return payPlan;
}

public void setPayPlan(String payPlan) {
	this.payPlan = payPlan;
}

public int getPayments() {
	return payments;
}

public void setPayments(int payments) {
	this.payments = payments;
}


}
