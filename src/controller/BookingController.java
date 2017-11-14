package controller;

import model.Booking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BookingController extends DatabaseController{

	private String DIR = "booking/";

	private ArrayList<Booking> bookingList;

	private static BookingController Instance = null;

	private BookingController() {
		bookingList = new ArrayList<>();
	}

	public static BookingController getInstance() {
		if (Instance == null)
			Instance = new BookingController();
		return Instance;
	}

	@Override
	protected void readDB() {
		bookingList.clear();
		if (checkDirectoryExist(BASEDIR + DIR)) {
			try{
				for (File f : getListofFiles(BASEDIR + DIR)) {
					List<String> text = retrieveData(BASEDIR + DIR + "Booking.dat");
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
						booking = new Booking(movieScreeningID,userName,mobileNum,emailAddress,transactionID);
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

	@Override
	protected void writeDB() {
		List<String> text = new ArrayList<>();
		StringBuilder str = new StringBuilder();
		/* Summary: Text in order: String transactionId , int movieScreeningID, String userName, String mobileNum, String emailAddress
		 */
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
				text.add(str.toString());

				// Attempt to save to file
				try {
					saveData(BASEDIR + DIR + "Booking" + ".dat", text); // Write to file
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
		updateSales(MovieScreeningController.getInstance().getMovieScreenings().get(movieScreeningID).getMovieID());
	}

	public ArrayList<Booking> getAllBookingByUser(String emailAddress){
		ArrayList<Booking> userBookings = new ArrayList<>();
		for (Booking booking : userBookings)
			if (emailAddress.equals(booking.getEmailAddress()))
				userBookings.add(booking);
		return userBookings;
	}

	/**
	 * Update the total sales of the model.Movie by using the MovieID.
	 */
	private void updateSales(int movieID){
		// calls controller.MovieController.get(movieID).addSales(SystemSettings.money);
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
