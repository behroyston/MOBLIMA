package model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a Movie which contains its listings.
 * @author Meiru
 * @version 1.0
 * @since 2017-11-06
 */
public class Movie{
	/**
	 * MovieID of the movie
	 */
	private int movieId;

	/**
	 * Name of the movie.
	 */
	private String movieName;

	/**
	 * Showing Status of the movie.
	 */
	private MovieShowingStatus movieShowingStatus;

	/**
	 * Synopsis of the movie.
	 */
	private String synopsis;

	/**
	 * Director of the movie.
	 */
	private String director;

	/**
	 * Cast of the movie.
	 */
	private String cast;

	/**
	 * Overall rating of the movie
	 */
	private double avg_rating;

	/**
	 * Reviews of the movie.
	 */
	private ArrayList<String> reviews;

	/**
	 * Ratings of the movie.
	 */
	private ArrayList<Double> ratingList;

	/**
	 * Status of the movie if it can be booked.
	 */
	private boolean isShowing;

	/**
	 * Ticket Sales of the movie.
	 */
	private double ticketSales;

	/**
	 * Duration of the movie.
	 */
	private int duration;

	/**
	 * Showing end date of the movie.
	 */
	private Calendar showingEndDate;

	/**
	 * Creates a new Movie with its listings. It will check based on this if the movie is available for booking.
	 * @param movieId	MovieID of the movie.
	 * @param movieName	Movie name.
	 * @param synopsis	Synopsis of the movie.
	 * @param director	Director of the movie.
	 * @param cast		Cast of the movie.
	 * @param status	Showing status of the movie.
	 * @param duration	Duration of the movie.
	 */
	public Movie(int movieId, String movieName, String synopsis, String director, String cast, MovieShowingStatus status, int duration){
		this.movieId = movieId;
		this.movieName = movieName;
		this.synopsis = synopsis;
		this.director = director;
		this.cast = cast;
		this.movieShowingStatus = status;
		this.reviews = new ArrayList<>();
		this.ratingList = new ArrayList<>();
		this.duration = duration;
		this.showingEndDate = null;
		checkIfBookable();
	}

	/**
	 * Creates a new Movie with its listings. This constructor is called by the loading of database.
	 * @param movieId			MovieID of the movie.
	 * @param movieName			Movie name.
	 * @param synopsis			Synopsis of the movie.
	 * @param director			Director of the movie.
	 * @param cast				Cast of the movie.
	 * @param status			Showing status of the movie.
	 * @param avg_rating		Overall rating of the movie.
	 * @param isShowing			Status of the movie if it can be booked.
	 * @param ticketSales		Ticket sales of the movie.
	 * @param duration			Duration of the movie.
	 * @param showingEndDate	Showing end date of the movie.
	 * @param reviews			Reviews of the movie.
	 * @param ratingList		Ratings of the movie.
	 */
	public Movie(int movieId, String movieName, String synopsis, String director, String cast, MovieShowingStatus status, double avg_rating, boolean isShowing, double ticketSales, int duration,
			Calendar showingEndDate, ArrayList<String> reviews, ArrayList<Double> ratingList){
		this.movieId = movieId;
		this.movieName = movieName;
		this.synopsis = synopsis;
		this.director = director;
		this.cast = cast;
		this.movieShowingStatus = status;
		this.avg_rating = avg_rating;
		this.isShowing = isShowing;
		this.ticketSales = ticketSales;
		this.duration = duration;
		this.showingEndDate = showingEndDate;
		this.reviews = reviews;
		this.ratingList = ratingList;
		checkIfBookable();
	}

	/**
	 * Get the movieID of the movie.
	 * @return MovieID of the movie.
	 */
	public int getMovieId() {
		return movieId;
	}

	/**
	 * Get the movie name of the movie.
	 * @return	Movie name of the movie.
	 */
	public String getMovieName() {
		return movieName;
	}

	/**
	 * Get the showing status of the movie.
	 * @return	Showing status of the movie.
	 */
	public MovieShowingStatus getStatus() {
		return movieShowingStatus;
	}
	
