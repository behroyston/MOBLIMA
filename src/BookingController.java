import java.util.ArrayList;

public class BookingController extends DatabaseController{

    private String DIR = "booking/";

    private ArrayList<Booking> bookingList;

    private static BookingController Instance = null;

    private BookingController() { }

    public static BookingController getInstance() {
        if (Instance == null)
            Instance = new BookingController();
        return Instance;
    }

    @Override
    public void readDB() {

    }

    @Override
    public void writeDB() {

    }

    // exposes the private directory to initialize the retrieval of data.
    public String getDir(){
        return DIR;
    }

    public ArrayList<Booking> getBookingList(){
        return bookingList;
    }

    /**
     *
     * @param userName
     * @param mobileNum
     * @param emailAddress
     * @param cinemaID
     * @param movieScreening
     * Add Booking into the BookingList after user has key in all the details required by the MovieGoerUI.
     */
    public void addBooking(String userName, int mobileNum, String emailAddress, int cinemaID, MovieScreening movieScreening){
        //
        Booking newBooking = new Booking(movieScreening,userName,mobileNum,emailAddress,cinemaID);
        bookingList.add(newBooking);
        updateSales(movieScreening.getMovieId());
    }

    /**
     * Update the total sales of the Movie by using the MovieID.
     */
    private void updateSales(int movieID){
        // calls MovieController.get(movieID).addSales(SystemSettings.money);
    }
}
