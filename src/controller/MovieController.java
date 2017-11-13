package controller;

import model.Movie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MovieController extends DatabaseController{

    private String DIR = "movie/";

    private String NIL = "nil";
    /**
     * the instance of this database, default is NULL
     */
    private static MovieController Instance = null;

    private ArrayList<Movie> movieList;

    /**
     * controller.MovieController can only be created itself
     */
    private MovieController() {
        movieList = new ArrayList<>();
    }
    /**
     * create new database if there's no database
     * @return controller.MovieController
     */
    public static MovieController getInstance() {
        if (Instance == null)
            Instance = new MovieController();
        return Instance;
    }

    @Override
    public void readDB() {
        movieList.clear();
        if (checkDirectoryExist(BASEDIR + DIR)) {
            try{
                for (File f : getListofFiles(BASEDIR + DIR)) {
                    List<String> text = retrieveData(BASEDIR + DIR + "Movie.dat");
                        StringTokenizer aStr;
                        Movie movie;
                        for (String line : text) {
                            aStr = new StringTokenizer(line, DELIMITER);
                            int movieID = Integer.parseInt(aStr.nextToken());
                            String movieName = aStr.nextToken();
                            String status = aStr.nextToken();
                            String synopsis = aStr.nextToken();
                            String director = aStr.nextToken();
                            String cast = aStr.nextToken();
                            double avg_rating = Double.parseDouble(aStr.nextToken());
                            boolean isShowing = Boolean.parseBoolean(aStr.nextToken());
                            double ticketSales = Double.parseDouble(aStr.nextToken());
                            int duration = Integer.parseInt(aStr.nextToken());
                            String review = aStr.nextToken();
                            ArrayList<String> reviewList = new ArrayList<>();
                            if (review.equals(NIL)){
                                review = aStr.nextToken();
                            }
                            else {
                                while (!isDouble(review) && !review.equals(NIL))
                                {
                                    reviewList.add(review);
                                    review = aStr.nextToken();
                                }
                            }
                            String rating = review;
                            ArrayList<Double> ratingList = new ArrayList<>();
                            // if there are ratings after reviews, parse it to double
                            if (rating.equals(NIL)){
                            }
                            else{
                                while (isDouble(rating))
                                {
                                    ratingList.add(Double.parseDouble(rating));
                                    if (!aStr.hasMoreTokens()) {
                                        break;
                                    }
                                    rating = aStr.nextToken();
                                }
                            }
                            System.out.println("MovieAdded!");
                            movie = new Movie(movieID,movieName,synopsis,director,cast,status,avg_rating,isShowing,ticketSales,duration,reviewList,ratingList);
                            movieList.add(movie);
                        }
                    }

            } catch (IOException io) {
                System.out.println("Error! Unable to retrieve model from file.");
            }
        }

        else
        {
            System.out.println("Error, Directory not found! Database for Booking is not loaded!");
        }

    }

    @Override
    public void writeDB() {
        List<String> text = new ArrayList<>();
        StringBuilder str = new StringBuilder();
		/* Summary: Text in order: int movieId, String movieName, String status, String synopsis, String director, String cast, double avg_rating,
		boolean isShowing, double ticketSales, int duration, ArrayList<String> reviews, ArrayList<Double> ratingList
        */
        if (checkDirectoryExist(BASEDIR + DIR)) {
            for (Movie movie : movieList) {
                str.setLength(0);
                str.append(movie.getMovieId());
                str.append(DELIMITER);
                str.append(movie.getMovieName());
                str.append(DELIMITER);
                str.append(movie.getStatus());
                str.append(DELIMITER);
                str.append(movie.getSynopsis());
                str.append(DELIMITER);
                str.append(movie.getDirector());
                str.append(DELIMITER);
                str.append(movie.getCast());
                str.append(DELIMITER);
                str.append(movie.getAvg_rating());
                str.append(DELIMITER);
                str.append(movie.isShowing());
                str.append(DELIMITER);
                str.append(movie.getTicketSales());
                str.append(DELIMITER);
                str.append(movie.getDuration());
                str.append(DELIMITER);
                int sizeOfMovieReviews = movie.getReviews().size();
                if (sizeOfMovieReviews == 0){
                    str.append(NIL);
                    str.append(DELIMITER);
                }
                else {
                    for (int i = 0; i < sizeOfMovieReviews ; i++) {
                        str.append(movie.getReviews().get(i));
                        str.append(DELIMITER);
                    }
                }
                int sizeOfMovieRatings = movie.getRatingList().size();
                if (sizeOfMovieRatings == 0){
                    str.append(NIL);
                    str.append(DELIMITER);
                }
                else {
                    for (int i = 0; i < sizeOfMovieRatings; i++) {
                        str.append(movie.getRatingList().get(i));
                        str.append(DELIMITER);
                    }
                }
                text.add(str.toString());
                System.out.println("MovieAdded!");
            }
                // Attempt to save to file
                try {
                    saveData(BASEDIR + DIR + "Movie" + ".dat", text); // Write to file
                } catch (Exception ex) {
                    System.out.println("Error! Unable to write to file!");
                }

        } else {
            System.out.println("Error! Directory cannot be found!");
        }
    }

    //add this method to class diagram
    /**
     * exposes the private directory to initialize the retrieval of model.
     * @return controller.MovieController path
     */
    public String getDir(){
        return DIR;
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public ArrayList<Movie> getMovieList(){
        return movieList;
    }

    /**
     * add model.Movie into the movieList after staff has key in all the details required by the StaffUI.
     * @param movieId
     * @param movieName
     * @param synopsis
     * @param director
     * @param cast
     */
    public void addMovie(int movieId, String movieName, String synopsis, String director, String cast, String status){
        Movie newMovie = new Movie(movieId, movieName, synopsis, director, cast, status);
        movieList.add(newMovie);
    }

    /**
     * get the model.Movie using movieId
     * @param movieId
     * @return model.Movie
     */
     public Movie getMovie(int movieId){
         for (Movie movie : movieList)
             if (movie.getMovieId()== movieId)
                 return movie;
         return null;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public void printMovieLists(){
        System.out.println("Size of movie: " + movieList.size());
        for (int i = 0; i < movieList.size();i++){
            movieList.get(i).printInfo();
        }

    }
}