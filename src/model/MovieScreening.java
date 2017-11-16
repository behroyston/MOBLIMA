package model;

import java.util.Calendar;

/**
 * Represents a Movie screening which contains its attributes like showtime, movie type,
 * and the seats available for booking.
 * @author Royston
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieScreening {
	/**
	 * Unique identifier of the movie screening
	 */
	private int movieScreeningID;

	/**
	 * MovieID of the movie screened
	 */
	private int movieID;

	/**
	 * CinemaID of the cinema the movie is screened
	 */
	private int cinemaID;

	/**
	 * Start time of the movie screening
	 */
	private Calendar startTime;

	/**
	 * End time of the movie screening
	 */
	private Calendar endTime;

	/**
	 * Class type of the movie screening (2D or 3D)
	 */
	private MovieClassType movieClassType;

	/**
	 * Seats available for booking
	 */
	private Seat[][] seats;

	/**
	 * Condition to check if the movie screening is still available for booking
	 */
	private boolean isExpired;

	/**
	 * Creates a new Movie Screening with its attributes. It will check based on this if the movie is available for booking.
	 * @param movieScreeningID	Movie Screening ID of the movie screening.
	 * @param startTime			Starting time of the movie screening.
	 * @param endTime			End time of the movie screening.
	 * @param movieType			Type of the movie screening.
	 * @param cinemaID			CinemaID of the movie being screened at.
	 * @param movieID			MovieID of the movie being screened.
	 */
	public MovieScreening(int movieScreeningID, Calendar startTime, Calendar endTime, MovieClassType movieType,
			int cinemaID, int movieID){
		this.movieScreeningID = movieScreeningID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.cinemaID = cinemaID;
		this.movieClassType = movieType;
		this.movieID = movieID;
		isExpired = false;

		// for now it's 8 by 15
		char[][] seatLayout = new char[8][15];
		//CineplexController.getInstance().getCinema(cinemaID).getSeatLayout();
		int x = seatLayout[0].length;
		int y = seatLayout.length;
		seats = new Seat[y][x];
		for (int i = 0; i < y; i++){
			for (int j = 0; j < x; j++) {
				if (j != 7) {
					seats[i][j] = new Seat(j * i + (j + 1));
				}
			}
		}
	}

	/**
	 * Creates a new Movie Screening with its attributes. This constructor is called by the loading of database.
	 * @param movieScreeningID	Movie Screening ID of the movie screening.
	 * @param startTime			Starting time of the movie screening.
	 * @param endTime			End time of the movie screening.
	 * @param movieType			Class type of the movie being screened.
	 * @param cinemaID			CinemaID of the movie being screened at.
	 * @param movieID			MovieID of the movie being screened.
	 * @param seats				Seats of the movie that is available for booking
	 * @param isExpired			If the movie screening is still available for booking.
	 */
	public MovieScreening(int movieScreeningID, Calendar startTime, Calendar endTime, MovieClassType movieType,
			int cinemaID, int movieID, Seat[][] seats, boolean isExpired){
		this.movieScreeningID = movieScreeningID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.cinemaID = cinemaID;
		this.movieClassType = movieType;
		this.movieID = movieID;
		this.seats = seats;
		this.isExpired = isExpired;
	}

	/**
	 * Get the movie class type of this screening.
	 * @return	Movie class type.
	 */
	public MovieClassType getMovieClassType() {
		return movieClassType;
	}

	/**
	 * Set the movie class type of this screening.
	 * @param movieClassType	New movie class type.
	 */
	public void setMovieClassType(MovieClassType movieClassType) {
		this.movieClassType = movieClassType;
	}

	/**
	 * Get the starting time of this screening.
	 * @return	Starting time of the movie screening.
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * Set the starting time of this screening.
	 * @param startTime	New starting time of the movie screening.
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * Get the ending time of this screening.
	 * @return	Ending time of the movie screening.
	 */
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * Set the ending time of this screening.
	 * @param endTime	New ending time of the movie screening.
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * Get the cinema ID of the movie screening being screened at.
	 * @return	CinemaID of the movie screening.
	 */
	public int getCinemaID() {
		return cinemaID;
	}

	/**
	 * Set the cinema ID of the movie screening being screened at.
	 * @param cinemaID New cinemaID of the movie screening.
	 */
	public void setCinemaID(int cinemaID) {
		this.cinemaID = cinemaID;
	}


	/**
	 * Get the movieID of the movie being screened.
	 * @return	MovieID of the movie being screened.
	 */
	public int getMovieID() {
		return movieID;
	}

	/**
	 * Set the movieID of the movie being screened.
	 * @param movieID of the movie being screened.
	 */
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}

	/**
	 * Get whether the movie screening is available for booking.
	 * @return	true if the movie screening is NOT available for booking. false otherwise.
	 */
	public boolean getIsExpired() {
		return isExpired;
	}

	/**
	 * Set whether the movie screening is available for booking.
	 * @param expired	true to set the movie screening as NOT available for booking. false otherwise.
	 */
	public void setIsExpired(boolean expired) {
		isExpired = expired;
	}

	/**
	 * Get the seats plan for the movie screening.
	 * @return	Seats plan for the movie screening.
	 */
	public Seat[][] getSeats() {
		return seats;
	}

	/**
	 * Get the Unique identifier of the movie screening.
	 * @return	MovieScreeningID of the movie screening.
	 */
	public int getMovieScreeningID() {
		return movieScreeningID;
	}

	/**
	 * Set the Unique identifier of the movie screening.
	 * @param movieScreeningID MovieScreeningID of the movie screening.
	 */
	public void setMovieScreeningID(int movieScreeningID) {
		this.movieScreeningID = movieScreeningID;
	}

	/**
	 * Print the movie screening seats information.
	 */
	public void printMovieScreeningSeatsInfo(){
		for (int i = 0; i < seats.length; i++){
			System.out.print(i+1 + " ");
			for (int j = 0; j < seats[i].length; j++)
				if (j == 7) {
					System.out.print(" ");
				}
				else if (!seats[i][j].getIsBooked())
					System.out.print("O");
				else
					System.out.print("X");
			System.out.println();
		}
	}

	/**
	 * Print this movie screening information.
	 */
	public void printMovieScreeningInfo(){
		System.out.println("MovieScreeningID: " + movieScreeningID);
		System.out.println("MovieID: " + movieID);
		System.out.println("CinemaID: " + cinemaID);
		System.out.println("Movie Class Type: " + movieClassType);
		System.out.println("Time: " + startTime.getTime() + " - " + endTime.getTime());

	}

	/**
	 * Gets the start date time in a formatted format.
	 * @return	Formatted start date and time of the movie screening.
	 */
	public String getStartDateTime(){
		Calendar cal = startTime;
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);      // 0 to 11
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%4d/%02d/%02d %02d:%02d",
				year, month+1, day, hour, minute);
	}
}
