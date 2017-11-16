package model;

import controller.StaffController;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Represents a Staff and its special attributes.
 * @author Amos
 * @version 1.0
 * @since 2017-11-06
 */
public class Staff extends Person{
	/**
	 * Unique identifier of the Staff.
	 */
	private int staffID;


	/**
	 * Creates a Staff account with its attributes.
	 * @param password		Password of the account.
	 * @param name			Name of the staff.
	 * @param mobileNumber	Mobile number of the staff.
	 * @param email			Email of the staff.
	 * @param staffID		StaffID of the staff.
	 */
	public Staff(String password, String name, String mobileNumber, String email, int staffID) {
		super(password, name, mobileNumber, email);//abstract
		this.staffID = staffID;
	}

	/**
	 * Gets the unique identifier of the staff.
	 * @return	StaffID of the staff.
	 */
	public int getStaffID() {
		return this.staffID;
	}

	/**
	 * Validate the staff account.
	 * @param email		Email of the staff.
	 * @param password Password of the staff.
	 * @return 			true if success. false is failed to authenticate.
	 */
	@Override
	public boolean validateIdentity(String email, String password) {
		// same as MovieGoer
		return email.equals(super.email) && password.equals(super.password);
	}

	/**
	 * Print the staff details
	 */
	public void showStaffInfo(){
		System.out.println("Email Address: " + email);
		System.out.println("Mobile Number: " + mobileNumber);
		System.out.println("Name: " + name);
		System.out.println("StaffID: " + staffID);
	}
}
