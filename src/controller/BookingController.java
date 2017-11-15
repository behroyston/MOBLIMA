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

public class BookingController extends DatabaseController{

	private String DIR = "booking/";

	private ArrayList<Booking> bookingList;

	private static BookingController instance = null;

	private BookingController() {
		bookingList = new ArrayList<>();
		readDB();
	}

	public static BookingController getInstance() {
		if (instance == null)
			instance = new BookingController();
		return instance;
	}

	 /** Summary: Text in order: String transactionId , int movieScreeningID, String userName, String mobileNum,
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
			catch (IOException io)
			{
				System.out.println("Error! Unable to retrieve model from file.");
			}
		}
		else
		{
			System.out.println("Error, Directory not found! Database for Booking is not loaded!");
		}

	}

	/** Each line in the Bookings.dat file is a booking object
	 * Summary: Text in order: String transactionId , int movieScreeningID, String userName, String mobileNum, String emailAddress, int cinemaID
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

	// exposes the private directory to initialize the retrieval of model.
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
	 * @param movieScreeningID
	 * Add model.Booking into the BookingList after user has key in all the details required by the ui.MovieGoerUI.
	 */
	public void addBooking(String userName, String mobileNum, String emailAddress, int cinemaID, int movieScreeningID){
		//
		Booking newBooking = new Booking(movieScreeningID,userName,mobileNum,emailAddress,cinemaID);
		bookingList.add(newBooking);
		writeDB();
		//updateSales(MovieScreeningController.getInstance().getMovieScreenings().get(movieScreeningID).getMovieID());
	}

	public ArrayList<Booking> getAllBookingByUser(String emailAddress){
		ArrayList<Booking> userBookings = new ArrayList<>();
		for (Booking booking : bookingList)
			if (emailAddress.equalsIgnoreCase(booking.getEmailAddress()))
				userBookings.add(booking);
		return userBookings;
	}/*
	private double basePrice=10;
	private double threeDExtra=5;
	private double platinumExtra=7;
	private double seniorDiscount=3;
	private double childDiscount=2;
	private double weekend_HolidayExtra=2;
	private double goldExtra=5;*/
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
		
		int dayOfWeek = movieScreening.getStartTime().get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
			price += sysSettings.getWeekend_HolidayExtra();
		
		return price;
	}
	
	/**
	 * Update the total sales of the model.Movie by using the MovieID.
	 */
	private void updateSales(int movieID){
        double basePrice = SystemSettings.getInstance().getBasePrice();
	}


	public void setBookingList(ArrayList<Booking> bookingList) {
		this.bookingList = bookingList;
	}

	public void printBookingList(){
		for (int i = 0; i < bookingList.size(); i++){
			bookingList.get(i).printInfo();
		}
	}
}
