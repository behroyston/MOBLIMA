package ui;

import controller.MovieController;
import controller.MovieScreeningController;
import controller.StaffController;
import model.MovieClassType;
import model.SystemSettings;

import java.util.Calendar;
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
			System.out.println("1. Create Movie Listing\n");
			System.out.println("2. Update Movie Listing\n");
			System.out.println("3. Remove Movie Listing\n");
			System.out.println("4. Create Cinema Showtimes\n");
			System.out.println("5. Update Cinema Showtimes\n");
			System.out.println("6. Remove Cinema Showtimes\n");
			System.out.println("7. Configure System Settings\n");
			System.out.println("0. Exit\n");
			choice1 = sc.nextInt();
		
			switch (choice1) {
			case '1':
				createMovieListing();
				break;
			case '2':
				updateMovieListing();
				break;
			case '3':
				removeMovieListing();
				break;
			case '4':
				createCinemaShowtimes();
				break;
			case '5':
				updateCinemaShowtimes();
				break;
			case '6':
				removeCinemaShowtimes();
				break;
			case '7':
				configSystemSettings();
				break;
			}
		} while (choice1 != 0);
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

	
	//methods to edit movie listing
	private void createMovieListing() {
		System.out.println("-----------------EDIT MOVIE LISTINGS-----------------");
		System.out.println("Add New Movie Listing: \n");
		System.out.println("Enter Movie ID: ");
		int newMID = sc.nextInt();
		System.out.println("Enter Movie Name: ");
		String newName = sc.next();
		System.out.println("Enter Movie Synopsis: ");
		String newSyn = sc.next();
		System.out.println("Enter Movie Director(s) Name: ");
		String newDir = sc.next();
		System.out.println("Enter Cast List: ");
		String newCast = sc.next();
		System.out.println("Enter Movie Showing Status: ");
		String newStat = sc.next();
		
		MovieController.getInstance().addMovie(newMID, newName, newSyn, newDir, newCast, newStat);
		
	}
	private void updateMovieListing() {
		System.out.println("-----------------EDIT MOVIE LISTINGS-----------------");
		System.out.println("Update Existing Movie Listing: \n");
		System.out.println("List of Movie Listings: \n");
		System.out.println(MovieController.getInstance().getShowingMovieList());
		System.out.println("Enter Movie ID: ");
		int newMID = sc.nextInt();
		System.out.println("Enter Movie Name: ");
		String newName = sc.next();
		System.out.println("Enter Movie Synopsis: ");
		String newSyn = sc.next();
		System.out.println("Enter Movie Director(s) Name: ");
		String newDir = sc.next();
		System.out.println("Enter Cast List: ");
		String newCast = sc.next();
		System.out.println("Enter Movie Showing Status: ");
		String newStat = sc.next();
		
		MovieController.getInstance().updateMovie(newMID, newName, newSyn, newDir, newCast, newStat);
	}
	private void removeMovieListing() {
		System.out.println("-----------------EDIT MOVIE LISTINGS-----------------");
		System.out.println("Remove Existing Movie Listing: \n");
		System.out.println("Enter Movie ID: ");
		int newMID = sc.nextInt();
		
		MovieController.getInstance().removeMovie(newMID);
	}
	
	//methods to edit cinema showtimes
	private void createCinemaShowtimes() {
		System.out.println("-----------------EDIT CINEMA SHOWTIMES-----------------");
		System.out.println("Add New Showtime:\n");
		//consider adding exception handling for wrong Cinema/Movie ID?
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
		calendar.set(newYear, newMonth, newDay, newHour,newMin, 0);
		
		MovieScreeningController.getInstance().addMovieScreening(newCID, newMID, calendar, movieClassType);
		
		
	}
	private void updateCinemaShowtimes() {
		System.out.println("-----------------EDIT CINEMA SHOWTIMES-----------------");
		System.out.println("Update Existing Showtime:\n");
		System.out.println("List of exisiting screenings: \n");
		System.out.println(MovieScreeningController.getInstance().getMovieScreenings());
		//consider adding exception handling for wrong Cinema/Movie ID?
		System.out.println("Enter Movie Screening ID of Screening to Update: ");
		int newSID = sc.nextInt();
		System.out.println("Enter Updated Cinema ID: ");
		int newCID = sc.nextInt();
		System.out.println("Enter Updated Movie ID: ");
		int newMID = sc.nextInt();
		System.out.println("Enter Updated Movie Type: ");
		String newType = sc.next();
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
		calendar.set(newYear, newMonth, newDay, newHour,newMin, 0);
		
		MovieScreeningController.getInstance().updateMovieScreening(newSID, newCID, newMID, calendar, newType);	
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
		System.out.println("-----------------SYSCONFIG-----------------");
		System.out.println("What would you like to do?\n");	
		System.out.println("1. Configure Base Price of tickets\n");
		System.out.println("2. Configure 3d Movie Surcharge\n");
		System.out.println("3. Configure Platinum Suite Surcharge\n");
		System.out.println("4. Configure Senior Citizen Disocunt\n");
		System.out.println("5. Configure Child Discount\n");
		System.out.println("6. Configure Weekend/Holiday Surcharge\n");
		System.out.println("0. Exit\n");
		int choice2 = sc.nextInt();
		
		
		do {
			switch(choice2) {
			case '1':
				System.out.println("Current Base Price: " + SystemSettings.getInstance().getBase_price() );
				System.out.println("Would you like to amend the Base Price? Enter '1' if YES; '0' if NO. ");
				int choice2a = sc.nextInt();
				if(choice2a == 1) {
					System.out.println("New Base Price: ");
					double newBase = sc.nextDouble();
					SystemSettings.getInstance().setBase_price(newBase);
				}
				else if(choice2a != 0 && choice2a != 1) {
					System.out.println("INVALID OPTION");
				}
				else break;
				
			case '2':
				System.out.println("Current 3D Movie Surcharge: " + SystemSettings.getInstance().getThreeDExtra() );
				System.out.println("Would you like to amend 3D Movie Surcharge? Enter '1' if YES; '0' if NO. ");
				int choice2b = sc.nextInt();
				if(choice2b == 1) {
					System.out.println("New 3D Surcharge: ");
					double newThreeD = sc.nextDouble();
					SystemSettings.getInstance().setThreeDExtra(newThreeD);;
				}
				else if(choice2b != 0 && choice2b != 1) {
					System.out.println("INVALID OPTION");
				}
				else break;
			
			case '3':
				System.out.println("Current Platinum Suite Surcharge: " + SystemSettings.getInstance().getPlatinumExtra() );
				System.out.println("Would you like to amend the Platinum Suite Surcharge? Enter '1' if YES; '0' if NO. ");
				int choice2c = sc.nextInt();
				if(choice2c == 1) {
					System.out.println("New Platinum Suite Surcharge: ");
					double newPlat = sc.nextDouble();
					SystemSettings.getInstance().setPlatinumExtra(newPlat);;
				}
				else if(choice2c != 0 && choice2c!= 1) {
					System.out.println("INVALID OPTION");
				}
				else break;
			
			case '4':
				System.out.println("Current Senior Citizen Discount: " + SystemSettings.getInstance().getSeniorDiscount() );
				System.out.println("Would you like to amend the Senior Citizen Discount? Enter '1' if YES; '0' if NO. ");
				int choice2d = sc.nextInt();
				if(choice2d == 1) {
					System.out.println("New Senior Citien Discount: ");
					double newSnr = sc.nextDouble();
					SystemSettings.getInstance().setSeniorDiscount(newSnr);;
				}
				else if(choice2d != 0 && choice2d != 1) {
					System.out.println("INVALID OPTION");
				}
				else break;
			
			case '5':
				System.out.println("Current Child Discount: " + SystemSettings.getInstance().getChildDiscount() );
				System.out.println("Would you like to amend the Child Discount? Enter '1' if YES; '0' if NO. ");
				int choice2e = sc.nextInt();
				if(choice2e == 1) {
					System.out.println("New Child Discount: ");
					double newChild = sc.nextDouble();
					SystemSettings.getInstance().setChildDiscount(newChild);;
				}
				else if(choice2e != 0 && choice2e != 1) {
					System.out.println("INVALID OPTION");
				}
				else break;
			
			case '6':
				System.out.println("Current Weekend/Holiday Surcharge: " + SystemSettings.getInstance().getWeekend_HolidayExtra() );
				System.out.println("Would you like to amend the Weekend/Holiday Surcharge? Enter '1' if YES; '0' if NO. ");
				int choice2f = sc.nextInt();
				if(choice2f == 1) {
					System.out.println("New Weekend/Holiday Surcharge: ");
					double newWeek = sc.nextDouble();
					SystemSettings.getInstance().setWeekend_HolidayExtra(newWeek);;
				}
				else if(choice2f != 0 && choice2f != 1) {
					System.out.println("INVALID OPTION");
				}
				else break;
			}
		} while (choice2 != 0);
	}
	
	//static 'instance' method
	public static StaffUI getInstance(){
		if(instance == null) {
			instance = new StaffUI();
		}
		return instance;
	}
}
>>>>>>> a967083b49fe7b562c5464c8491a669aece4d4e1
