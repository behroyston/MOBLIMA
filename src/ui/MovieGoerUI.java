package ui;

import controller.*;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MovieGoerUI {

	private static MovieGoerUI instance = null;

	Scanner sc = new Scanner(System.in);

	private MovieGoer movieGoer;

	private String emailAddress;

	private final List<String> stringList = Arrays.asList("A", "B", "C", "D", "E", "F", "G", " ", "H", "I", "J", "K", "L","M","N");

	private MovieGoerUI(){};

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

	private int checkIfInt(int value){
		if (!sc.hasNextInt()){
			sc.next(); // Remove the invalid input
			return value;
		}
		return sc.nextInt();
	}

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

	private String loginValidation(){
		String email, password;
		MovieGoer movieGoer;
		System.out.println("Login System:");

		System.out.print("Please enter your email address: ");
		email = sc.next();
		System.out.print("Please enter your password: ");
		password = sc.next();
		movieGoer = MovieGoerController.getInstance().validateCustomer(email, password);

		if (movieGoer == null){
			System.out.println("Invalid login details!");
			return null;
		}
		this.movieGoer = movieGoer;
		return movieGoer.getEmail();
	}

	// We need a logic to check that 'Now Showing' & 'Preview' should set isShowing == true
	private void listMovies(){
		System.out.println("The movies that are coming soon, previewing or showing: ");
		MovieController.getInstance().printMovieNames();
	}

	private void printShowingMovies(){
		ArrayList<Movie> movieList = MovieController.getInstance().getShowingMovieList();
		System.out.println("The list of movies that are currently showing: ");
		for (int i = 0; i < movieList.size(); i++){
			System.out.println((i+1) + ") " + movieList.get(i).getMovieName());
		}
	}

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
		System.out.println("MovieScreenings:");
		for (int i = 0; i< movieScreenings.size(); i++)
		{
			int cinemaID = movieScreenings.get(i).getCinemaID();
			System.out.print((i+1) + ") ");
			System.out.print(movieScreenings.get(i).getStartDateTime() + " at " +
					cineplexController.getCineplexByCinemaID(cinemaID).getName());
			System.out.println(" - Cinema "+ cineplexController.getCinema(cinemaID).getCinemaID());
		}
		System.out.println("Enter the Movie Screening Number if you want to book seats!");

		int selection = sc.nextInt();
		showSeatsAvailability(movieScreenings.get(selection-1).getMovieScreeningID());
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

	private void enterReviewRating(){
		// TODO: Validate that the movie-goer has watched the movie
		ArrayList<Booking> userBookings = BookingController.getInstance().getAllBookingByUser(emailAddress);
		MovieController movieController = MovieController.getInstance();
		MovieScreeningController movieScreeningController = MovieScreeningController.getInstance();
		ArrayList<Integer> movieIDs = new ArrayList<>();

		for (Booking booking : userBookings){
			int screeningID = booking.getMovieScreeningID();
			MovieScreening movieScreening = movieScreeningController.getMovieScreeningByScreeningID(screeningID);
			int movieID = movieScreening.getMovieID();
			if (!movieIDs.contains(movieID))
				movieIDs.add(movieID);
		}

		// Enter review
		if (movieIDs.size() == 0){
			System.out.println("You have not watched any movies yet.");
			return;
		}
		else
			System.out.println("You have watched the following movies:");
		for (int i = 1; i <= movieIDs.size(); i++)
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
	}

	public static MovieGoerUI getInstance() {
		if (instance == null) {
			instance = new MovieGoerUI();
		}
		return instance;
	}

	// traverses through MovieController.getCineplex(position).getCinema(position).getMovieScreening(position).getSeatsForThisMovie
	public void showSeatsAvailability(int movieScreeningID){

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
		movieScreening.printMovieScreeningInfo();

		Scanner sc = new Scanner(System.in);
		System.out.println("1. Book Seat\n2. Go Back to Main Menu");
		int choice = checkIfInt(2);
		switch (choice) {
		case (1): bookTickets(movieScreeningID);
		break;
		case (2): //TODO: Add in the show main menu function
			break;
		}
	}

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
			//TODO AddBooking through the BookingManager
			int cinemaID = movieScreening.getCinemaID();
			int age = movieGoer.getAge();
			int type = 0;
			if (age <= 10)
				type = 1;
			else if (age >= 65)
				type = 2;

			// Ask for payment
			double price = BookingController.getInstance().calculatePrice(type, movieScreeningID);
			System.out.printf("Please make payment of SGD %.2f\n (inclusive of 7% GST)", price);
			System.out.print("Enter anything to pay :");
			sc.nextLine(); sc.nextLine();
			BookingController.getInstance().addBooking(movieGoer.getName(),movieGoer.getMobileNumber(),
					movieGoer.getEmail(),cinemaID,movieScreeningID, price);
		}
		else
			System.out.println("The seat is booked! Please select another seat.");
		showSeatsAvailability(movieScreeningID);
	}

	public void viewBookingHistory() {
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

}