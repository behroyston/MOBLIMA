package ui;

import model.Booking;
import model.Movie;
import model.MovieScreening;
import model.Seat;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.BookingController;
import controller.MovieController;
import controller.MovieGoerController;

public class MovieGoerUI {

	private static MovieGoerUI instance = null;

	Scanner sc = new Scanner(System.in);

	private final List<String> stringList = Arrays.asList("A", "B", "C", "D", "E", "F", "G", " ", "H", "I", "J", "K", "L","M","N");

	private MovieGoerUI(){};

	public void display(){
		// Validation first
		System.out.println("-------------------- Welcome to MOBLIMA! --------------------");
		String emailAddress = loginValidation();
		if (emailAddress == null){
			return;
		}
		

		// Movie-Goer Options
		int choice;
		do{
			System.out.println();
			System.out.println("1. Search/List movie");
			System.out.println("2. View movie details - including review and ratings");
			System.out.println("3. List the Top 5 ranking by ticket sales OR by overall reviewers' ratings");
			System.out.println("4. Check seat availability and selection of seats");
			System.out.println("5. Book and purchase ticket");
			System.out.println("6. View booking history");
			System.out.println("7. Enter review or rating for a movie you have watched");
			System.out.println("8. Go back to previous menu");
			System.out.print("Enter choice: ");
			choice = checkIfInt(8);
			switch (choice){
			case 1:
				listSearchMovies();
				break;
			case 2:
				viewMovieDetail();
				break;
			case 3:
				listTopRankings();
				break;
			case 4:
				//showSeatsAvailability();
				break;
			case 5:
				//bookTickets();
				break;
			case 6:
				//viewBookingHistory();
				break;
			case 7:
				enterReviewRating(emailAddress);
			case 8:
				break; 
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		}while(choice != 8);
	}
	
	private int checkIfInt(int value){
	    if (!sc.hasNextInt()) {
	        sc.next(); 	// Remove the invalid input
	        return value;
	    }
	    return sc.nextInt();
	}

	// Current display seems to be implemented in MovieGoer - may need to revise this
	private String loginValidation(){
		System.out.println("Please enter your email address: ");
		String email = sc.next();
		System.out.println("Please enter your password: ");
		String password = sc.next();
		//MovieGoerController.getInstance().validate();
		return "abc@email.com";
	}

	// We need a logic to check that 'Now Showing' & 'Preview' should set isShowing == true
	private void listSearchMovies(){
		int choice;
		System.out.println("Select if you would like to search for a movie or list all available movies:");
		do{
			System.out.println("1. Search for a movie");
			System.out.println("2. List all available movies");
			System.out.println("3. Go back to previous menu");
			System.out.print("Enter choice: ");
			choice = checkIfInt(4);
			switch (choice){
			case 1:
				System.out.println("Please enter the name of the movie: ");
				sc.nextLine();                      // Clear buffer
				String movieName = sc.nextLine();
				Movie showingMovie = MovieController.getInstance().getShowingMovieByName(movieName);
				if (showingMovie == null)
					System.out.println("Movie not found. It is either not showing currently or you have mistyped the movie name");
				else
					System.out.println(showingMovie.getMovieName() + " is currently showing.");
				break;
			case 2:
				printShowingMovies();
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		}while (choice > 3 || choice < 1);
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
				if (showingMovie != null)
					showingMovie.printInfo();
				break;
			case 2:
				ArrayList<Movie> movieList = MovieController.getInstance().getShowingMovieList();
				printShowingMovies();
				System.out.print("Enter corresponding number: ");
				int num = checkIfInt(-1);
				if (num > movieList.size() || num < 1){
					System.out.println("Invalid Number!");
				}
				else
					movieList.get(num-1).printInfo();
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		}while (choice > 3 || choice < 1);
	}

	private void listTopRankings(){
		MovieController movieController = MovieController.getInstance();
		String[] postfix = {"st", "nd", "rd", "th"};
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
				ArrayList<Movie> topMoviesBySales = movieController.getTopFiveBySales();
				for (int i = 1; i <= topMoviesBySales.size(); i++){
					Movie movie = topMoviesBySales.get(i-1);
					System.out.println(i + postfix[Math.min(i, postfix.length)-1] + ": " + movie.getMovieName() + 
							" (Ticket Sales - $" + movie.getTicketSales() + ")");
				}
				break;
			case 2:
				ArrayList<Movie> topMoviesByRatings = movieController.getTopFiveByRatings();
				for (int i = 1; i <= topMoviesByRatings.size(); i++){
					Movie movie = topMoviesByRatings.get(i-1);
					System.out.println(i + postfix[Math.min(i, postfix.length)-1] + ": " + movie.getMovieName() + 
							" (Overall Rating - " + movie.getAvg_rating() + "/5)");
				}
				break;
			case 3:
				return;
			default:
				System.out.println("Invalid choice! Please re-enter...");
			}
		}while (choice > 3 || choice < 1);
	}
	
	private void enterReviewRating(String emailAddress){
		// TODO: Validate that the movie-goer has watched the movie
		ArrayList<Booking> userBookings = BookingController.getInstance().getAllBookingByUser(emailAddress);
		System.out.println("You have watched the following movies:");
		/*for (int i = 1; i <= userBookings.size(); i++){
			System.out.println(userBookings.get(i-1).);
		}
		*/
		// Enter review
		int movieID = 1;
		Movie movie = MovieController.getInstance().getMovie(movieID);
		System.out.print("Enter your rating: ");
		double rating = sc.nextDouble();
		movie.addRating(rating);
		System.out.print("Enter your review: ");
		sc.nextLine(); 			// Clear buffer
		String review = sc.nextLine();
		movie.addReview(review);
	}

	public static MovieGoerUI getInstance() {
		if (instance == null) {
			instance = new MovieGoerUI();
		}
		return instance;
	}


	//    ArrayList<Movie> movieList = MovieController.getMovieList();
	//
	//    Scanner sc = new Scanner(System.in);
	//
	//    private static MovieGoerUI Instance = null;
	//
	//    public static MovieGoerUI getInstance() {
	//        if (Instance == null) {
	//            Instance = new MovieGoerUI();
	//        }
	//        return Instance;
	//    }
	//
	//    public void loginValidation(){
	//        System.out.println("-----------------------USER LOGIN------------------------");
	//        if (MovieGoer.login())
	//                System.out.println("Login Successfully!");
	//        System.out.println("-------------------"+MovieGoer.getCusID()+"-----------------");
	//    }
	//
	//    public void listMovies(){
	//        System.out.println("Now showing: ");
	//        for (Movie movie : movieList)
	//            if (movie.getIsShowing())
	//                System.out.println("ID: "+ movie.getId()+ " Name: "+ movie.getMovieName());
	//    }
	//
	//    public void viewMovieDetail(){
	//        System.out.println("Enter movie ID for viewing detail: ");
	//        int movieId = sc.nextInt();
	//        for (Movie movie : movieList)
	//            if (movie.getId() == movieId)
	//                System.out.println("ID: "+ movie.getId()+ " Name: "+ movie.getMovieName()+ " Synopsis: "+ movie.getSynopsis()+ " Director: " + movie.getDirector() + " Cast: " + movie.getCast());
	//    }
	//    /**
	//     * print out booking history
	//     */
	//    public void viewBookingHistory(){
	//        System.out.println("This is your booking history: ");
	//        for (Booking booking : BookingController.getBookingList())
	//            System.out.println(booking.transactionID + booking.cinemaID + booking.movieScreening);
	//    }
	//
	//    /**
	//     * ask the user to enter a review
	//     */
	//    public void enterReviewRating(){
	//        System.out.println("Please enter your review: ");
	//        String newReview = sc.next();
	//        Movie.addReviews(Movie.reviews) = newReview;
	//    }
	// traverses through MovieController.getCineplex(position).getCinema(position).getMovieScreening(position).getSeatsForThisMovie
	public void showSeatsAvailability(MovieScreening movieScreening){

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

		movieScreening.printMovieScreeningInfo();

		System.out.println("\t<<<SCREEN>>>\t");

		Scanner sc = new Scanner(System.in);
		System.out.println("1. Book Seat\n2. Go Back to Main Menu");
		int choice = checkIfInt(2);
		switch (choice) {
		case (1): bookTickets(movieScreening);
		break;
		case (2): //TODO: Add in the show main menu function
			break;
		}
	}

	private void bookTickets(MovieScreening movieScreening){
		int number;
		String alphabet;
		System.out.println("\"Please enter the Seat Alphabet you wished to book!");
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
		int horizontalIndex = stringList.indexOf(alphabet);

		Seat[][] seats = movieScreening.getSeats();


		Seat selectedSeat = seats[number-1][horizontalIndex];
		if (!selectedSeat.getIsBooked()) {
			//TODO AddBooking through the BookingManager
			//BookingController.getInstance().addBooking(//user,);
			selectedSeat.setIsBooked(true);
		}
		else
			System.out.println("The seat is booked! Please select another seat.");
		showSeatsAvailability(movieScreening);
	}

}