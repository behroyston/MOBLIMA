package controller;

import model.Booking;
import model.Staff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class StaffController extends DatabaseController{

	
	//Attributes
	private String DIR = "staff/";
	
	private ArrayList<Staff> staffList;
	
	private static StaffController instance = null;
	
	Scanner sc = new Scanner(System.in);
	
	//Class constructor
	private StaffController() {
		
	}

    @Override
    protected void readDB() {
        staffList.clear();
        if (checkDirectoryExist(BASEDIR + DIR)) {
            try{
                //int staffID, String name, String password, int mobileNumber, String email
                for (File f : getListofFiles(BASEDIR + DIR)) {
                    List<String> text = retrieveData(BASEDIR + DIR + "Staff.dat");
                    StringTokenizer aStr;
                    Staff staff;
                    for (String line : text) {
                        // model.MovieScreening Attributes
                        aStr = new StringTokenizer(line, DELIMITER);
                        int staffID = Integer.parseInt(aStr.nextToken());
                        String userName = aStr.nextToken();
                        String password = aStr.nextToken();
                        String mobileNum = aStr.nextToken();
                        String emailAddress = aStr.nextToken();
                        staff = new Staff(password,userName,mobileNum,emailAddress,staffID);
                        bookingList.add(booking);
                    }
                }

            }
            catch (IOException io)
            {
                System.out.println("Error! Unable to retrieve model from file.");
            }
        }
        else
        {
            System.out.println("Error, Directory not found! Database for Booking is not loaded!");
        }

    }

    @Override
    protected void writeDB() {

        //int staffID, String name, String password, int mobileNumber, String email
        List<String> text = new ArrayList<>();
        StringBuilder str = new StringBuilder();

        for (Staff staff : staffList){
            text.clear();
            if (checkDirectoryExist(BASEDIR + DIR)) {
                // model.Cineplex Attributes
                str.setLength(0); // Reset Buffer
                str.append(staff.getStaffID());
                str.append(DELIMITER);              // StaffID
                str.append(staff.getName());		// Name
                str.append(DELIMITER);
                str.append(staff.getPassword()); // Password
                str.append(DELIMITER);
                str.append(staff.getMobileNumber());   //  mobileNumber
                str.append(DELIMITER);
                str.append(staff.getEmail());     // emailAddres
                str.append(DELIMITER);
                text.add(str.toString());			// Write to line

                // Attempt to save to file
                try {
                    saveData(BASEDIR + DIR + "Staff.dat", text); // Write to file
                } catch (Exception ex) {
                    System.out.println("Error! Unable to write to file!");
                }
            } else {
                System.out.println("Error! Directory cannot be found!");
            }
        }

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
