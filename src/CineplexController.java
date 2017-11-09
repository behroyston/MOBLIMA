import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Calendar;

/**
 * A controller to perform storing and retrieval of Cineplex data to/from database.
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
	 * Instance of the cineplex controller.
	 */
	private static CineplexController instance = null;
	
	/**
	 * List of the cineplex.
	 */
	private ArrayList<Cineplex> cineplexList;

	/**
	 * Creates a new Cineplex Controller. 
	 * Also initalise the list of cineplex.
	 */
	private CineplexController(){
		cineplexList = new ArrayList<>();
	}

	// Temporarily for testing purpose - remove after no use
	public void setCineplexList(ArrayList<Cineplex> cineplexList){
		this.cineplexList = cineplexList;
	}
	
	/**
	 * Get the list of cineplex owned by the vendor
	 * @return List of cineplex
	 */
	public ArrayList<Cineplex> getCineplexList(){
		return cineplexList;
	}

	/**
	 * Retrieve the Cineplex data from database. 
	 */
	@Override
	public void readDB() {
		cineplexList.clear();

		if (checkDirectoryExist(BASEDIR + DIR)) {
			File folder = new File(BASEDIR + DIR);
			File[] listOfFiles = folder.listFiles();
			try{
				for (File f : listOfFiles) {
					if (f.isFile()){
						List<String> text = retrieveData(BASEDIR + DIR + f.getName());

						// Cineplex Attributes
						StringTokenizer aStr = new StringTokenizer(text.get(0), DELIMTER);
						String cineplexName = aStr.nextToken();			// Name
						String cineplexLocation = aStr.nextToken();		// Location

						// Cinema Attributes
						ArrayList<Cinema> cinemaList = new ArrayList<>();
						for (String line : text.subList(1, text.size())){
							aStr = new StringTokenizer(line, DELIMTER);
							int cinemaID = Integer.parseInt(aStr.nextToken());	// CinemaID
							String classType = aStr.nextToken();				// Type of Cinema
							cinemaList.add(new Cinema(cinemaID, classType));

							// Movie Screening Attributes
							while (aStr.hasMoreTokens()) {
								Calendar startTime = Calendar.getInstance();
								startTime.setTimeInMillis(Long.parseLong(aStr.nextToken()));	// Start Time
								Calendar endTime = Calendar.getInstance();
								endTime.setTimeInMillis(Long.parseLong(aStr.nextToken()));		// End Time

								String movieType = aStr.nextToken();							// Movie Type
								int movieID = Integer.parseInt(aStr.nextToken());				// Movie ID
								cinemaList.get(cinemaList.size()-1).addMovieScreening(startTime, endTime, movieType, movieID);
							}
						}
						cineplexList.add(new Cineplex(cineplexName, cineplexLocation, cinemaList));
					}
				}
			}
			catch (IOException io){
				System.out.println("Error! Unable to retrieve data from file.");
			}

		} else {
			System.out.println("Error, Directory not found! Database for Cineplex is not loaded!");
		}
	}

	/**
	 * Save the Cineplex data into database. 
	 * Each cineplex will be divided into individual file.
	 */
	@Override
	public void writeDB() {
		List<String> text = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		for (Cineplex cineplex : cineplexList){
			text.clear();
			if (checkDirectoryExist(BASEDIR + DIR)) {
				// Cineplex Attributes
				str.setLength(0); // Reset Buffer
				str.append(cineplex.getName());		// Name
				str.append(DELIMTER);
				str.append(cineplex.getLocation()); // Location
				str.append(DELIMTER);
				text.add(str.toString());			// Write to line

				// Cinema attributes
				for (Cinema cinema : cineplex.getCinemaList()){	// Cinemas
					str.setLength(0); // Reset Buffer
					str.append(cinema.getCinemaID());	// Cinema ID
					str.append(DELIMTER);
					str.append(cinema.getClassType());	// Cinema Class type
					str.append(DELIMTER);

					// MovieScreening attributes
					for (MovieScreening screening : cinema.getMovieScreenings()){
						str.append(screening.getStartTime().getTimeInMillis());	// Start Time
						str.append(DELIMTER);
						str.append(screening.getEndTime().getTimeInMillis());	// End Time
						str.append(DELIMTER);
						str.append(screening.getMovieType());					// Movie Type
						str.append(DELIMTER);
						str.append(screening.getMovieID());						// Movie ID
						str.append(DELIMTER);
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
	 * Adds a new movie screening to a specified Cineplex's Cinema.
	 * Commits the change to database only if it is successfuly added.
	 * @param cineplexID	ID of the cineplex to add new movie screening.
	 * @param cinemaID		ID of the cinema to add new movie screening.
	 * @param movieID		ID of the corresponding movie of the movie screening.
	 * @param movieType		Movie type of the movie being screened.
	 * @param startTime		Starting time of the screening.
	 * @param endTime		Ending time of the screening.
	 */
	public void addMovieScreening(int cineplexID, int cinemaID, int movieID, String movieType, Calendar startTime, Calendar endTime){
		if (cineplexID >= cineplexList.size() || cineplexID < 0){
			System.out.println("Error! Invalid Cineplex!");
			return;
		}
		for (Cinema cinema : cineplexList.get(cineplexID).getCinemaList())
			if (cinema.getCinemaID() == cinemaID)
				if(cinema.addMovieScreening(startTime, endTime, movieType, movieID)){
					writeDB();
					return;
				}
		System.out.println("Error! Failed to add movie screening!");
	}
	
	/**
	 * Update a movie screening of a specified Cineplex's Cinema to a new value.
	 * Commits the change to database only if it is successfully updated.
	 * @param cineplexID	ID of the cineplex to change movie screening.
	 * @param cinemaID		ID of the cinema to change movie screening.
	 * @param screeningID	ID of movie screening to update.
	 * @param movieID		ID of the new movie to be screened.
	 * @param movieType		Movie type of the new movie to be screened.
	 * @param startTime		New starting time of the screening.
	 * @param endTime		Old starting time of the screening.
	 */
	public void updateMovieScreening(int cineplexID, int cinemaID, int screeningID, int movieID, String movieType, Calendar startTime, Calendar endTime){
		if (cineplexID >= cineplexList.size() || cineplexID < 0){
			System.out.println("Error! Invalid Cineplex!");
			return;
		}
		for (Cinema cinema : cineplexList.get(cineplexID).getCinemaList())
			if (cinema.getCinemaID() == cinemaID)
				if (cinema.updateMovieScreening(screeningID, startTime, endTime, movieType, movieID)){
					writeDB();
					return;
				}
		System.out.println("Error! Failed to update movie screening!");
	}

	/**
	 * Remove a movie screening of a specified Cineplex's Cinema.
	 * @param cineplexID	ID of the cineplex to remove movie screening from.
	 * @param cinemaID		ID of the cinema to remove movie screening from.
	 * @param screeningID	ID of the movie screening to be removed.
	 */
	public void removeMovieScreening(int cineplexID, int cinemaID, int screeningID){
		if (cineplexID >= cineplexList.size() || cineplexID < 0){
			System.out.println("Error! Invalid Cineplex!");
			return;
		}
		for (Cinema cinema : cineplexList.get(cineplexID).getCinemaList())
			if (cinema.getCinemaID() == cinemaID)
				if(cinema.removeMovieScreening(screeningID)){
					writeDB();
					return;
				}
		System.out.println("Error! Failed to add movie screening!");
	}

	/**
	 * Gets the channel reference of the CineplexController.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the Cineplex Controller
	 */
	public static CineplexController getInstance(){
		if (instance == null)
			instance = new CineplexController();
		return instance;
	}

}
