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

public class MovieController extends DatabaseController{

	private String DIR = "movie/";

	private String NIL = "nil";
	/**
	 * the instance of this database, default is NULL
	 */
	private static MovieController instance = null;

	private static ArrayList<Movie> movieList;

	/**
	 * controller.MovieController can only be created itself
	 */
	private MovieController() {
		movieList = new ArrayList<>();
		readDB();
	}
	/**
	 * create new database if there's no database
	 * @return controller.MovieController
	 */
	public static MovieController getInstance() {
		if (instance == null)
			instance = new MovieController();
		checkAllMovies();
		return instance;
	}

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
				str.append(movie.isShowing());
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
	
	public ArrayList<Movie> getShowingMovieList(){
		ArrayList<Movie> showingMovies = new ArrayList<>(movieList);
		for (int i = showingMovies.size() - 1 ; i >= 0; i--)
			if (!showingMovies.get(i).isShowing())
				showingMovies.remove(i);
		return showingMovies;
	}
	

	/**
	 * add model.Movie into the movieList after staff has key in all the details required by the StaffUI.
	 * @param movieId
	 * @param movieName
	 * @param synopsis
	 * @param director
	 * @param cast
	 */
	public void addMovie(int movieId, String movieName, String synopsis, String director, String cast, MovieShowingStatus status, int duration){
		Movie newMovie = new Movie(movieId, movieName, synopsis, director, cast, status, duration);
		movieList.add(newMovie);
		writeDB();
	}
	
	public void updateMovie(int movieId, String newMovieName, String newSynopsis, String newdirector, String newCast, MovieShowingStatus newStatus, int duration){
		Movie oldMovie = removeMovie(movieId);
		if (oldMovie != null)
			addMovie(movieId, newMovieName, newSynopsis, newdirector, newCast, newStatus, duration);
		writeDB();
	}
	
	
	/**
         * removes the movie based on its unique ID.
         * if movie is not found, return null object
         * @param movieId
         * @return
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

	// Add this to class diagram
	public Movie getShowingMovieByName(String movieName){
		for (Movie movie : movieList)
			if (movie.getMovieName().equalsIgnoreCase(movieName) && movie.isShowing())
				return movie;
		return null;
	}
	
	public void updateMovieEndOfShowing(int movieID, Calendar showingEndDate){
		getMovie(movieID).setShowingEndDate(showingEndDate);
		writeDB();
	}
	
	// Add this to class diagram
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
	
	// Add this to class diagram
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
	
	public void updateMovieSales(int movieID, double sales){
		getMovie(movieID).addTicketSales(sales);
		writeDB();
	}
	
	public void addMovieReview(int movieID, String review){
		getMovie(movieID).addReview(review);
		writeDB();
	}
	
	public void addMovieRating(int movieID, double rating){
		getMovie(movieID).addRating(rating);
		writeDB();
	}
	
	public void setMovieList(ArrayList<Movie> newMovieList) {
		movieList = newMovieList;
	}

	private static void checkAllMovies(){
		for (Movie movie : movieList)
			movie.checkIfBookable();
	}
	
	public void printMovieLists(){
		System.out.println("Size of movie: " + movieList.size());
		for (int i = 0; i < movieList.size();i++){
			movieList.get(i).printInfo();
		}
	}

	public void printMovieNames() {
		for (int i = 0; i < movieList.size(); i++) {
			if (movieList.get(i).getStatus() != MovieShowingStatus.END_OF_SHOWING)
				System.out.println((i + 1) + ") " + movieList.get(i).getMovieName() + " - " + movieList.get(i).getStatus());
		}
	}
}
