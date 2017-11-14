package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Movie{
	private int movieId;
	private String movieName;
	private String status;
	private String synopsis;
	private String director;
	private String cast;
	private double avg_rating;
	private ArrayList<String> reviews;
	private ArrayList<Double> ratingList;
	private boolean isShowing;
	private double ticketSales;
	private int duration;

	public Movie(int movieId, String movieName, String synopsis, String director, String cast, String status){
		this.movieId = movieId;
		this.movieName = movieName;
		this.synopsis = synopsis;
		this.director = director;
		this.cast = cast;
		this.status = status;
		this.reviews = new ArrayList<>();
		this.ratingList = new ArrayList<>();
	}

	public Movie(int movieId, String movieName, String synopsis, String director, String cast, String status, double avg_rating, boolean isShowing, double ticketSales, int duration,
			ArrayList<String> reviews, ArrayList<Double> ratingList){
		this.movieId = movieId;
		this.movieName = movieName;
		this.synopsis = synopsis;
		this.director = director;
		this.cast = cast;
		this.status = status;
		this.avg_rating = avg_rating;
		this.isShowing = isShowing;
		this.ticketSales = ticketSales;
		this.duration = duration;
		this.reviews = reviews;
		this.ratingList = ratingList;

	}

	public int getMovieId() {
		return movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public String getStatus() {
		return status;
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

	public double getTicketSales() {
		return ticketSales;
	}

	public int getDuration() {
		return duration;
	}

	public void setStatus(String status) {
		this.status = status;
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
		System.out.println("Movie Status: " + status);
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