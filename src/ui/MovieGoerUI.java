package ui;

import model.MovieScreening;
import model.Seat;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MovieGoerUI {

    private static MovieGoerUI Instance = null;

    private final List<String> stringList = Arrays.asList("A", "B", "C", "D", "E", "F", "G", " ", "H", "I", "J", "K", "L","M","N");


    public static MovieGoerUI getInstance() {
        if (Instance == null) {
            Instance = new MovieGoerUI();
        }
        return Instance;
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
        int choice = sc.nextInt();
        switch (choice) {
            case (1): bookTickets(movieScreening);
                break;
            case (2): //TODO: Add in the show main menu function
                break;
        }
    }

    private void bookTickets(MovieScreening movieScreening){
        Scanner sc = new Scanner(System.in);
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