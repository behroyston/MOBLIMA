package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Movie{
	private int movieId;
	private String movieName;
	private MovieShowingStatus movieShowingStatus;
	private String synopsis;
	private String director;
	private String cast;
	private double avg_rating;
	private ArrayList<String> reviews;
	private ArrayList<Double> ratingList;
	private boolean isShowing;
	private double ticketSales;
	private int duration;
	private Calendar showingEndDate;

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

	public int getMovieId() {
		return movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public MovieShowingStatus getStatus() {
		return movieShowingStatus;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getDirector() {
		return director;
	}

	public String getCast() {
		return cast;
	}

	public double getAvg_rating() {
		return avg_rating;
	}

	public ArrayList<String> getReviews() {
		return reviews;
	}

	public ArrayList<Double> getRatingList() {
		return ratingList;
	}

	public boolean isShowing() {
		return isShowing;
	}
	
	public void setShowingEndDate(Calendar showingEndDate){
		this.showingEndDate = showingEndDate;
	}
	
	public Calendar getShowingEndDate(){
		return showingEndDate;
	}
	
	public void checkIfBookable(){
		if (showingEndDate != null && Calendar.getInstance().after(showingEndDate))
			movieShowingStatus = MovieShowingStatus.END_OF_SHOWING;
		isShowing = movieShowingStatus == MovieShowingStatus.PREVIEW || movieShowingStatus == MovieShowingStatus.NOW_SHOWING;
	}

	public double getTicketSales() {
		return ticketSales;
	}

	public int getDuration() {
		return duration;
	}

	public void setStatus(MovieShowingStatus status) {
		this.movieShowingStatus = status;
		checkIfBookable();
	}

	public void setAvg_rating(double avg_rating) {
		this.avg_rating = avg_rating;
	}

	public void setShowing(boolean showing) {
		isShowing = showing;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @return average rating
	 */
	 public double getRating(){
		return avg_rating;
	}

	/**
	 * calculate and update average rating
	 */
	 private void setAvgRating(){
		double sum = 0;
		for (double rating : ratingList)
			sum += rating;
		avg_rating = sum/ratingList.size();
	}

	public void addReview(String review){
		reviews.add(review);
	}

	public void addRating(double rating){
		ratingList.add(rating);
		setAvgRating();
	}

	public void addTicketSales(double sales){
		ticketSales += sales;
	}

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