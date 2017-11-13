package model;

import controller.StaffController;

import java.util.ArrayList;
import java.util.Scanner;

public class Staff extends Person{

	//Attribute(s)
	private int staffID;
	
	Scanner sc = new Scanner(System.in);
	
	//class constructor
	public Staff(String password, String name, int mobileNumber, String email, int staffID) {
		super(password, name, mobileNumber, email);//abstract
		this.staffID = staffID;
	}
	
	//access private ID
	public int getStaffID() {
		return this.staffID;
	}
	
	//mutate private ID
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	
	//login(abstract)
	public boolean login() {
		//Logic: ask user for email and pw; check against arraylist iteratively; return TRUE/FALSE
		System.out.println("Please enter your email address: ");
		String email = sc.next();
		System.out.println("Please enter your password: ");
		String password = sc.next();
		
		ArrayList<Staff> staffList = StaffController.getInstance().getStaffList();
		
		for (int i=0; i < staffList.size(); i++) {
			if (staffList.get(i).getEmail() == email) {
				if (staffList.get(i).getPassword() == password) {
					return true;
				}
			}
		}
		
		return false;
	}
}
