package ui;

import controller.*;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * A user interface for MovieGoer to interact and book tickets.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieGoerUI {

	/**
	 * Instance of the MovieGoerUI.
	 */
	private static MovieGoerUI instance = null;

	/**
	 * Scanner object to take in inputs from the MovieGoer.
	 */
	Scanner sc = new Scanner(System.in);

	/**
	 * MovieGoer object to store the details of the MovieGoer after validation.
	 */
	private MovieGoer movieGoer;

	/**
	 * Store the Email of MovieGoer after validation for easy access subsequently.
	 */
	private String emailAddress;

	/**
	 * The column letters used in the seats layout display and booking
	 */
	private final List<String> stringList = Arrays.asList("A", "B", "C", "D", "E", "F", "G", " ", "H", "I", "J", "K", "L","M","N");

	/**
	 * Creates a MovieGoerUI object.
	 */
	private MovieGoerUI(){};

	/**
	 * Central interface that display all the options avaliable to a moviegoer.
	 * Will ask the moviegoer to login first before proceeding.
	 */
	public void display(){
		// Validation first
		System.out.println("-------------------- Welcome to MOBLIMA! --------------------");
		int choice;
		do{
			System.out.println("Do you have a account with us?");
			System.out.println("1. No and would like to register one");
			System.out.println("2. Yes and would like to login");
			System.out.println("3. Go back to previous menu");
			System.out.print("Enter choice: ");
			choice = checkIfInt(4);
			switch (choice){
			case 1:
				if(!registerNewAccount()){
					System.out.println("The email account already exists!");
					break;
				}
				System.out.println("Your account has been created! Please login now.");
			case 2:
				emailAddress = loginValidation();
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		}while(emailAddress == null);

		// Movie-Goer Options
		do{
			System.out.println("-------------------- Main Menu --------------------");
			System.out.println("1. Search/List movie");
			System.out.println("2. View movie details and book tickets");
			System.out.println("3. List the Top 5 ranking by ticket sales OR by overall reviewers' ratings");
			System.out.println("4. View booking history");
			System.out.println("5. Enter review or rating for a movie you have watched");
			System.out.println("6. Go back to previous menu");
			System.out.print("Enter choice: ");
			choice = checkIfInt(7);
			switch (choice){
			case 1:
				listMovies();
				break;
			case 2:
				viewMovieDetail();
				break;
			case 3:
				listTopRankings();
				break;
			case 4:
				viewBookingHistory();
				break;
			case 5:
				enterReviewRating();
				break;
			case 6:
				break; 
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		}while(choice != 6);
	}

	/**
	 * Check if the value entered is integer.
	 * @param value	Value to return in user enter an non-integer value.
	 * @return		Value entered by user if it is integer. Otherwise, returns the input parameter of this method. 
	 */
	private int checkIfInt(int value){
		if (!sc.hasNextInt()){
			sc.next(); // Remove the invalid input
			return value;
		}
		return sc.nextInt();
	}

	/**
	 * Interact with the moviegoer to register a new account. It will also check if the account already exists.
	 * @return	true if account is created successfully. false otherwise.
	 */
	private boolean registerNewAccount(){
		sc.nextLine();			// Clear buffer
		System.out.print("Please enter your name: ");
		String name = sc.nextLine();
		System.out.print("Please enter your email address: ");
		String email = sc.next();
		System.out.print("Please enter your password: ");
		String password = sc.next();
		System.out.print("Please enter your mobile number: ");
		String mobileNumber = sc.next();
		System.out.print("Please enter your age: ");
		int age = sc.nextInt();
		return MovieGoerController.getInstance().addMovieGoer(password, name, mobileNumber, email, age);
	}

	/**
	 * Interact with the moviegoer to login and perform validation. 
	 * @return	Email address of the moviegoer if validated successfully. null object otherwise.
	 */
	private String loginValidation(){
		String email, password;
		MovieGoer movieGoer;
		System.out.println("Login System:");

		System.out.print("Please enter your email address: ");
		email = sc.next();
		System.out.print("Please enter your password: ");
		password = sc.next();
		movieGoer = MovieGoerController.getInstance().validateCustomer(email, password);	// Validate the movie-goer

		if (movieGoer == null){
			System.out.println("Invalid login details!");
			return null;
		}
		this.movieGoer = movieGoer;
		return movieGoer.getEmail();
	}

	/**
	 * Display all the movies that is coming soon, previewing or showing.
	 */
	private void listMovies(){
		System.out.println("The movies that are coming soon, previewing or showing: ");
		MovieController.getInstance().printMovieNames();
	}

	/**
	 * Display all movies that are available for booking.
	 */
	private void printShowingMovies(){
		ArrayList<Movie> movieList = MovieController.getInstance().getShowingMovieList();
		System.out.println("The list of movies that are currently showing: ");
		for (int i = 0; i < movieList.size(); i++){
			System.out.println((i+1) + ") " + movieList.get(i).getMovieName());
		}
	}

	/**
	 * Interact with the moviegoer to display the movie detail and display those that can be booked.
	 * Let the moviegoer booked ticket subsequently.
	 */
	private void viewMovieDetail(){
		int choice;
		System.out.println("Select an option to view the Movie details:");
		do{
			System.out.println("1. Enter the movie name");
			System.out.println("2. Enter the number shown from the list of movies");
			System.out.println("3. Go back to previous menu");
			System.out.print("Enter choice: ");
			choice = checkIfInt(4);
			switch (choice){
			case 1:
				System.out.println("Please enter the name of the movie: ");
				sc.nextLine();                // Clear buffer
				String movieName = sc.nextLine();
				Movie showingMovie = MovieController.getInstance().getShowingMovieByName(movieName);
				if (showingMovie != null) {
					showingMovie.printInfo();
					int movieID = showingMovie.getMovieId();
					listTimings(movieID);
				}
				else
					System.out.println("No such movie!");
				break;
			case 2:
				ArrayList<Movie> movieList = MovieController.getInstance().getShowingMovieList();
				printShowingMovies();
				System.out.print("Enter corresponding number: ");
				int num = checkIfInt(-1);
				if (num > movieList.size() || num < 1)
					System.out.println("Invalid Number!");
				else {
					movieList.get(num-1).printInfo();
					listTimings(movieList.get(num-1).getMovieId());
				}
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		} while (choice > 3 || choice < 1);
	}

	/**
	 * Interact with the user to display the movie screenings that are available for booking.
	 * @param movieID	MovieID of the movie selected by moviegoer.
	 */
	private void listTimings(int movieID){
		ArrayList<MovieScreening> movieScreenings = MovieScreeningController.getInstance().getMovieScreeningsByMovieID(movieID);
		char isBooking;
		while (true){
			System.out.println("Would like to book a ticket now? (y/n)");
			isBooking = sc.next().charAt(0);
			if (isBooking == 'y')
				break;
			else if (isBooking == 'n')
				return;
			else
				System.out.println("Invalid input! Please re-enter...");
		}
		CineplexController cineplexController = CineplexController.getInstance();

		// Get list of moviescreening that is bookable
		ArrayList<MovieScreening> bookableScreenings = new ArrayList<>();
		for (MovieScreening movieScreening : movieScreenings)
			if (!movieScreening.getIsExpired())
				bookableScreenings.add(movieScreening);

		if (bookableScreenings.size() == 0)
			System.out.println("There is no more screenings for this movie!");
		else{
			System.out.println("MovieScreenings:");
			for (int i = 0; i < bookableScreenings.size(); i++){
				int cinemaID = bookableScreenings.get(i).getCinemaID();
				System.out.print((i+1) + ") ");
				System.out.print(bookableScreenings.get(i).getStartDateTime() + " at " +
						cineplexController.getCineplexByCinemaID(cinemaID).getName());
				System.out.println(" - Cinema "+ cineplexController.getCinema(cinemaID).getCinemaID());
			}
			System.out.println("Enter the Movie Screening Number if you want to book seats!");

			int selection = sc.nextInt();
			showSeatsAvailability(bookableScreenings.get(selection-1).getMovieScreeningID());
		}
	}

	/**
	 * Interact with the moviegoer to display the top rankings either by sales or overall ratings.
	 */
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

	/**
	 * Interact with the moviegoer to enter review and rating for movies that he has watched.
	 */
	private void enterReviewRating(){
		ArrayList<Booking> userBookings = BookingController.getInstance().getAllBookingByUser(emailAddress);
		MovieController movieController = MovieController.getInstance();
		MovieScreeningController movieScreeningController = MovieScreeningController.getInstance();
		ArrayList<Integer> movieIDs = new ArrayList<>();

		// Get all unique movieIDs the user have watched.
		for (Booking booking : userBookings){
			int screeningID = booking.getMovieScreeningID();
			MovieScreening movieScreening = movieScreeningController.getMovieScreeningByScreeningID(screeningID);
			int movieID = movieScreening.getMovieID();
			if (!movieIDs.contains(movieID))
				movieIDs.add(movieID);
		}

		// Prompt the user to enter a review or rating if they have watched a movie
		if (movieIDs.size() == 0){
			System.out.println("You have not watched any movies yet.");
			return;
		}
		else
			System.out.println("You have watched the following movies:");
		for (int i = 1; i <= movieIDs.size(); i++)	// Print a list of movies watched for the user to select
			System.out.println(i + ") " + movieController.getMovie(movieIDs.get(i-1)).getMovieName());
		int choice;
		while (true){
			System.out.print("Enter the index corresponding to the movie: ");
			choice = checkIfInt(-1);
			if (choice > movieIDs.size() || choice < 1)
				System.out.println("Invalid choice. Please re-enter...");
			else
				break;
		}
		int movieID = movieIDs.get(choice-1);
		System.out.print("Enter your rating: ");
		double rating = sc.nextDouble();
		System.out.print("Enter your review: ");
		sc.nextLine(); 			// Clear buffer
		String review = sc.nextLine();
		movieController.addMovieReviewRating(movieID, review, rating);
		System.out.println("Your review and rating have been recorded. Thank you!");
	}

	/**
	 * Display the seats availability for selected movie screening. It will prompt
	 * the user if it want to book seats now.
	 * @param movieScreeningID	MovieScreeningID selected by the user for booking.
	 */
	private void showSeatsAvailability(int movieScreeningID){

		MovieScreening movieScreening = MovieScreeningController.getInstance().getMovieScreeningByScreeningID(movieScreeningID);
		// 14
		System.out.println("\t <<<EXIT>>>\t");
		System.out.print("  ");
		// Construct 1 to 15 for horizontal label;
		for (int i = 0; i < 15; i++) {
			if (i == 7) {
				System.out.print(" ");
				continue;
			}
			System.out.print(stringList.get(i));
		}
		System.out.println();
		movieScreening.printMovieScreeningSeatsInfo();
		System.out.println("\t<<<SCREEN>>>\t");
		System.out.println("O - Available Seat\tX - Unavailable Seat");
		movieScreening.printMovieScreeningInfo();
		System.out.println("Cinema Class Type: " + CineplexController.getInstance().getCinema(movieScreening.getCinemaID()).getCinemaClassType());

		System.out.println("1. Book Seat\n2. Go Back to Main Menu");
		int choice = checkIfInt(2);
		switch (choice) {
		case (1): bookTickets(movieScreeningID);
		break;
		case (2): // Return to main menu
			break;
		}
	}

	/**
	 * Interact with the user to book the seat.
	 * If the user confirm the seat, it will proceed to make payment and the booking is confirmed.
	 * @param movieScreeningID MovieScreeningID selected by the user for booking.
	 */
	private void bookTickets(int movieScreeningID){
		int number;
		String alphabet;
		MovieScreening movieScreening = MovieScreeningController.getInstance().getMovieScreeningByScreeningID(movieScreeningID);
		System.out.println("Please enter the Seat Alphabet you wished to book!");
		while (!sc.hasNext("[ABCDEFGHIJKLMNabcdefghijklmn]")) {
			System.out.println("That's not an Alphabet");
			sc.next(); // this is important!
		}
		alphabet = sc.next();

		do {
			System.out.println("Please enter a valid number!");
			while (!sc.hasNextInt()) {
				System.out.println("That's not a number!");
				sc.next(); // this is important!
			}
			// y
			number = sc.nextInt();
		} while (number <= 0);


		// x
		int horizontalIndex = stringList.indexOf(alphabet.toUpperCase());
		boolean setSeat = MovieScreeningController.getInstance().setSeatSelected(movieScreeningID,number-1,horizontalIndex);

		if (setSeat) {
			int cinemaID = movieScreening.getCinemaID();
			int age = movieGoer.getAge();
			int type = 0;
			if (age <= 10)
				type = 1;
			else if (age >= 65)
				type = 2;

			// Ask for payment
			double price = BookingController.getInstance().calculatePrice(type, movieScreeningID);
			System.out.printf("Please make payment of SGD %.2f (inclusive of %s)\n ", price, "7% GST");
			System.out.print("Enter anything to pay :");
			sc.nextLine(); sc.nextLine();
			BookingController.getInstance().addBooking(movieGoer.getName(),movieGoer.getMobileNumber(),
					movieGoer.getEmail(),cinemaID,movieScreeningID, price);
		}
		else
			System.out.println("The seat is booked! Please select another seat.");
		showSeatsAvailability(movieScreeningID);
	}

	private void viewBookingHistory() {
		ArrayList<Booking> bookings = BookingController.getInstance().getAllBookingByUser(emailAddress);
		if (bookings.size() == 0){
			System.out.println("You have not made any bookings!");
			return;
		}

		System.out.println("Booking History: \n");
		for (Booking booking : bookings) {
			int moviescreeningID = booking.getMovieScreeningID();
			System.out.println(moviescreeningID);
			MovieScreening movieScreening = MovieScreeningController.getInstance().getMovieScreeningByScreeningID(moviescreeningID);
			int movieID = movieScreening.getMovieID();
			String movieName = MovieController.getInstance().getMovie(movieID).getMovieName();

			System.out.println(
					"Booking Transaction ID: " + booking.getTransactionID()
					+ "\nMovie watched: " +  movieName);
			System.out.printf("Time watched: " );
			movieScreening.getStartDateTime();
		}
	}

	/**
	 * Gets the channel reference of the MovieGoerUI.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the MovieGoerUI.
	 */
	public static MovieGoerUI getInstance() {
		if (instance == null) {
			instance = new MovieGoerUI();
		}
		return instance;
	}

}