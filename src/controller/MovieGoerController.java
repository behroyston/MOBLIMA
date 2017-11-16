package controller;

import model.MovieGoer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A controller to perform storing and retrieval of Movie to/from database.
 * Also validates changes before committing any changes to database.
 * @author Amos
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieGoerController extends DatabaseController{
	/**
	 * The sub-directory of the storage of Bookings.
	 */
	private String DIR = "person/";
	
	/**
	 * List of the moviegoers.
	 */
	private ArrayList<MovieGoer> movieGoerList;

	/**
	 * Instance of the MovieGoer controller.
	 */
	private static MovieGoerController instance = null;

	/**
	 * Creates a new MovieGoer Controller.
	 * Also initialize the list of moviegoers and read from database.
	 */
	private MovieGoerController() {
		movieGoerList = new ArrayList<>();
		readDB();
	}

	/**
	 * Retrieve the moviegoers from database.
	 * The order is as follows: MovieGoer Name, Mobile Number, Password, Email, CustomerID, age
	 * These variables are parsed into the MovieGoer objects and stored when the MovieGoer Controller is initalized.
	 */
	protected void readDB() {
		movieGoerList.clear();
		if (checkDirectoryExist(BASEDIR + DIR)) {
			try{
				for (File f : getListofFiles(BASEDIR + DIR)) {
					if (f.getName().equals("moviegoer.dat") || f.getName().equals("moviegoer")){
						List<String> text = retrieveData(BASEDIR + DIR + f.getName());
						StringTokenizer aStr;
						for (String line : text) {
							aStr = new StringTokenizer(line, DELIMITER);
							String name = aStr.nextToken();						// Name
							String mobileNumber = (aStr.nextToken());			// Mobile Number
							String password = aStr.nextToken();					// Password
							String email = aStr.nextToken();					// Email
							int cusID = Integer.parseInt(aStr.nextToken());		// Customer ID
							int age = Integer.parseInt(aStr.nextToken());		// Age
							MovieGoer movieGoer = new MovieGoer(password, name, mobileNumber, email, cusID, age);
							movieGoerList.add(movieGoer);
						}
					}
				}
			}
			catch (IOException | NumberFormatException ex)
			{
				System.out.println("Error! Unable to retrieve MovieGoer model from file. The data may be corrupted.");
			}
		}
		else
		{
			System.out.println("Error, Directory not found! Database for MovieGoer is not loaded!");
		}

	}
	
	/**
	 * Save the MovieGoer model into database. Each line in the moviegoer.dat file is a booking object.
	 * The order is as follows: MovieGoer Name, Mobile Number, Password, Email, CustomerID, age
	 * These will be appended into the moviegoers.dat file in sequence.
	 */
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
	
	/**
	 * Add a moviegoer into database. It will also checks if the moviegoer account already exists
	 * in the database by its email address.
	 * @param password		Password of the moviegoer.
	 * @param name			Name of the moviegoer.
	 * @param mobileNumber	Mobile number of the moviegoer.
	 * @param email			Email address of the moviegoer.
	 * @param age			Age of the moviegoer.
	 * @return				True if it successfully added. False otherwise.
	 */
	public boolean addMovieGoer(String password, String name, String mobileNumber, String email, int age){
		int cusID;
		if (movieGoerList.size() == 0)
			cusID = 1;
		else{
			for (MovieGoer movieGoer : movieGoerList)
				if (movieGoer.getEmail() == email)
					return false;
			cusID = movieGoerList.get(movieGoerList.size()-1).getCusID();
		}
		movieGoerList.add(new MovieGoer(password, name, mobileNumber, email, cusID, age));
		writeDB();
		return true;
	}

	/**
	 * Get the MovieGoer object by its email address.
	 * @param email	Email of the moviegoer.
	 * @return		MovieGoer object it the account exists in database. null object otherwise.
	 */
	public MovieGoer getMovieGoerByEmail(String email){
		for (MovieGoer movieGoer : movieGoerList)
			if (email.equalsIgnoreCase(movieGoer.getEmail()))
				return movieGoer;
		return null;
	}

	/**
	 * Validate the account credentials of a moviegoer.
	 * @param email		Email of the moviegoer.
	 * @param password	Password of the moviegoer.
	 * @return			MovieGoer object if the account exists and the credentials are correct. null object otherwise.
	 */
	public MovieGoer validateCustomer(String email, String password){
		MovieGoer movieGoer = getMovieGoerByEmail(email);
		if (movieGoer != null){
			if (movieGoer.validateIdentity(email, password))
				return movieGoer;
		}
		return null;
	}

	/**
	 * Gets the channel reference of the MovieGoerController.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the MovieGoerController.
	 */
	public static MovieGoerController getInstance(){
		if(instance == null) {
			instance = new MovieGoerController();
		}
		return instance;
	}

	/**
	 * Gets the list of moviegoers from database.
	 * @return	List of MovieGoers.
	 */
	public ArrayList<MovieGoer> getMovieGoerList(){
		return movieGoerList;
	}
}
