package model;

import java.util.Scanner;

/**
 * Represents a MovieGoer and its special attributes.
 * @author Amos
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieGoer extends Person {
	/**
	 * Unique identifier of the moviegoer.
	 */
	private int cusID;

	/**
	 * Age of the moviegoer.
	 */
	private int age;

	/**
	 * Creates a new MovieGoer account with its attributes. 
	 * @param password		Password of the account.
	 * @param name			Name of the moviegoer.
	 * @param mobileNumber	Mobile number of the moviegoer.
	 * @param email			Email of the moviegoer.
	 * @param cusID			CustomerID of the moviegoer.
	 * @param age			Age of the moviegoer.
	 */
	public MovieGoer(String password, String name, String mobileNumber, String email, int cusID, int age) {
		super(password, name, mobileNumber, email);//abstract
		this.cusID = cusID;
		this.age = age;
	}

	/**
	 * Gets the unique identifier of the moviegoer
	 * @return	Unique customerID of the moviegoer.
	 */
	public int getCusID() {
		return this.cusID;
	}

	/**
	 * Validate the moviegoer account.
	 * @param email		Email of the moviegoer
	 * @param password Password of the moviegoer.
	 * @return 			true if success. false is failed to authenticate.
	 */
	@Override
	public boolean validateIdentity(String email, String password) {
		//same logic as staff
		return email.equals(super.email) && password.equals(super.password);
	}

	/**
	 * Get the age of the moviegoer.
	 * @return Age of the moviegoer.
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * Set the age of the moviegoer.
	 * @param age	Age of the moviegoer
	 */
	public void setAge(int age) {
		this.age = age;
		return;
	}

	/**
	 * Print out the information of the moviegoer.
	 */
	public void printInfo(){
		System.out.println("Email Address: " + email);
		System.out.println("Mobile Number: " + mobileNumber);
		System.out.println("Name: " + name);
		System.out.println("CustomerID: " + cusID);
		System.out.println("Age: " + age);
	}
}