	/**
	 * Get the synopsis of the movie.
	 * @return	Synopsis of the movie
	 */
	public String getSynopsis() {
		return synopsis;
	}

	/**
	 * Get the director of the movie.
	 * @return	Directory of the movie.
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Get the cast of the movie.
	 * @return	Cast of the movie.
	 */
	public String getCast() {
		return cast;
	}

	/**
	 * Get the overall rating of the movie.
	 * @return	Overall rating of the movie.
	 */
	public double getAvg_rating() {
		return avg_rating;
	}

	/**
	 * Get the reviews of the movie.
	 * @return	Reviews of the movie.
	 */
	public ArrayList<String> getReviews() {
		return reviews;
	}

	/**
	 * Get the ratings of the movie.
	 * @return	Ratings of the movie.
	 */
	public ArrayList<Double> getRatingList() {
		return ratingList;
	}

	/**
	 * Get the availability of the movie for booking.
	 * @return	Availability of the movie for booking.
	 */
	public boolean getIsShowing() {
		return isShowing;
	}

	/**
	 * Set the showing end date of the movie.
	 * @param showingEndDate	Showing End Date of the movie.
	 */
	public void setShowingEndDate(Calendar showingEndDate){
		this.showingEndDate = showingEndDate;
	}

	/**
	 * Get the showing end date of the movie.
	 * @return	Showing End Date of the movie.
	 */
	public Calendar getShowingEndDate(){
		return showingEndDate;
	}

	/**
	 * Update the availability of the movie for booking.
	 */
	public void checkIfBookable(){
		if (showingEndDate != null && Calendar.getInstance().after(showingEndDate))
			movieShowingStatus = MovieShowingStatus.END_OF_SHOWING;
		isShowing = movieShowingStatus == MovieShowingStatus.PREVIEW || movieShowingStatus == MovieShowingStatus.NOW_SHOWING;
	}

	/**
	 * Get the ticket sales of the movie.
	 * @return	Ticket sales of the movie.
	 */
	public double getTicketSales() {
		return ticketSales;
	}

	/**
	 * Get the duration of the movie.
	 * @return	Duration of the movie.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Set the showing status of the movie.
	 * @param status	Showing status of the movie.
	 */
	public void setStatus(MovieShowingStatus status) {
		this.movieShowingStatus = status;
		checkIfBookable();
	}

	/**
	 * Set the duration of the movie.
	 * @param duration	Duration of the movie.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 *	Get the overall rating of the movie.
	 * @return Overall rating of the movie.
	 */
	public double getRating(){
		return avg_rating;
	}

	/**
	 * Calculate and update overall rating
	 */
	private void setAvgRating(){
		double sum = 0;
		for (double rating : ratingList)
			sum += rating;
		avg_rating = sum/ratingList.size();
	}

	/**
	 * Add a review to the movie.
	 * @param review	Review for the movie.
	 */
	public void addReview(String review){
		reviews.add(review);
	}

	/**
	 * Add a rating to the movie.
	 * @param rating	Rating for the movie.
	 */
	public void addRating(double rating){
		ratingList.add(rating);
		setAvgRating();
	}

	/**
	 * Add sales to movie.
	 * @param sales	Sales of the movie.
	 */
	public void addTicketSales(double sales){
		ticketSales += sales;
	}

	/**
	 * Print out the movie details
	 */
	public void printInfo(){
		System.out.println("MovieID: " + movieId);
		System.out.println("MovieName: " + movieName);
		System.out.println("Movie Status: " + movieShowingStatus);
		System.out.println("Movie Synopsis: " + synopsis);
		System.out.println("Movie Director: " + director);
		System.out.println("Movie Cast: " + cast);
		System.out.println("Movie Showing Status: " + isShowing);
		System.out.println("Movie Average Rating: " + avg_rating);
		System.out.println("Movie Reviews: ");
		for (int i = 0; i < reviews.size(); i++){
			System.out.println(reviews.get(i));
		}
		System.out.println("Movie ratings: ");
		for (int i = 0; i < ratingList.size(); i++){
			System.out.println(ratingList.get(i));
		}
		System.out.println("End of Movie Details");

	}

}