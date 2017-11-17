package controller;

import model.Movie;
import model.MovieClassType;
import model.MovieScreening;
import model.MovieShowingStatus;
import model.Seat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A controller to perform storing and retrieval of MovieScreening to/from database.
 * Also validates changes before committing any changes to database.
 * @author Royston
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieScreeningController extends DatabaseController {
	/**
	 * The sub-directory of the storage of Movie Screenings.
	 */
    private String DIR = "moviescreening/";
    
	/**
	 * Instance of the MovieScreening controller.
	 */
    private static MovieScreeningController instance = null;
    
	/**
	 * List of the Movie Screenings.
	 */
    private static ArrayList<MovieScreening> movieScreenings;
    
	/**
	 * Creates a new MovieScreening Controller.
	 * Also initialize the list of movie screenings and read from database.
	 */
    private MovieScreeningController(){
        movieScreenings = new ArrayList<>();
        readDB();
    }

    /** 
     * Retrieve the movie screenings from database.
     * The order is as follows:  int movieScreening_ID, int cinemaID, int movieID, Calendar startTime, Calendar endTime ,
     * String movieType, isExpired, Seat[][] seats;
     * These variables are parsed into the movie screening objects and stored when the MovieScreening Controller is
     * initalized.
     */
    @Override
    protected void readDB(){
        movieScreenings.clear();
        if (checkDirectoryExist(BASEDIR + DIR)) {
            try{
                for (File f : getListofFiles(BASEDIR + DIR)) {
                    List<String> text = retrieveData(BASEDIR + DIR + "MovieScreenings.dat");
                        StringTokenizer aStr;
                        MovieScreening movieScreening;

                        for (String line : text) {
                            // model.MovieScreening Attributes
                            aStr = new StringTokenizer(line, DELIMITER);
                            int movieScreeningID  = Integer.parseInt(aStr.nextToken());     // movieScreeningID
                            int cinemaID = Integer.parseInt(aStr.nextToken());      // cinemaID
                            int movieID = Integer.parseInt(aStr.nextToken());       // movieID
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(Long.parseLong(aStr.nextToken()));    // Start Time
                            Calendar startTime = (Calendar)cal.clone();
                            cal.setTimeInMillis(Long.parseLong(aStr.nextToken()));        // End Time
                            MovieClassType movieType = MovieClassType.valueOf(aStr.nextToken());                            // movieType
                            boolean isExpired = Boolean.parseBoolean(aStr.nextToken()); // isExpired

                            Seat[][] mSeats = new Seat[8][15];
                            char[][] seatLayout = new char[8][15];
                            int i = 0;
                            while (aStr.hasMoreTokens()) {
                                String col = aStr.nextToken();
                                for (int j = 0; j < col.length(); j++) {
                                    char c = col.charAt(j);
                                    if (c == 'O')
                                        mSeats[i][j] = new Seat(j * i + (j + 1));
                                    else if (c == 'X') {
                                        Seat movieSeat = new Seat(j * i + (j + 1));
                                        movieSeat.setIsBooked(true);
                                        mSeats[i][j] = movieSeat;
                                    }
                                }
                                i++;
                            }

                            movieScreening = new MovieScreening(movieScreeningID,startTime,cal,movieType,cinemaID,movieID,
                                    mSeats, isExpired);
                            movieScreenings.add(movieScreening);
                        }
                    }

            }
            catch (IOException io){
                System.out.println("Error! Unable to retrieve model from file.");
            }

        } else {
            System.out.println("Error, Directory not found! Database for MovieScreening is not loaded!");
        }
    }


    /** 
     *  Save the MovieScreening model into database. Each line in the Moviescreenings.dat file is a moviescreening object.
     * Summary: Text in order:  int movieScreening_ID, int cinemaID, int movieID, Calendar startTime, Calendar endTime ,
     * String movieType, isExpired, Seat[][] seats;
     * These will be appended into the MovieScreenings.dat file in sequence.
     */
    @Override
    protected void writeDB() {

        List<String> text = new ArrayList<>();
        StringBuilder str = new StringBuilder();

        for (MovieScreening movieScreening : movieScreenings) {
            if (checkDirectoryExist(BASEDIR + DIR)) {
                // model.MovieScreening Attributes
                str.setLength(0); // Reset Buffer
                str.append(movieScreening.getMovieScreeningID());        // movieScreeningID
                str.append(DELIMITER);
                str.append(movieScreening.getCinemaID()); // cinemaID
                str.append(DELIMITER);
                str.append(movieScreening.getMovieID()); // movieID
                str.append(DELIMITER);
                str.append(movieScreening.getStartTime().getTimeInMillis()); // store Calendar startTime in milliseconds
                str.append(DELIMITER);
                str.append(movieScreening.getEndTime().getTimeInMillis()); // store Calendar endTime in milliseconds
                str.append(DELIMITER);
                str.append(movieScreening.getMovieClassType());
                str.append(DELIMITER);
                str.append(Boolean.toString(movieScreening.getIsExpired()));
                str.append(DELIMITER);

                Seat[][] seats = movieScreening.getSeats();
                int x = seats[0].length;
                int y = seats.length;
                for (int i = 0; i < y; i++) {
                    for (int j = 0; j < x; j++) {
                        if (j == 7)
                            str.append(" ");
                        else if (seats[i][j].getIsBooked())
                            str.append("X");
                        else
                            str.append("O");
                    }
                    str.append(DELIMITER);
                }

                text.add(str.toString());            // Write to line

                // Attempt to save to file
                try {
                    saveData(BASEDIR + DIR + "MovieScreenings" + ".dat", text); // Write to file
                } catch (Exception ex) {
                    System.out.println("Error! Unable to write to file!");
                }
            } else {
                System.out.println("Error! Directory cannot be found!");
            }
        }
    }

    /**
     * Get list of the movie screenings.
     * @return	List of the movie screenings.
     */
    public ArrayList<MovieScreening> getMovieScreenings(){
        return movieScreenings;
    }

    /**
     * Add a movie screening to a cinema. It will validates if the screening clashes with other 
     * screenings in the same cinema.
     * @param movieScreeningID	an absolute ID which identifies the unique movie screening.
     * @param cinemaID			an absolute ID which identifies the unique cinema.
     * @param movieID			an absolute ID which identifies the unique movie.
     * @param startTime			the starting time of the movie.
     * @param movieType			Movie type of screening (2D or 3D)
     * @return					true of the new movie screening is added successfully. false otherwise.
     */
    public boolean addMovieScreening(int movieScreeningID, int cinemaID, int movieID, Calendar startTime, MovieClassType movieType)
    {
        Calendar endTime = (Calendar)(startTime.clone());
        endTime.add(Calendar.MINUTE, MovieController.getInstance().getMovie(movieID).getDuration());
        System.out.println(startTime.getTime());
        System.out.println(endTime.getTime());
        for (int i = 0; i < movieScreenings.size(); i ++){
            MovieScreening movieScreening = movieScreenings.get(i);
            if (movieScreening.getCinemaID() == cinemaID){
            	// If there exists one existing movieScreening such that the new start time is before or equal to this movie screening
            	// end time AND the new end time is after or equal to this movie screening's start time means there is a time clash
                if (!startTime.after(movieScreening.getEndTime()) && !endTime.before(movieScreening.getStartTime())){
                	System.out.println("This moviescreening's timing clashes with another moviescreening's timing!");
                    return false;
                }
            }
        }
        // if movie is created instead of updated
        if (movieScreeningID == -1) {
            movieScreeningID = movieScreenings.size()+1;
        }
        MovieScreening newMovieScreening = new MovieScreening(movieScreeningID,startTime, endTime, movieType, cinemaID,movieID);

        movieScreenings.add(newMovieScreening);
        writeDB();
        return true;
    }

    /**
     * Update the movieScreening given the new CinemaID, movieID and new starting time and movieType.
     * It will validate if the change is acceptable before committing the change (such as no time clash).
     * @param movieScreeningID 	the unique movieScreeningID to be updated.
     * @param newCinemaID 		an absolute ID which identifies the unique cinema.
     * @param newMovieID 		an absolute ID which identifies the unique movie.
     * @param newStartTime 		the new starting time of the movie
     * @param movieType			Movie type of screening (2D or 3D)
     * @return					true of the movie screening is updated successfully. false otherwise.
     */
    public boolean updateMovieScreening(int movieScreeningID, int newCinemaID, int newMovieID, Calendar newStartTime,
                                        MovieClassType movieType)
    {
        MovieScreening oldMovieScreening = removeMovieScreening(movieScreeningID);
        if (addMovieScreening(movieScreeningID,newCinemaID,newMovieID, newStartTime, movieType) && oldMovieScreening != null) {
            return true;
        }
        else if (oldMovieScreening != null){
            addMovieScreening(movieScreeningID,oldMovieScreening.getCinemaID(),oldMovieScreening.getMovieID(),
                    oldMovieScreening.getStartTime(), movieType);
            return false;
        }
        return false;
    }

    /**
     * Removes the movieScreening based on its unique ID. It will validate it the movie object exists in the database.
     * @param movieScreeningID	the unique movieScreeningID to be removed.
     * @return					MovieScreening object of the movie screening if it is successfully removed. null object otherwise.
     */
    public MovieScreening removeMovieScreening(int movieScreeningID){
        for (int i = 0; i < movieScreenings.size(); i++)
        {
            if (movieScreenings.get(i).getMovieScreeningID() == movieScreeningID) {
                MovieScreening movieScreening = movieScreenings.remove(i);
                writeDB();
                return movieScreening;
            }
        }
        return null;
    }

    /**
     * Check through all the movieScreenings to update if currentTime > startTime and if the movie has reached end of showing.
     * If yes, set isExpired = true.
     */
    private static void updateExpiry(){
    	for (MovieScreening movieScreening : movieScreenings){
    		Movie movie = MovieController.getInstance().getMovie(movieScreening.getMovieID());
    		if (Calendar.getInstance().after(movieScreening.getStartTime()) || movie.getStatus() == MovieShowingStatus.END_OF_SHOWING)
    			movieScreening.setIsExpired(true);
    	}
    }

    /**
     * Print all the movie screenings info.
     */
    public void printMovieScreenings(){
        for (int i = 0; i < movieScreenings.size(); i ++){
        	if (!movieScreenings.get(i).getIsExpired())
        		movieScreenings.get(i).printMovieScreeningInfo();
        }
    }
    
    /**
     * Get the movie screening by its screening ID
     * @param screeningID	the unique screeningID of the movie screening.
     * @return				Movie screening object if it exists. null object if it cannot be found.
     */
    public MovieScreening getMovieScreeningByScreeningID(int screeningID){
    	for (MovieScreening movieScreening : movieScreenings)
    		if (movieScreening.getMovieScreeningID() == screeningID)
    			return movieScreening;
    	return null;
    }

    /**
     * Find all movie screenings of a movie.
     * @param movieID	the unique movieID of the movie.
     * @return			List of movie screenings of the movie.
     */
    public ArrayList<MovieScreening> getMovieScreeningsByMovieID(int movieID){
        ArrayList<MovieScreening> movieScreeningList = new ArrayList<>();
        for (MovieScreening movieScreening : movieScreenings) {
            if (movieID == movieScreening.getMovieID())
            {
                movieScreeningList.add(movieScreening);
            }
        }
        return movieScreeningList;
    }

    /**
     * Set a set for particular Moviescreening to booked. It will check if it is indeed bookable.
     * @param movieScreeningID	the unique movieScreeningID to be set the seat.
     * @param verticalIndex		the index of the seat in Y-axis
     * @param horizontalIndex	the index of the seat in X-axis
     * @return					true if it is set successfully to booked. false otherwise.
     */
    public boolean setSeatSelected(int movieScreeningID, int verticalIndex, int horizontalIndex){
        Seat[][] movieScreeningSeats = getMovieScreeningByScreeningID(movieScreeningID).getSeats();
        Seat seat = movieScreeningSeats[verticalIndex][horizontalIndex];
        if (seat.getIsBooked()){
            return false;
        }
        else {
            seat.setIsBooked(true);
            writeDB();
            return true;
        }
    }

    /**
	 * Gets the channel reference of the MovieScreeningController.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the MovieScreeningController.
	 */
    public static MovieScreeningController getInstance() {
        if (instance == null)
            instance = new MovieScreeningController();
    	updateExpiry();
        return instance;
    }

    /*public void setMovieScreenings(ArrayList<MovieScreening> movieScreenings) {
        this.movieScreenings = movieScreenings;
    }*/
}
