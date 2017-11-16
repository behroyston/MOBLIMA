package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Represents a Booking which captures the moviegoer details and transactionID.
 * @author Royston
 * @version 1.0
 * @since 2017-11-06
 */
public class Booking {
	/**
	 * Unique Transaction ID for each Booking.
	 */
    private String transactionId;

    /**
     * Name of the moviegoer.
     */
    private String userName;

    /**
     * Mobile number of the moviegoer.
     */
    private String mobileNum;

    /**
     * Email address of the moviegoer.
     */
    private String emailAddress;

    /**
     * Cinema ID of the booking.
     */
    private int cinemaID;

    /**
     * Movie screening ID of the booking.
     */
    private int movieScreeningID;

    /**
	 * Creates a new Booking with its details. It will create the respective transaction ID.
     * @param movieScreeningID	The Movie screening ID of the booking.
     * @param userName			Name of the moviegoer.
     * @param mobileNum			Mobile number of the moviegoer.
     * @param emailAddress		Email address of the moviegoer.
     * @param cinemaID			CinemaID of the booking.
     */
    public Booking(int movieScreeningID, String userName, String mobileNum, String emailAddress, int cinemaID){
        this.movieScreeningID = movieScreeningID;
        this.userName = userName;
        this.mobileNum = mobileNum;
        this.emailAddress = emailAddress;
        this.cinemaID = cinemaID;
        formatTransactionID();
    }

    /**
     * Creates a new booking with its details. This constructor is called by the loading of database.
     * @param movieScreeningID	The Movie screening ID of the booking.
     * @param userName			Name of the moviegoer.
     * @param mobileNum			Mobile number of the moviegoer.
     * @param emailAddress		Email address of the moviegoer.
     * @param transactionId		TransactionID of the booking.
     * @param cinemaID			CinemaID of the booking.		
     */
    public Booking(int movieScreeningID, String userName, String mobileNum, String emailAddress, String transactionId, int cinemaID){
        this.movieScreeningID = movieScreeningID;
        this.userName = userName;
        this.mobileNum = mobileNum;
        this.emailAddress = emailAddress;
        this.cinemaID = cinemaID;
        this.transactionId = transactionId;
    }

    /**
     * Get the transactionID of the booking.
     * @return	TransactionID of the booking.
     */
    public String getTransactionID(){
        return transactionId;
    }

    /**
     * Format the transactionID appropriately for storage of this booking.
     */
    private void formatTransactionID(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        transactionId = String.format("%03d%s", cinemaID, dateFormat.format(date));
    }

    /**
     * Get the email address of the moviegoer who made this booking.
     * @return	Email address of the moviegoer.
     */
    public String getEmailAddress(){
        return this.emailAddress;
    }

    /**
     * Get the movie screening ID of the booking.
     * @return	Movie screening ID of the booking.
     */
    public int getMovieScreeningID() {
        return movieScreeningID;
    }

    /**
     * Get the name of the moviegoer who made this booking.
     * @return	Name of the moviegoer.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get the mobile number of the moviegoer who made this booking.
     * @return	Mobile number of the moviegoer.
     */
    public String getMobileNum() {
        return mobileNum;
    }
    
    /**
     * Get the cinemaID of the booking.
     * @return	CinemaID of the booking.
     */
    public int getCinemaID() {
        return cinemaID;
    }
    
    /**
     * Prints the booking information.
     */
    public void printInfo(){
        System.out.println("Transaction ID:" + transactionId);
        System.out.println("Username: " + userName);
        System.out.println("Mobile Number: " + mobileNum);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("CinemaID: " + cinemaID);
        System.out.println("MovieScreeningID: " + movieScreeningID);


    }
}
