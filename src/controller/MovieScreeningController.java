package controller;

import model.MovieScreening;
import model.Seat;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class MovieScreeningController extends DatabaseController {

    private String DIR = "moviescreening/";
    private static MovieScreeningController instance = null;
    private ArrayList<MovieScreening> movieScreenings;

    private MovieScreeningController(){
        movieScreenings = new ArrayList<>();
    }

    public static MovieScreeningController getInstance() {
        if (instance == null)
            instance = new MovieScreeningController();
        return instance;
    }

    @Override
    public void readDB(){
        movieScreenings.clear();
        if (checkDirectoryExist(BASEDIR + DIR)) {
            File folder = new File(BASEDIR + DIR);
            File[] listOfFiles = folder.listFiles();
            /* Summary: Text in order:  int movieScreening_ID, int cinemaID, int movieID, Calendar startTime, Calendar endTime ,
            String movieType, isExpired, Seat[][] seats;
            **/
            try{
                for (File f : listOfFiles) {
                    if (f.isFile() && f.getName().equals("MovieScreening.dat")) {
                        List<String> text = retrieveData(BASEDIR + DIR + "MovieScreenings.dat");
                        StringTokenizer aStr;
                        MovieScreening movieScreening;

                        for (String line : text) {
                            // model.MovieScreening Attributes
                            aStr = new StringTokenizer(line, DELIMITER);
                            int movieScreeningID  = Integer.parseInt(aStr.nextToken());     // movieScreeningID
                            int cinemaID = Integer.parseInt(aStr.nextToken());      // cinemaID
                            int movieID = Integer.parseInt(aStr.nextToken());       // movieID
                            Calendar startTime = Calendar.getInstance();
                            startTime.setTimeInMillis(Long.parseLong(aStr.nextToken()));    // Start Time
                            Calendar endTime = Calendar.getInstance();
                            endTime.setTimeInMillis(Long.parseLong(aStr.nextToken()));        // End Time
                            String movieType = aStr.nextToken();                            // movieType
                            boolean isExpired = Boolean.parseBoolean(aStr.nextToken()); // isExpired
<<<<<<< HEAD

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
||||||| merged common ancestors
                            String[][] seats = new String[20][20];
                            String[] seat;
                            String[][] movieSeats;
                            int x = 0;
                            do {
                                seat = aStr.nextToken().split("");
                                seats[x] = seat;
                                x++;
                            } while (seat != null);
                            int y = seats[0].length;
                            Seat[][] movieScreeningSeats = new Seat[x+1][y];
                            for (int i = 0; i < y-1; i ++) {
                                for (int j = 0; j < x; j++) {
                                    Seat movieSeat = new Seat(j*i+(j+1));
                                    if (seats[j][i].equals("X")) {
=======
                            // model.Seat Layout
                            char[][] seatLayout = new char[8][15];
                            int i = 0;
                            while (aStr.hasMoreTokens()) {
                                String col = aStr.nextToken();
                                for (int j = 0; j < col.length(); j++)
                                    seatLayout[i][j] = col.charAt(j);
                                i++;
                            }
                            String[][] seats = new String[20][20];
                            String[] seat;
                            String[][] movieSeats;
                            int x = 0;
                            do {
                                seat = aStr.nextToken().split("");
                                seats[x] = seat;
                                x++;
                            } while (seat != null);
                            int y = seats[0].length;
                            Seat[][] movieScreeningSeats = new Seat[x+1][y];
                            for (int i = 0; i < y-1; i ++) {
                                for (int j = 0; j < x; j++) {
                                    Seat movieSeat = new Seat(j*i+(j+1));
                                    if (seats[j][i].equals("X")) {
>>>>>>> fetch_head
                                        movieSeat.setIsBooked(true);
                                        mSeats[i][j] = movieSeat;
                                    }
                                }
                                i++;
                            }
//                            int y = seats[0].length;
//                            Seat[][] movieScreeningSeats = new Seat[x+1][y];
//                            for (int i = 0; i < y-1; i ++) {
//                                for (int j = 0; j < x; j++) {
//                                    Seat movieSeat = new Seat(j*i+(j+1));
//                                    if (seats[j][i].equals("X")) {
//                                        movieSeat.setIsBooked(true);
//                                        movieScreeningSeats[j][i] = movieSeat;
//                                    }
//                                    else if (seats[j][i].equals("O"))
//                                        movieScreeningSeats[j][i] = movieSeat;
//                                }
//                            }

                            movieScreening = new MovieScreening(movieScreeningID,startTime,endTime,movieType,cinemaID,movieID,
                                    mSeats);
                            movieScreenings.add(movieScreening);
                        }
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

    @Override
    public void writeDB() {

        List<String> text = new ArrayList<>();
        StringBuilder str = new StringBuilder();
		/* Summary: Text in order:  int movieScreening_D, int cinemaID, int movieID, Calendar startTime, Calendar endTime ,
		String movieType, isExpired, Seat[][] seats;
        */
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
                str.append(movieScreening.getMovieType());
                str.append(DELIMITER);
                str.append(Boolean.toString(movieScreening.getIsExpired()));
                str.append(DELIMITER);

                char [][] seatLayout = cinema.getSeatLayout();
                for (int i = 0; i < seatLayout.length; i++){
                    for (int j = 0; j < seatLayout[i].length; j++){
                        str.append(seatLayout[i][j]);
                    }
                    str.append(DELIMITER);
                }
                Seat[][] seats = movieScreening.getSeats();
                int x = seats[0].length;
                int y = seats.length;
                for (int i = 0; i < y; i++) {
                    for (int j = 0; j < x; j++) {
<<<<<<< HEAD
                        if (j == 7)
                            str.append(" ");
                        else if (seats[i][j].getIsBooked())
                            str.append("X");
||||||| merged common ancestors
                        if (seats[j][i].getIsBooked())
                            System.out.print("X");
=======
                        if (seats[j][i].getIsBooked())
                            str.append("X");
>>>>>>> fetch_head
                        else
                            str.append("O");
                    }
                    str.append(DELIMITER);
                }

                // model.Seat layout of the cinema
                //char [][] seatLayout = cinema.getSeatLayout();
//                for (int i = 0; i < seatLayout.length; i++){
//                    for (int j = 0; j < seatLayout[i].length; j++){
//                        str.append(seatLayout[i][j]);
//                    }
//                    str.append(DELIMITER);
//                }
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
    public boolean addMovieScreening(int cinemaID, int movieID, Calendar startTime, String movieType)
    {
        Calendar endTime = startTime;
        endTime.add(Calendar.MINUTE, MovieController.getInstance().getMovie(movieID).getDuration());
        for (int i = 0; i < movieScreenings.size() - 1 ; i ++){
            MovieScreening movieScreening = movieScreenings.get(i);
            if (movieScreening.getCinemaID() == cinemaID){
                // if startTime is lesser than one of the moviescreening's endTime
                if (startTime.compareTo(movieScreening.getEndTime()) == -1) {
                    System.out.println("This moviescreening's timing clashes with another moviescreening's timing!");
                    return false;
                }
            }
        }
        MovieScreening newMovieScreening = new MovieScreening(movieScreenings.size()-1,startTime, endTime, movieType, cinemaID,movieID);
        movieScreenings.add(newMovieScreening);
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
                                        String movieType)
    {
        MovieScreening oldMovieScreening = removeMovieScreening(movieScreeningID);
        if (addMovieScreening(newCinemaID,newMovieID, newStartTime, movieType) && oldMovieScreening != null)
            return true;
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
        for (int i = 0; i < movieScreenings.size() - 1; i++)
        {
            if (movieScreenings.get(i).getMovieScreeningID() == movieScreeningID) {
                return movieScreenings.remove(i);
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
}
