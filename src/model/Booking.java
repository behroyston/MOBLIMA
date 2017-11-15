package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {

    private String transactionId;

    private String userName;

    private String mobileNum;

    private String emailAddress;

    private int cinemaID;

    private int movieScreeningID;

    public Booking(int movieScreeningID, String userName, String mobileNum, String emailAddress, int cinemaID){
        this.movieScreeningID = movieScreeningID;
        this.userName = userName;
        this.mobileNum = mobileNum;
        this.emailAddress = emailAddress;
        this.cinemaID = cinemaID;
        formatTransactionID();
    }

    public Booking(int movieScreeningID, String userName, String mobileNum, String emailAddress, String transactionId, int cinemaID){
        this.movieScreeningID = movieScreeningID;
        this.userName = userName;
        this.mobileNum = mobileNum;
        this.emailAddress = emailAddress;
        this.cinemaID = cinemaID;
        this.transactionId = transactionId;
    }

    // returns the transactionID of this booking.
    public String getTransactionID(){
        return transactionId;
    }

    // format the transactionID appropriately for storage of this model.
    private void formatTransactionID(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        transactionId = String.format("%03d%s", cinemaID, dateFormat.format(date));
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public int getMovieScreeningID() {
        return movieScreeningID;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void printInfo(){
        System.out.println("Transaction ID:" + transactionId);
        System.out.println("Username: " + userName);
        System.out.println("Mobile Number: " + mobileNum);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("CinemaID: " + cinemaID);
        System.out.println("MovieScreeningID: " + movieScreeningID);


    }
}
