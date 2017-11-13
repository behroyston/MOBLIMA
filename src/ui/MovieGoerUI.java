//package ui;
//
//import controller.BookingController;
//import controller.MovieController;
//import model.Booking;
//import model.Movie;
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class MovieGoerUI {
//
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
//
//}