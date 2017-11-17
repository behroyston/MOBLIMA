package controller;

import model.Movie;
import model.MovieSalesComparator;
import model.MovieRatingsComparator;
import model.MovieShowingStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Collections;

/**
 * A controller to perform storing and retrieval of Movie to/from database.
 * Also validates changes before committing any changes to database.
 * @author Meiru
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieController extends DatabaseController{
	/**
	 * The sub-directory of the storage of Bookings.
	 */
	private String DIR = "movie/";

	/**
	 * Constant to denote the non-entry.
	 */
	private final String NIL = "nil";

	/**
	 * List of the movies.
	 */
	private static ArrayList<Movie> movieList;

	/**
	 * Instance of the movie controller.
	 */
	private static MovieController instance = null;


	/**
	 * Creates a new Movie Controller.
	 * Also initialize the list of movies and read from database.
	 */
	private MovieController() {
		movieList = new ArrayList<>();
		readDB();
	}

	/**
	 * Retrieve the movies from database.
	 * The order is as follows: MovieID, Movie Name, Showing Status, Synopsis, Directory, Cast, Overall rating, Bookable, Ticket Sales, 
	 * Duration, End Date of Showing, Reviews, Ratings.
	 * These variables are parsed into the Movie objects and stored when the Movie Controller is initalized.
	 */
	@Override
	protected void readDB() {
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
						MovieShowingStatus status = MovieShowingStatus.valueOf(aStr.nextToken());
						String synopsis = aStr.nextToken();
						String director = aStr.nextToken();
						String cast = aStr.nextToken();
						double avg_rating = Double.parseDouble(aStr.nextToken());
						boolean isShowing = Boolean.parseBoolean(aStr.nextToken());
						double ticketSales = Double.parseDouble(aStr.nextToken());
						int duration = Integer.parseInt(aStr.nextToken());
						String showStr = aStr.nextToken();
						Calendar showingEndDate;
						if (showStr.equals("NONE"))
							showingEndDate = null;
						else{
							showingEndDate = (Calendar) Calendar.getInstance().clone();
							showingEndDate.setTimeInMillis(Long.parseLong(showStr));
						}
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
						movie = new Movie(movieID,movieName,synopsis,director,cast,status,avg_rating,isShowing,ticketSales,duration,showingEndDate,reviewList,ratingList);
						movieList.add(movie);
					}
				}

			} catch (IOException | NumberFormatException ex) {
				System.out.println("Error! Unable to retrieve Movie model from file.");
			}
		}

		else
		{
			System.out.println("Error, Directory not found! Database for Booking is not loaded!");
		}

	}

	/** Save the Movie model into database. Each line in the Movie.dat file is a booking object.
	 * The order is as follows: MovieID, Movie Name, Showing Status, Synopsis, Directory, Cast, Overall rating, Bookable, Ticket Sales, 
	 * Duration, End Date of Showing, Reviews, Ratings.
	 * These will be appended into the Movie.dat file in sequence.
	 */
	@Override
	protected void writeDB() {
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
				str.append(movie.getIsShowing());
				str.append(DELIMITER);
				str.append(movie.getTicketSales());
				str.append(DELIMITER);
				str.append(movie.getDuration());
				str.append(DELIMITER);
				if (movie.getShowingEndDate() == null)
					str.append("NONE");
				else
					str.append(movie.getShowingEndDate().getTimeInMillis());
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

	/**
	 * Check if the input is double.
	 * @param value	Value to check.
	 * @return		True if it is double. False otherwise.
	 */
	private boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Get the list of all the movies.
	 * @return	List of all movies.
	 */
	public ArrayList<Movie> getMovieList(){
		return movieList;
	}

	/**
	 * Get the list of all movies that is available for booking (Preview or Now Showing).
	 * @return	List of the bookable movies.
	 */
	public ArrayList<Movie> getShowingMovieList(){
		ArrayList<Movie> showingMovies = new ArrayList<>(movieList);
		for (int i = showingMovies.size() - 1 ; i >= 0; i--)
			if (!showingMovies.get(i).getIsShowing())
				showingMovies.remove(i);
		return showingMovies;
	}

	/**
	 * Add Movie into the database after staff has key in all the details required by the StaffUI.
	 * @param movieId	MovieID of the movie.
	 * @param movieName	Name of the movie.
	 * @param synopsis	Synopsis of the movie.
	 * @param director	Director of the movie.
	 * @param cast		Cast of the movie.
	 * @param status	Showing Status of the movie.
	 * @param duration	Duration of the movie.
	 */
	public void addMovie(int movieId, String movieName, String synopsis, String director, String cast, MovieShowingStatus status, int duration){
		Movie newMovie = new Movie(movieId, movieName, synopsis, director, cast, status, duration);
		movieList.add(newMovie);
		writeDB();
	}

	/**
	 * Modify an existing movie in the database. It will validate if the movie exists before committing the change.
	 * @param movieId		MovieID of the movie to change.
	 * @param newMovieName	New Movie Name of the movie.
	 * @param newSynopsis	New Synopsis of the movie.
	 * @param newdirector	New Director name of the movie.
	 * @param newCast		New cast of the movie.
	 * @param newStatus		New Showing status of the movie.
	 * @param duration		Duration of the movie.
	 */
	public void updateMovie(int movieId, String newMovieName, String newSynopsis, String newdirector, String newCast, MovieShowingStatus newStatus, int duration){
		Movie oldMovie = removeMovie(movieId);
		if (oldMovie != null)
			addMovie(movieId, newMovieName, newSynopsis, newdirector, newCast, newStatus, duration);
		writeDB();
	}


	/**
	 * Removes the movie based on its unique MovieID. It will validate if the movie exists in the database.
	 * @param movieId	MovieID of the movie to remove.
	 * @return			Removed Movie object if it exists. null object if movie is not found.
	 */
	public Movie removeMovie(int movieId){
		for (int i = 0; i < movieList.size(); i++)
		{
			if (movieList.get(i).getMovieId() == movieId) {
				Movie movie = movieList.remove(i);
				writeDB();
				return movie;
			}
		}
		return null;
	}


	/**
	 * Get the Movie using its MovieID.
	 * @param movieId		MovieID of the movie to retrieve.
	 * @return Movie		Movie object if it exists. null object if it is not found.
	 */
	public Movie getMovie(int movieId){
		for (Movie movie : movieList)
			if (movie.getMovieId()== movieId)
				return movie;
		return null;
	}

	/**
	 * Find a movie that is currently available for booking by its name.
	 * @param movieName	Movie Name of the movie.
	 * @return			Movie object if it exists. null object if it is not found.
	 */
	public Movie getShowingMovieByName(String movieName){
		for (Movie movie : movieList)
			if (movie.getMovieName().equalsIgnoreCase(movieName) && movie.getIsShowing())
				return movie;
		return null;
	}

	/**
	 * Update a movie for its end of showing date.
	 * @param movieID			Movie ID of the movie.
	 * @param showingEndDate	End date of the movie.
	 */
	public void updateMovieEndOfShowing(int movieID, Calendar showingEndDate){
		getMovie(movieID).setShowingEndDate(showingEndDate);
		writeDB();
	}

	/**
	 * Prints out the top 5 movies that are available for booking by sales.
	 */
	public void printTopFiveBySales(){
		ArrayList<Movie> sortedMovieList = new ArrayList<>(getShowingMovieList());
		Collections.sort(sortedMovieList, new MovieSalesComparator());
		String[] postfix = {"st", "nd", "rd", "th"};
		int size = Math.min(5, sortedMovieList.size());
		for (int i = 1; i <= size; i++){
			Movie movie = sortedMovieList.subList(0, size).get(i-1);
			System.out.println(i + postfix[Math.min(i, postfix.length)-1] + ": " + movie.getMovieName() + 
					" (Ticket Sales - $" + movie.getTicketSales() + ")");
		}
	}

	/**
	 * Prints out the top 5 movies that are available for booking by ratings.
	 */
	public void printTopFiveByRatings(){
		ArrayList<Movie> sortedMovieList = new ArrayList<>(getShowingMovieList());
		Collections.sort(sortedMovieList, new MovieRatingsComparator());
		String[] postfix = {"st", "nd", "rd", "th"};
		int size = Math.min(5, sortedMovieList.size());
		for (int i = 1; i <= size; i++){
			Movie movie = sortedMovieList.subList(0, size).get(i-1);
			System.out.println(i + postfix[Math.min(i, postfix.length)-1] + ": " + movie.getMovieName() + 
					" (Overall Rating - " + movie.getAvg_rating() + "/5)");
		}
	}

	/**
	 * Update a movie sales.
	 * @param movieID	MovieID of the movie.
	 * @param sales		Sales to add to a movie.
	 */
	public void updateMovieSales(int movieID, double sales){
		getMovie(movieID).addTicketSales(sales);
		writeDB();
	}

	/**
	 * Add a review and rating to a movie.
	 * @param movieID	MovieID of the movie.
	 * @param review	Review to add to a movie.
	 * @param rating	Rating to add to a movie.
	 */
	public void addMovieReviewRating(int movieID, String review, double rating){
		Movie movie = getMovie(movieID);
		movie.addReview(review);
		movie.addRating(rating);
		writeDB();
	}

	/** 
	 * Update all movies if it is bookable.
	 */
	private static void checkAllMovies(){
		for (Movie movie : movieList)
			movie.checkIfBookable();
	}
	
	/**
	 * Prints out all the movie names and showing status.
	 */
	public void printMovieLists(){
		System.out.println("Size of movie: " + movieList.size());
		for (int i = 0; i < movieList.size();i++){
			movieList.get(i).printInfo();
		}
	}
	
	/**
	 * Print out all the movies and their information.
	 */
	public void printMovieNames() {
		for (int i = 0; i < movieList.size(); i++) {
			if (movieList.get(i).getStatus() != MovieShowingStatus.END_OF_SHOWING)
				System.out.println((i + 1) + ") " + movieList.get(i).getMovieName() + " - " + movieList.get(i).getStatus());
		}
	}

	/**
	 * Gets the channel reference of the MovieController.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the Movie Controller.
	 */
	public static MovieController getInstance() {
		if (instance == null)
			instance = new MovieController();
		checkAllMovies();
		return instance;
	}
	
	/**
	 * Checks for movieID clashes prior to creation of new movie listing.
	 * @param movieId movieID of the movie.
	 * @return true/false for clash
	 */
	public boolean checkMovieIDClash(int movieId) {
		for (int i = 0; i < movieList.size(); i++) {
			if (movieList.get(i).getMovieId() == movieId)
				return true;
		}
		return false;
	}
	
	/*	public void setMovieList(ArrayList<Movie> newMovieList) {
		movieList = newMovieList;
	}*/
}
