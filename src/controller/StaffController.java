package CZ2002Assn;

import java.util.ArrayList;
import java.util.Scanner;

public class StaffController {

	
	//Attributes
	private String DIR = "staff/";
	
	private ArrayList<Staff> staffList;
	
	private static StaffController instance = null;
	
	Scanner sc = new Scanner(System.in);
	
	//Class constructor
	private StaffController() {
		
	}
	
	//database methods
	protected void readDB() {
		//to be added
	}
	protected void writeDB() {
		//to be added
	}
	
	//static 'instance' method
	public static StaffController getInstance() {
		if(instance == null) {
			instance = new StaffController();
		}
		return instance;
	}
	
	//add staff to DB
	public void addStaff() {
		int newID = 0;
		String newEmail = null;
		int newNum = 0;
		String newName = null;
		String newPass = null;
		
		
		//Get and set details
		Staff newStaff = new Staff(newPass, newName, newNum, newEmail, newID);
		System.out.println("Enter new Staff ID: ");
		newID = sc.nextInt();
		newStaff.setStaffID(newID);
		
		System.out.println("Enter new Staff Email: ");
		newEmail = sc.next();
		newStaff.setEmail(newEmail);
		
		System.out.println("Enter new Staff Mobile Number: ");
		newNum = sc.nextInt();
		newStaff.setMobleNumber(newNum);
		
		System.out.println("Enter new Staff Name: ");
		newName = sc.next();
		newStaff.setName(newName);
		
		System.out.println("Enter new Staff Password: ");
		newPass = sc.next();
		newStaff.setPassword(newPass);
		
		//add to array
		staffList.add(newStaff);
		
		//add to DB - to be added
	}
	
	//get private arraylist for login
	public ArrayList<Staff> getStaffList(){
        return staffList;
    }
}
