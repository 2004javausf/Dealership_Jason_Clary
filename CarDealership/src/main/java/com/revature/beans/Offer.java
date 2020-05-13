package com.revature.beans;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Offer {
//Class Fields
private String fname;
private String lname;
private String color;
private int year;
private String make;
private String model;
private double price;
private double downPayment;
private int payments;
private double monthlyPay;
private int userID;
private int vehiclesID;
//Collection of Offers
public static List<Offer> Offers = new ArrayList<Offer>();

public Offer() {
	
}

public Offer(String fname, String lname, String color, int year, String make, String model, double price,
		double downPayment, int payments, double monthlyPay) {
	super();
	this.fname = fname;
	this.lname = lname;
	this.color = color;
	this.year = year;
	this.make = make;
	this.model = model;
	this.price = price;
	this.downPayment = downPayment;
	this.payments = payments;
	this.monthlyPay = monthlyPay;
}

//Getters & Setters
public double getDownPayment() {
	return downPayment;
}

public void setDownPayment(double downPayment) {
	this.downPayment = downPayment;
}

public int getPayments() {
	return payments;
}

public void setPayments(int payments) {
	this.payments = payments;
}

public String getFname() {
	return fname;
}

public void setFname(String fname) {
	this.fname = fname;
}

public String getLname() {
	return lname;
}

public void setLname(String lname) {
	this.lname = lname;
}

public String getColor() {
	return color;
}

public void setColor(String color) {
	this.color = color;
}

public int getYear() {
	return year;
}

public void setYear(int year) {
	this.year = year;
}

public String getMake() {
	return make;
}

public void setMake(String make) {
	this.make = make;
}

public String getModel() {
	return model;
}

public void setModel(String model) {
	this.model = model;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

public double getMonthlyPay() {
	return monthlyPay;
}

public void setMonthlyPay(double monthlyPay) {
	this.monthlyPay = monthlyPay;
}

@Override
public String toString() {
	NumberFormat balance = NumberFormat.getCurrencyInstance();
	return "FirstName=" + fname + " LastName=" + lname 
			+ " Color=" + color + " Year=" + year + " Make=" + make + " Model=" + model 
			+ " Price=" + balance.format(price) + " DownPayment=" + balance.format(downPayment) 
			+ " Payments=" + payments + " MonthlyPay=" + balance.format(monthlyPay);
}

public int getUserID() {
	return userID;
}

public void setUserID(int userID) {
	this.userID = userID;
}

public int getVehiclesID() {
	return vehiclesID;
}

public void setVehiclesID(int vehiclesID) {
	this.vehiclesID = vehiclesID;
}

//Overriden toString

}
