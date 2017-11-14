package model;

import controller.MovieGoerController;

import java.util.ArrayList;
import java.util.Scanner;

public class MovieGoer extends Person {

	//Attributes
	private int cusID;
	private int age;
	private boolean isStudent;
	
	Scanner sc = new Scanner(System.in);
	
	//Class constructor
	public MovieGoer(String password, String name, int mobileNumber, String email, int cusID, int age, boolean isStudent) {
		super(password, name, mobileNumber, email);//abstract
		this.cusID = cusID;
		this.age = age;
		this.isStudent = isStudent;
	}
	
	//access private ID
	public int getCusID() {
		return this.cusID;
	}
	//mutate private ID
	public void setCusID(int cusID) {
		this.cusID = cusID;
		return;
	}
	
	//Login for moviegoer
	@Override
	public boolean validateIdentity(String email, String password) {
		//same logic as staff
		return email.equals(super.email) && password.equals(super.password);
	}
	
	//access private age
	public int getAge() {
		return this.age;
	}
	//mutate private age
	public void setAge(int age) {
		this.age = age;
		return;
	}
	
	//access private age
	public boolean getIsStudent() {
		return isStudent;
	}
	
	//mutate private age
	public void setIsStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	
	public void printInfo(){
		System.out.println("Email Address: " + email);
		System.out.println("Password: " + password);
		System.out.println("Mobile Number: " + mobileNumber);
		System.out.println("Name: " + name);
		System.out.println("CustomerID: " + cusID);
		System.out.println("Age: " + age);
		System.out.println("isStudent: " + isStudent);
	}
}
