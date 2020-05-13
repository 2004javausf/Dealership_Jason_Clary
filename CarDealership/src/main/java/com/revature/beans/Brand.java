package com.revature.beans;

import java.util.ArrayList;
import java.util.List;

public class Brand {
//Class fields
	private int id;
	private String make;
	private String model;
	//Collections for menus
	public static List<Integer> IDs = new ArrayList<Integer>();
	public static List<String> Makes = new ArrayList<String>();
	public static List<String> Models = new ArrayList<String>();
	
	public Brand() {
		super();
	}
	
	public Brand(int id, String make, String model) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
	}

	//Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Make=" + make + " Model=" + model;
	}
	
	//Override toString
	
	
}
