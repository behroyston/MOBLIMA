package controller;

import model.Booking;
import model.MovieGoer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class MovieGoerController extends DatabaseController{
	//Attributes
	private String DIR = "person/";

	private ArrayList<MovieGoer> movieGoerList;

	private static MovieGoerController instance = null;

	//Class constructor
	private MovieGoerController() {

	}

	@Override
	protected void readDB() {
		movieGoerList.clear();
		if (checkDirectoryExist(BASEDIR + DIR)) {
			try{
				for (File f : getListofFiles(BASEDIR + DIR)) {
					List<String> text = retrieveData(BASEDIR + DIR + f.getName());
					StringTokenizer aStr;
					for (String line : text) {
						aStr = new StringTokenizer(line, DELIMITER);
						String name = aStr.nextToken();
						String mobileNumber = (aStr.nextToken());
						String password = aStr.nextToken();
						String email = aStr.nextToken();
						int cusID = Integer.parseInt(aStr.nextToken());
						int age = Integer.parseInt(aStr.nextToken());
						boolean isStudent = Boolean.parseBoolean(aStr.nextToken());
						MovieGoer movieGoer = new MovieGoer(password, name, mobileNumber, email, cusID, age, isStudent);
						movieGoerList.add(movieGoer);
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
	
	protected void writeDB() {
		List<String> text = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		/* Summary: Each line in the file represents a moviegoer.
		 * It stores the name, mobile number, password, email, customer ID, age and whether it is student or not.
		 */
		text.clear();
		if (checkDirectoryExist(BASEDIR + DIR)) {
			for (MovieGoer movieGoer : movieGoerList){
				// model.Cineplex Attributes
				str.setLength(0); // Reset Buffer
				str.append(movieGoer.getName());		// Name
				str.append(DELIMITER);
				str.append(movieGoer.getMobileNumber()); // Mobile Number
				str.append(DELIMITER);
				str.append(movieGoer.getPassword());	// Password
				str.append(DELIMITER);	
				str.append(movieGoer.getEmail());		// Email
				str.append(DELIMITER);
				str.append(movieGoer.getCusID());		// Customer ID
				str.append(DELIMITER);
				str.append(movieGoer.getAge());			// Age
				str.append(DELIMITER);
				str.append(movieGoer.getIsStudent()); // isStudent
				str.append(DELIMITER);
				text.add(str.toString());			// Write to line
			}
			// Attempt to save to file
			try {
				saveData(BASEDIR + DIR + "moviegoer.dat", text); // Write to file
			} catch (Exception ex) {
				System.out.println("Error! Unable to write to file!");
			}
		}
		else {
			System.out.println("Error! Directory cannot be found!");
		}
	}
	
	public MovieGoer getMovieGoerByEmail(String email){
		for (MovieGoer movieGoer : movieGoerList)
			if (email.equalsIgnoreCase(movieGoer.getEmail()))
				return movieGoer;
		return null;
	}

	public boolean validateCustomer(String email, String password){
		MovieGoer movieGoer = getMovieGoerByEmail(email);
		if (movieGoer != null)
			return movieGoer.validateIdentity(email, password);
		return false;
	}

	//static 'instance' method	
	public static MovieGoerController getInstance(){
		if(instance == null) {
			instance = new MovieGoerController();
		}
		return instance;
	}

	//get private arraylist for login
	public ArrayList<MovieGoer> getMovieGoerList(){
		return movieGoerList;
	}

	
	public void setMovieGoerList(ArrayList<MovieGoer> arrList){
		movieGoerList = arrList;
	}
}
