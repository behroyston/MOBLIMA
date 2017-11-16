package controller;

import model.Booking;
import model.Cinema;
import model.CinemaClassType;
import model.MovieClassType;
import model.MovieScreening;
import model.SystemSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A controller to perform storing and retrieval of Booking to/from database.
 * Also validates changes before committing any changes to database.
 * @author Royston
 * @version 1.0
 * @since 2017-11-06
 */
public class BookingController extends DatabaseController{
	/**
	 * The sub-directory of the storage of Bookings.
	 */
	private String DIR = "booking/";

	/**
	 * List of the bookings.
	 */
	private ArrayList<Booking> bookingList;

	/**
	 * Instance of the booking database controller.
	 */
	private static BookingController instance = null;

	/**
	 * Creates a new Booking Controller.
	 * Also initialize the list of booking and read from database.
	 */
	private BookingController() {
		bookingList = new ArrayList<>();
		readDB();
	}

	/**
	 * Retrieve the Bookings from database.
	 * The order is as follows: String transactionId , int movieScreeningID, String userName, String mobileNum,
	 * String emailAddress
	 * These variables are parsed into the Booking objects and stored when the Booking Controller is initalized.
	 */
	@Override
	protected void readDB() {
		bookingList.clear();
		if (checkDirectoryExist(BASEDIR + DIR)) {
			try{
				for (File f : getListofFiles(BASEDIR + DIR)) {
					List<String> text = retrieveData(BASEDIR + DIR + "Bookings.dat");
					StringTokenizer aStr;
					Booking booking;
					for (String line : text) {
						// model.MovieScreening Attributes
						aStr = new StringTokenizer(line, DELIMITER);
						String transactionID = aStr.nextToken();
						int movieScreeningID = Integer.parseInt(aStr.nextToken());
						String userName = aStr.nextToken();
						String mobileNum = aStr.nextToken();
						String emailAddress = aStr.nextToken();
						int cinemaID = Integer.parseInt(aStr.nextToken());
						booking = new Booking(movieScreeningID,userName,mobileNum,emailAddress,transactionID, cinemaID);
						bookingList.add(booking);
					}
				}

			}
			catch (IOException  | NumberFormatException ex)
			{
				System.out.println("Error! Unable to retrieve Booking model from file.");
			}
		}
		else
		{
			System.out.println("Error, Directory not found! Database for Booking is not loaded!");
		}

	}

	/** Save the Booking model into database. Each line in the Bookings.dat file is a booking object.
	 * The order is as follows: String transactionId , int movieScreeningID, String userName, String mobileNum, String emailAddress, int cinemaID
	 * These will be appended into the Bookings.dat file in sequence.
	 */
	@Override
	protected void writeDB() {
		List<String> text = new ArrayList<>();
		StringBuilder str = new StringBuilder();

		for (Booking booking : bookingList) {
			if (checkDirectoryExist(BASEDIR + DIR)) {
				str.setLength(0); // Reset Buffer
				str.append(booking.getTransactionID());
				str.append(DELIMITER);
				str.append(booking.getMovieScreeningID());
				str.append(DELIMITER);
				str.append(booking.getUserName());
				str.append(DELIMITER);
				str.append(booking.getMobileNum());
				str.append(DELIMITER);
				str.append(booking.getEmailAddress());
				str.append(DELIMITER);
				str.append(booking.getCinemaID());
				str.append(DELIMITER);
				text.add(str.toString());

				// Attempt to save to file
				try {
					saveData(BASEDIR + DIR + "Bookings" + ".dat", text); // Write to file
				} catch (Exception ex) {
					System.out.println("Error! Unable to write to file!");
				}
			}
			else {
				System.out.println("Error! Directory cannot be found!");
			}
		}

	}

	/**
	 * Get the all of the bookings.
	 * @return List of bookings.
	 */
	public ArrayList<Booking> getBookingList(){
		return bookingList;
	}

