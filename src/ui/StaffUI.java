package ui;

import controller.MovieController;
import controller.MovieScreeningController;
import controller.StaffController;
import model.Movie;
import model.MovieClassType;
import model.MovieShowingStatus;
import model.SystemSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class StaffUI {

	//attributes
	private static StaffUI instance = null;

	Scanner sc = new Scanner(System.in);

	//class constructor
	private StaffUI() {

	}

	//display
	public void display() {
		// Validation first
		System.out.println("-------------------- Welcome to MOBLIMA! --------------------");
		String emailAddress = loginValidation();
		if (emailAddress == null){
			return;
		}

		int choice1;
		do {
			System.out.println("-----------------STAFF CONSOLE-----------------");
			System.out.println("What would you like to do?\n");
			System.out.println("1. Create Movie Listing");
			System.out.println("2. Update Movie Listing");
			System.out.println("3. Remove Movie Listing");
			System.out.println("4. Create Cinema Showtimes");
			System.out.println("5. Update Cinema Showtimes");
			System.out.println("6. Remove Cinema Showtimes");
			System.out.println("7. List Top 5 Movies by Sales or by Ratings");
			System.out.println("8. Configure System Settings");
			System.out.println("9. Exit");
			choice1 = checkIfInt(10);

			switch (choice1) {
			case 1:
				createMovieListing();
				break;
			case 2:
				updateMovieListing();
				break;
			case 3:
				removeMovieListing();
				break;
			case 4:
				createCinemaShowtimes();
				break;
			case 5:
				updateCinemaShowtimes();
				break;
			case 6:
				removeCinemaShowtimes();
				break;
			case 7:
				listTopRankings();
				break;
			case 8:
				configSystemSettings();
				break;
			}
		} while (choice1 != 8);
	}

	private int checkIfInt(int value){
		if (!sc.hasNextInt()){
			sc.next(); // Remove the invalid input
			return value;
		}
		return sc.nextInt();
	}

	//login
	private String loginValidation() {

		String email, password;
		while (true){
			System.out.print("Please enter your email address: ");
			email = sc.next();
			System.out.print("Please enter your password: ");
			password = sc.next();
			if (!StaffController.getInstance().validateStaff(email, password))
				System.out.println("Invalid Staff login details! Please re-enter...");
			else
				break;
		}
		return email;
	}

	private void createMovieListing(){
		System.out.println("-----------------CREATE MOVIE LISTINGS-----------------");
		System.out.println("Add new Movie Listing: \n");
		createOrUpdateMovieListing(true);
	}

	private void updateMovieListing(){
		System.out.println("-----------------EDIT MOVIE LISTINGS-----------------");
		System.out.println("Update Existing Movie Listing: \n");
		createOrUpdateMovieListing(false);
	}

	private void createOrUpdateMovieListing(boolean create) {
		System.out.println("List of Movie Listings: \n");
		MovieController.getInstance().printMovieNames();
		System.out.println("Enter Movie ID: ");
		int newMID = sc.nextInt();
		// remove the garbage \n char
		sc.nextLine();
		System.out.println("Enter Movie Name: ");
		String newName = sc.nextLine();
		System.out.println("Enter Movie Synopsis: ");
		String newSyn = sc.nextLine();
		System.out.println("Enter Movie Director(s) Name: ");
		String newDir = sc.nextLine();
		System.out.println("Enter Cast List: ");
		String newCast = sc.nextLine();
		System.out.println("Enter Duration of Movie: ");
		int duration = sc.nextInt();

		System.out.println("Enter Movie Showing Status: \n" +
				"1) Coming Soon\n" + "2) Preview\n" + "3) Now Showing\n" + "4) End of Showing" );
		int newType = sc.nextInt();
		MovieShowingStatus movieShowingStatus;
		switch (newType){
		case (1) :
			movieShowingStatus = MovieShowingStatus.COMING_SOON;
		break;
		case (2) :
			movieShowingStatus = MovieShowingStatus.PREVIEW;
		break;
		case (3) :
			movieShowingStatus = MovieShowingStatus.NOW_SHOWING;
		break;
		case (4) :
			movieShowingStatus = MovieShowingStatus.END_OF_SHOWING;
		break;
		default:
			movieShowingStatus = MovieShowingStatus.PREVIEW;
			break;
		}

		if (create)
			MovieController.getInstance().addMovie(newMID, newName, newSyn, newDir, newCast, movieShowingStatus, duration);
		else
			MovieController.getInstance().updateMovie(newMID, newName, newSyn, newDir, newCast, movieShowingStatus, duration);

	}
	private void removeMovieListing() {
		System.out.println("-----------------EDIT MOVIE LISTINGS-----------------");
		System.out.println("Remove Existing Movie Listing: \n");
		System.out.println("Enter Movie ID: ");
		int newMID = sc.nextInt();
		Movie movie = MovieController.getInstance().removeMovie(newMID);
		if (movie != null){
			System.out.println(movie.getMovieName() + " is removed!");
		}
		else
			System.out.println("Movie is not found!");
	}

	private void createCinemaShowtimes(){
		System.out.println("-----------------CREATE CINEMA SHOWTIMES-----------------");
		System.out.println("Add New Showtime:\n");
		createOrUpdateCinemaShowtimes(true);
	}

	private void updateCinemaShowtimes(){
		System.out.println("-----------------EDIT CINEMA SHOWTIMES-----------------");
		System.out.println("Edit New Showtime:\n");
		createOrUpdateCinemaShowtimes(false);

	}

	//methods to edit cinema showtimes
	private void createOrUpdateCinemaShowtimes(boolean create) {
		//consider adding exception handling for wrong Cinema/Movie ID?
		int movieScreeningID = -1;
		if (!create) {
			System.out.println("Enter the movie screening ID: ");
			movieScreeningID = sc.nextInt();
		}
		System.out.println("Enter Cinema ID: ");
		int newCID = sc.nextInt();
		System.out.println("Enter Movie ID: ");
		int newMID = sc.nextInt();
		System.out.println("Enter Movie Type: \n" +
				"1) 2D\n" + "2) 3D\n");
		int newType = sc.nextInt();
		MovieClassType movieClassType;
		switch (newType){
		case (1) :
			movieClassType = MovieClassType.CLASS2D;
		break;
		case (2) :
			movieClassType = MovieClassType.CLASS3D;
		break;
		default:
			movieClassType = MovieClassType.CLASS2D;
			break;
		}
		System.out.println("Movie Start Time:\n");
		System.out.println("Enter Year of screening: ");
		int newYear = sc.nextInt();
		System.out.println("Enter Month of screening: ");
		int newMonth = sc.nextInt();
		System.out.println("Enter Day of screening: ");
		int newDay = sc.nextInt();
		System.out.println("Enter Hour of Screening: ");
		int newHour = sc.nextInt();
		System.out.println("Enter Minute of Screening: ");
		int newMin = sc.nextInt();
		Calendar calendar = Calendar.getInstance();
		calendar.set(newYear, newMonth - 1, newDay, newHour,newMin, 0);

		if (create)
			MovieScreeningController.getInstance().addMovieScreening(movieScreeningID, newCID, newMID, calendar, movieClassType);
		else
			MovieScreeningController.getInstance().updateMovieScreening(movieScreeningID, newCID, newMID, calendar, movieClassType);

	}

	private void removeCinemaShowtimes() {
		System.out.println("-----------------EDIT CINEMA SHOWTIMES-----------------");
		System.out.println("Remove Existing Showtime:\n");
		System.out.println("List of exisiting screenings: \n");
		System.out.println(MovieScreeningController.getInstance().getMovieScreenings());
		System.out.println("Enter Movie Screening ID of Screening to Update: ");
		int newSID = sc.nextInt();

		MovieScreeningController.getInstance().removeMovieScreening(newSID);
	}

	private void configSystemSettings() {
		int choice, choice2;
		do {
			System.out.println("-----------------SYSCONFIG-----------------");
			System.out.println("What would you like to do?");	
			System.out.println("1. Configure Base Price of tickets");
			System.out.println("2. Configure 3d Movie Surcharge");
			System.out.println("3. Configure Platinum Suite Surcharge");
			System.out.println("4. Configure Senior Citizen Disocunt");
			System.out.println("5. Configure Child Discount");
			System.out.println("6. Configure Weekend/Holiday Surcharge");
			System.out.println("7. Add a Holiday Date");
			System.out.println("0. Exit\n");
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Current Base Price: " + SystemSettings.getInstance().getBasePrice() );
				System.out.println("Would you like to amend the Base Price? Enter '1' if YES; '0' if NO. ");
				choice2 = sc.nextInt();
				if(choice2 == 1) {
					System.out.println("New Base Price: ");
					double newBase = sc.nextDouble();
					SystemSettings.getInstance().setBasePrice(newBase);
				}
				else if(choice2 != 0 && choice2 != 1) {
					System.out.println("INVALID OPTION");
				}
				break;

			case 2:
				System.out.println("Current 3D Movie Surcharge: " + SystemSettings.getInstance().getThreeDExtra() );
				System.out.println("Would you like to amend 3D Movie Surcharge? Enter '1' if YES; '0' if NO. ");
				choice2 = sc.nextInt();
				if(choice2 == 1) {
					System.out.println("New 3D Surcharge: ");
					double newThreeD = sc.nextDouble();
					SystemSettings.getInstance().setThreeDExtra(newThreeD);;
				}
				else if(choice2 != 0 && choice2 != 1) {
					System.out.println("INVALID OPTION");
				}
				break;

			case 3:
				System.out.println("Current Platinum Suite Surcharge: " + SystemSettings.getInstance().getPlatinumExtra() );
				System.out.println("Would you like to amend the Platinum Suite Surcharge? Enter '1' if YES; '0' if NO. ");
				choice2 = sc.nextInt();
				if(choice2 == 1) {
					System.out.println("New Platinum Suite Surcharge: ");
					double newPlat = sc.nextDouble();
					SystemSettings.getInstance().setPlatinumExtra(newPlat);;
				}
				else if(choice2 != 0 && choice2!= 1) {
					System.out.println("INVALID OPTION");
				}
				break;

			case 4:
				System.out.println("Current Senior Citizen Discount: " + SystemSettings.getInstance().getSeniorDiscount() );
				System.out.println("Would you like to amend the Senior Citizen Discount? Enter '1' if YES; '0' if NO. ");
				choice2 = sc.nextInt();
				if(choice2 == 1) {
					System.out.println("New Senior Citien Discount: ");
					double newSnr = sc.nextDouble();
					SystemSettings.getInstance().setSeniorDiscount(newSnr);;
				}
				else if(choice2 != 0 && choice2 != 1) {
					System.out.println("INVALID OPTION");
				}
				break;

			case 5:
				System.out.println("Current Child Discount: " + SystemSettings.getInstance().getChildDiscount() );
				System.out.println("Would you like to amend the Child Discount? Enter '1' if YES; '0' if NO. ");
				choice2 = sc.nextInt();
				if(choice2 == 1) {
					System.out.println("New Child Discount: ");
					double newChild = sc.nextDouble();
					SystemSettings.getInstance().setChildDiscount(newChild);;
				}
				else if(choice2 != 0 && choice2 != 1) {
					System.out.println("INVALID OPTION");
				}
				break;

			case 6:
				System.out.println("Current Weekend/Holiday Surcharge: " + SystemSettings.getInstance().getWeekend_HolidayExtra() );
				System.out.println("Would you like to amend the Weekend/Holiday Surcharge? Enter '1' if YES; '0' if NO. ");
				choice2 = sc.nextInt();
				if(choice2 == 1) {
					System.out.println("New Weekend/Holiday Surcharge: ");
					double newWeek = sc.nextDouble();
					SystemSettings.getInstance().setWeekend_HolidayExtra(newWeek);;
				}
				else if(choice2 != 0 && choice2 != 1) {
					System.out.println("INVALID OPTION");
				}
				break;
			case 7:
				System.out.println("Add a Holiday Date: ");
				System.out.print("Enter the date in yyyy-mm-dd: ");
				sc.nextLine(); // Clear buffer
				String dateStr = sc.nextLine();
				try {
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
					Calendar holiday = Calendar.getInstance();
					holiday.setTime(date);
					if (!SystemSettings.getInstance().addHoliday(holiday))
						System.out.println("You have already set this day as a holiday date!");
				} catch (ParseException e) {
					System.out.println("Invalid input!");
				}
				
				break;
			}
		} while (choice != 0);
	}
	
	private void listTopRankings(){
		MovieController movieController = MovieController.getInstance();
		int choice;
		System.out.println("Select an option to view the Top 5 Rankings:");
		do{
			System.out.println("1. List the Top 5 Movies by Ticket Sales");
			System.out.println("2. List the Top 5 Movies by Overall Reviewer's Rating");
			System.out.println("3. Go back to previous menu");
			System.out.print("Enter choice: ");
			choice = checkIfInt(4);
			switch (choice){
			case 1:
				movieController.printTopFiveBySales();
				break;
			case 2:
				movieController.printTopFiveByRatings();
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		}while (choice > 3 || choice < 1);
	}

	//static 'instance' method
	public static StaffUI getInstance(){
		if(instance == null) {
			instance = new StaffUI();
		}
		return instance;
	}
}
