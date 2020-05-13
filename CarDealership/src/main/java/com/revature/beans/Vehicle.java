package com.revature.beans;

import java.text.NumberFormat;

public class Vehicle {
	private int id;
	private int year;
	private String make;
	private String model;
	private String color;
	private double price;
	private String status;
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(int id, int year, String make, String model, String color, double price, String status) {
		super();
		this.id = id;
		this.year = year;
		this.make = make;
		this.model = model;
		this.color = color;
		this.price = price;
		this.status = status;
	}
	
	//Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		NumberFormat balance = NumberFormat.getCurrencyInstance();
		return "Year=" + year + " Make=" + make + " Model=" + model + " Color=" + color + " Price=" + balance.format(price);
	}
}
