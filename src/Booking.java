import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {

    private String transactionId;

    private String userName;

    private int mobileNum;

    private String emailAddress;

    private int cinemaID;

    private MovieScreening movieScreening;

    public Booking(MovieScreening movieScreening, String userName, int mobileNum, String emailAddress, int cinemaID){
        this.movieScreening = movieScreening;
        this.userName = userName;
        this.mobileNum = mobileNum;
        this.emailAddress = emailAddress;
        this.cinemaID = cinemaID;
        setTransactionID();
    }

    // returns the transactionID of this booking.
    public String getTransactionID(){
        return transactionId;
    }

    // format the transactionID appropriately for storage of this data.
    private void setTransactionID(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        transactionId = (cinemaID + dateFormat.format(date));
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

}