	/**
	 * Add Booking into the BookingList after the MovieGoer has confirmed the booking.
	 * It captures the name, mobile number, email address, cinema ID and movie screening ID of the MovieGoer.
	 * It will also updates the sales for the movie booked by the MovieGoer.
	 * @param userName			Name of the MovieGoer
	 * @param mobileNum			Mobile number of the MovieGoer
	 * @param emailAddress		Email address of the MovieGoer
	 * @param cinemaID			Cinema ID of the movie screening booked by the MovieGoer
	 * @param movieScreeningID	Movie Screening ID of the movie screening booked by the MovieGoer
	 * @param price				Total amount paid by the MovieGoer
	 */
	public void addBooking(String userName, String mobileNum, String emailAddress, int cinemaID, int movieScreeningID, double price){
		//
		Booking newBooking = new Booking(movieScreeningID,userName,mobileNum,emailAddress,cinemaID);
		bookingList.add(newBooking);
		updateSales(MovieScreeningController.getInstance().getMovieScreeningByScreeningID(movieScreeningID).getMovieID(), price);
		writeDB();
		System.out.println("Thank you! Your booking is confirmed!(Transaction ID: " + newBooking.getTransactionID() + ")");
	}

	/**
	 * Gets all the bookings made by the MovieGoer.
	 * @param emailAddress	Email address of the MovieGoer.
	 * @return				List of bookings made by the MovieGoer.
	 */
	public ArrayList<Booking> getAllBookingByUser(String emailAddress){
		ArrayList<Booking> userBookings = new ArrayList<>();
		for (Booking booking : bookingList)
			if (emailAddress.equalsIgnoreCase(booking.getEmailAddress()))
				userBookings.add(booking);
		return userBookings;
	}

	/**
	 * Computes the cost of the booking made by the MovieGoer inclusive of 7% GST.
	 * @param type				Integer value 1 for Children Discount, 2 for Senior Discount. Adult otherwise.
	 * @param movieScreeningID	Movie Screening ID of the booking made by the MovieGoer.
	 * @return					Total cost of the booking inclusive of GST.
	 */
	public double calculatePrice(int type, int movieScreeningID){
		SystemSettings sysSettings = SystemSettings.getInstance();
		MovieScreening movieScreening = MovieScreeningController.getInstance().getMovieScreeningByScreeningID(movieScreeningID);
		CinemaClassType classType = CineplexController.getInstance().getCinema(movieScreening.getCinemaID()).getCinemaClassType();
		double price = sysSettings.getBasePrice();

		// Children or Senior discount
		if (type == 1)
			price -= sysSettings.getChildDiscount();
		else if (type == 2)
			price -= sysSettings.getSeniorDiscount();

		// Cinema Type
		if (classType == CinemaClassType.GOLDCLASS)
			price += sysSettings.getGoldExtra();
		else if (classType == CinemaClassType.PLATINUM)
			price += sysSettings.getPlatinumExtra();

		// 2D or 3D
		if (movieScreening.getMovieClassType() == MovieClassType.CLASS3D)
			price += sysSettings.getThreeDExtra();
		
		Calendar cal = movieScreening.getStartTime();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		boolean isHoliday = false;
		
		// Check if holiday
		for (Calendar holiday : sysSettings.getHolidays())
			isHoliday = cal.get(Calendar.YEAR) == holiday.get(Calendar.YEAR) &&
			cal.get(Calendar.DAY_OF_YEAR) == holiday.get(Calendar.DAY_OF_YEAR);

		// Weekend or Holiday
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY || isHoliday)
			price += sysSettings.getWeekend_HolidayExtra();

		return price*1.07;
	}

	/**
	 * Update the total sales of the Movie by using the MovieID.
	 * @param movieID	Movie ID of the movie booked by the MovieGoer.
	 * @param price		Sales of the movie.
	 */
	private void updateSales(int movieID, double price){
		MovieController.getInstance().updateMovieSales(movieID, price);
	}

	/**
	 * Gets the channel reference of the BookingController.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the Booking Controller.
	 */
	public static BookingController getInstance() {
		if (instance == null)
			instance = new BookingController();
		return instance;
	}
	
	/*	public void printBookingList(){
		for (int i = 0; i < bookingList.size(); i++){
			bookingList.get(i).printInfo();
		}
	}*/
}
