package controller;

import model.Staff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A controller to perform storing and retrieval of Staff to/from database.
 * Also validates changes before committing any changes to database.
 * @author Amos
 * @version 1.0
 * @since 2017-11-06
 */
public class StaffController extends DatabaseController{

	/**
	 * The sub-directory of the storage of Bookings.
	 */
	private String DIR = "person/";
	
	/**
	 * List of the staffs.
	 */
	private ArrayList<Staff> staffList;
	
	/**
	 * Instance of the Staff controller.
	 */
	private static StaffController instance = null;
	
	/**
	 * Creates a new Staff Controller.
	 * Also initialize the list of staffs and read from database.
	 */
	private StaffController() {
	    staffList = new ArrayList<>();
	    readDB();
	}

	/**
	 * Retrieve the staffs from database.
	 * The order is as follows: Staff Name, Mobile Number, Password, Email, StaffID
	 * These variables are parsed into the MovieGoer objects and stored when the MovieGoer Controller is initalized.
	 */
    @Override
    protected void readDB() {
        staffList.clear();
        if (checkDirectoryExist(BASEDIR + DIR)) {
            try{
                //int staffID, String name, String password, int mobileNumber, String email
                for (File f : getListofFiles(BASEDIR + DIR)) {
                    if (f.getName().equals("Staff.dat")) {
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
                            staff = new Staff(password, userName, mobileNum, emailAddress, staffID);
                            staffList.add(staff);
                        }
                    }
                }

            }
            catch (IOException | NumberFormatException ex)
            {
                System.out.println("Error! Unable to retrieve Staff model from file.");
            }
        }
        else
        {
            System.out.println("Error, Directory not found! Database for Booking is not loaded!");
        }

    }
    
    /**
	 * Save the Staff model into database. Each line in the Staff.dat file is a booking object.
	 * The order is as follows: Staff Name, Mobile Number, Password, Email, StaffID
	 * These will be appended into the Staff.dat file in sequence.
	 */
    @Override
    protected void writeDB() {

        //int staffID, String name, String password, int mobileNumber, String email
        List<String> text = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        if (checkDirectoryExist(BASEDIR + DIR)) {
            for (Staff staff : staffList) {
                // model.Cineplex Attributes
                str.setLength(0); // Reset Buffer
                str.append(staff.getStaffID());
                str.append(DELIMITER);              // StaffID
                str.append(staff.getName());        // Name
                str.append(DELIMITER);
                str.append(staff.getPassword()); // Password
                str.append(DELIMITER);
                str.append(staff.getMobileNumber());   //  mobileNumber
                str.append(DELIMITER);
                str.append(staff.getEmail());     // emailAddress
                str.append(DELIMITER);
                text.add(str.toString());            // Write to line
            }

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

    /**
	 * Validate the account credentials of a staff.
	 * @param email		Email of the staff.
	 * @param password	Password of the staff.
	 * @return			true if the account exists and the credentials are correct. false otherwise.
	 */
    public boolean validateStaff(String email, String password){
        Staff staff = getStaffByEmail(email);
        if (staff != null)
            return staff.validateIdentity(email, password);
        return false;
    }
    
    /**
	 * Get the Staff object by its email address.
	 * @param email	Email of the staff.
	 * @return		Staff object it the account exists in database. null object otherwise.
	 */
    public Staff getStaffByEmail(String email){
        for (Staff staff : staffList)
            if (email.equalsIgnoreCase(staff.getEmail()))
                return staff;
        return null;
    }
    
    /**
	 * Gets the channel reference of the StaffController.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the StaffController.
	 */
	public static StaffController getInstance() {
		if(instance == null) {
			instance = new StaffController();
		}
		return instance;
	}
	
    /**
	 * Gets the list of staffs from database.
	 * @return	List of Staffs.
	 */
	public ArrayList<Staff> getStaffList(){
        return staffList;
    }
    
    /**
     * Print out all the staff info.
     */
    public void printStaffList(){
	    for (int i = 0; i < staffList.size(); i ++){
	        staffList.get(i).showStaffInfo();
        }
    }
}
