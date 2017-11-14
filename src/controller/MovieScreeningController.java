package controller;

import model.MovieClassType;
import model.MovieScreening;
import model.Seat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 */
public class MovieScreeningController extends DatabaseController {

    private String DIR = "moviescreening/";
    private static MovieScreeningController instance = null;
    private ArrayList<MovieScreening> movieScreenings;

    private MovieScreeningController(){
        movieScreenings = new ArrayList<>();
        readDB();
    }

    public static MovieScreeningController getInstance() {
        if (instance == null)
            instance = new MovieScreeningController();
        return instance;
    }

    /** Summary: Text in order:  int movieScreening_ID, int cinemaID, int movieID, Calendar startTime, Calendar endTime ,
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
                                    mSeats);
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


    /** Each line in the Moviescreenings.dat is a moviescreening object.
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

    public ArrayList<MovieScreening> getMovieScreenings(){
        return movieScreenings;
    }

    /**
     * Returns true if the new movieScreening is added successfully.
     *
     * @param cinemaID an absolute ID which identifies the unique cinema.
     * @param movieID an absolute ID which identifies the unique movie.
     * @param startTime the starting time of the movie
     * @return
     */
    public boolean addMovieScreening(int cinemaID, int movieID, Calendar startTime, MovieClassType movieType)
    {
        Calendar endTime = (Calendar)(startTime.clone());
        endTime.add(Calendar.MINUTE, MovieController.getInstance().getMovie(movieID).getDuration());
        System.out.println(startTime.getTime());
        System.out.println(endTime.getTime());
        for (int i = 0; i < movieScreenings.size(); i ++){
            MovieScreening movieScreening = movieScreenings.get(i);
            if (movieScreening.getCinemaID() == cinemaID){
            	// Consider changing this. We only accept the add IF
            	// 1) the start time is after ALL of the endTime OR
            	// 2) the end time is before ALL of the startTime
                /*if (!(((startTime.compareTo(movieScreening.getStartTime())== 1) && ((endTime.compareTo(movieScreening.getEndTime())) == 1)) ||
                        (((startTime.compareTo(movieScreening.getStartTime())) == -1) && ((endTime.compareTo(movieScreening.getEndTime())) == -1)))) {
                    System.out.println("This moviescreening's timing clashes with another moviescreening's timing!");
                    return false;
                }*/
            	// Take negation, so if there exists one existing movieScreening such that the new start time is before or equal to this movie screening
            	// end time AND the new end time is after or equal to this movie screening
                if (!startTime.after(movieScreening.getEndTime()) && !endTime.before(movieScreening.getStartTime())){
                	System.out.println("This moviescreening's timing clashes with another moviescreening's timing!");
                    return false;
                }
            }
        }
        MovieScreening newMovieScreening = new MovieScreening(movieScreenings.size()+1,startTime, endTime, movieType, cinemaID,movieID);
        movieScreenings.add(newMovieScreening);
        writeDB();
        return true;
    }

    /**
     * update the movieScreening given the new CinemaID, movieID and startTime
     * @param movieScreeningID the unique movieScreeningID to be updated.
     * @param newCinemaID an absolute ID which identifies the unique cinema.
     * @param newMovieID an absolute ID which identifies the unique movie.
     * @param newStartTime the new starting time of the movie
     * @return
     */
    public boolean updateMovieScreening(int movieScreeningID, int newCinemaID, int newMovieID, Calendar newStartTime,
                                        MovieClassType movieType)
    {
        MovieScreening oldMovieScreening = removeMovieScreening(movieScreeningID);
        if (addMovieScreening(newCinemaID,newMovieID, newStartTime, movieType) && oldMovieScreening != null) {
            return true;
        }
        else if (oldMovieScreening != null){
            addMovieScreening(oldMovieScreening.getCinemaID(),oldMovieScreening.getMovieID(),
                    oldMovieScreening.getStartTime(), movieType);
            return false;
        }
        return false;
    }

    /**
     * removes the movieScreening based on its unique ID.
     * if movieScreening is not found, return null object
     * @param movieScreeningID
     * @return
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
     * check through all the movieScreenings to update if currentTime > endTime.
     * if yes, set isExpired = true.
     * @return
     */
    public void updateExpiry(){}

    public void setMovieScreenings(ArrayList<MovieScreening> movieScreenings) {
        this.movieScreenings = movieScreenings;
    }

    public void printMovieScreenings(){
        for (int i = 0; i < movieScreenings.size(); i ++){
            movieScreenings.get(i).printMovieScreeningInfo();
        }
    }
    
    public MovieScreening getMovieScreeningByScreeningID(int screeningID){
    	for (MovieScreening movieScreening : movieScreenings)
    		if (movieScreening.getMovieScreeningID() == screeningID)
    			return movieScreening;
    	return null;
    }

    public ArrayList<MovieScreening> getMovieScreeningsByMovieID(int movieID){
        ArrayList<MovieScreening> movieScreeningList = new ArrayList<>();
        for (int i = 0; i < this.movieScreenings.size(); i++) {
            MovieScreening movieScreening = movieScreenings.get(i);
            if (movieID == movieScreening.getMovieID())
            {
                movieScreeningList.add(movieScreening);
            }
        }
        return movieScreenings;
    }


}
