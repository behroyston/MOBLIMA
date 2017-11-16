package controller;

import model.Cinema;
import model.Cineplex;
import model.CinemaClassType;

import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;

/**
 * A controller to perform storing and retrieval of Cineplex model to/from database.
 * Also validates changes before committing any changes to database.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public class CineplexController extends DatabaseController {
	/**
	 * The sub-directory of Cineplex Controller.
	 */
	private final String DIR = "cineplex/";

	/**
	 * Instance of the cineplex database controller.
	 */
	private static CineplexController instance = null;

	/**
	 * List of the cineplex.
	 */
	private ArrayList<Cineplex> cineplexList;

	/**
	 * Creates a new Cineplex Controller.
	 * Also initialize the list of cineplex.
	 */
	private CineplexController(){
		cineplexList = new ArrayList<>();
		readDB();
	}

	/**
	 * Get the list of cineplex owned by the vendor
	 * @return List of cineplex
	 */
	public ArrayList<Cineplex> getCineplexList(){
		return cineplexList;
	}

	/**
	 * Find the cinema with the corresponding cinemaID from all the cineplex
	 * @param cinemaID	Cinema ID of the cinema to find
	 * @return			Cinema corresponding to the cinemaID if it exists. Returns null if cannot find such cinema.
	 */
	public Cinema getCinema(int cinemaID){
		for (Cineplex cineplex : cineplexList)
			for (Cinema cinema : cineplex.getCinemaList())
				if (cinema.getCinemaID() == cinemaID)
					return cinema;
		return null;
	}

	public Cineplex getCineplexByCinemaID(int cinemaID){
		for (Cineplex cineplex : cineplexList)
			for (Cinema cinema : cineplex.getCinemaList())
				if (cinema.getCinemaID() == cinemaID)
					return cineplex;
		return null;
	}
	
	/**
	 * Retrieve the Cineplex model from database.
	 * The order is as follows: Cineplex Name , Location of Cineplex, List of Cinema whichs includes CinemaID, 
	 * Cinema Classtype, Seats Layout
	 * These variables are parsed into the Cineplex objects and stored when the Cineplex Controller is initalized.
	 */
	@Override
	protected void readDB() {
		cineplexList.clear();

		if (checkDirectoryExist(BASEDIR + DIR)) {
			try{
				for (File f : getListofFiles(BASEDIR + DIR)) {
					List<String> text = retrieveData(BASEDIR + DIR + f.getName());

					// model.Cineplex Attributes
					StringTokenizer aStr = new StringTokenizer(text.get(0), DELIMITER);
					String cineplexName = aStr.nextToken();			// Name
					String cineplexLocation = aStr.nextToken();		// Location

					// model.Cinema Attributes
					ArrayList<Cinema> cinemaList = new ArrayList<>();
					for (String line : text.subList(1, text.size())){
						aStr = new StringTokenizer(line, DELIMITER);
						int cinemaID = Integer.parseInt(aStr.nextToken());	// CinemaID
						String classType = aStr.nextToken();				// Type of Cinema
						CinemaClassType cinemaClassType = CinemaClassType.valueOf(classType);

						// model.Seat Layout
						char[][] seatLayout = new char[8][15];
						for (int i = 0; i < seatLayout.length; i++)
							Arrays.fill(seatLayout[i], ' ');
						int i = 0;
						while (aStr.hasMoreTokens()) {
							String col = aStr.nextToken();
							for (int j = 0; j < col.length(); j++)
								seatLayout[i][j] = col.charAt(j);
							i++;
						}
						cinemaList.add(new Cinema(cinemaID, cinemaClassType, seatLayout));
					}
					cineplexList.add(new Cineplex(cineplexName, cineplexLocation, cinemaList));
				}
			}
			catch (IOException | NumberFormatException ex){
				System.out.println("Error! Unable to retrieve Cineplex model from file. The data may be corrupted.");
			}

		} else {
			System.out.println("Error, Directory not found! Database for model.Cineplex is not loaded!");
		}
	}

	/** Save the Cineplex model into database. Each file in the directory is a cineplex object.
	 *The order is as follows: Cineplex Name , Location of Cineplex, List of Cinema whichs includes CinemaID, 
	 * Cinema Classtype, Seats Layout
	 * The first line in the file is the cineplex attributes. Subsequent lines are the cineplex's cinemas attributes.
	 */
	@Override
	protected void writeDB() {
		List<String> text = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		/* Summary: 1st line of the file will be the cineplex attributes
		 * All following line will be the model.Cineplex's cinemas attributes
		 * The seat layout will be divided by column by column for storage.
		 */
		for (Cineplex cineplex : cineplexList){
			text.clear();
			if (checkDirectoryExist(BASEDIR + DIR)) {
				// model.Cineplex Attributes
				str.setLength(0); // Reset Buffer
				str.append(cineplex.getName());		// Name
				str.append(DELIMITER);
				str.append(cineplex.getLocation()); // Location
				str.append(DELIMITER);
				text.add(str.toString());			// Write to line

				// model.Cinema attributes
				for (Cinema cinema : cineplex.getCinemaList()){	// Cinemas
					str.setLength(0); // Reset Buffer
					str.append(cinema.getCinemaID());	// model.Cinema ID
					str.append(DELIMITER);
					str.append(cinema.getCinemaClassType());	// model.Cinema Class type
					str.append(DELIMITER);

					// model.Seat layout of the cinema
					char [][] seatLayout = cinema.getSeatLayout();
					for (int i = 0; i < seatLayout.length; i++){
						for (int j = 0; j < seatLayout[i].length; j++){
							str.append(seatLayout[i][j]);
						}
						str.append(DELIMITER);
					}
					text.add(str.toString());			// Write to line
				}

				// Attempt to save to file
				try {
					saveData(BASEDIR + DIR + cineplex.getName() + ".dat", text); // Write to file
				} catch (Exception ex) {
					System.out.println("Error! Unable to write to file!");
				}
			} else {
				System.out.println("Error! Directory cannot be found!");
			}
		}
	}

	/**
	 * Gets the channel reference of the CineplexController.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of Cineplex Controller.
	 */
	public static CineplexController getInstance(){
		if (instance == null)
			instance = new CineplexController();
		return instance;
	}
}
