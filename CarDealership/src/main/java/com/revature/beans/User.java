package com.revature.beans;

public class User {
	//Class Fields
	private int id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String userType;
	
	public User() {
		super();
	}
	
	public User(int id, String firstname, String lastname, String username, String password, String userType) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.userType = userType;
	}
	
	//Getters & Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", userType=" + userType + "]";
	}
	
}
