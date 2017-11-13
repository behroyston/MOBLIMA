package ui;

import controller.CineplexController;
import model.Booking;
import model.Cinema;
import model.Cineplex;
import model.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestApp {
	public static void main(String[] args) {

//        MovieController movieController = MovieController.getInstance();
//        movieController.setMovieList(createSampleMovie());
//        movieController.writeDB();
//        movieController.readDB();
//        movieController.printMovieLists();

//        BookingController bookingController = BookingController.getInstance();
////        bookingController.setBookingList(createSampleBooking());
////        bookingController.writeDB();
//        bookingController.readDB();
//        bookingController.printBookingList();

		// Manual text format - preferred here because it simulates the allowance of staff to add new cineplex/cinema to DB
		CineplexController control = CineplexController.getInstance();
		ArrayList<Cineplex> cineplexList ;
		cineplexList = createSample();
		control.setCineplexList(cineplexList);
		control.writeDB();
//		//control.readDB();
//		cineplexList = control.getCineplexList();
//		System.out.println("---------------------------------------");
//		printCineplex(cineplexList);
		
		/*control.writeDB();
		System.out.println("---------------------------------------");
		printCineplex(control.getCineplexList());*/
		/*
		
		// Test the functionality of create movie screening of model.Cineplex Controller
		System.out.println("---------------------------------------");
		int cineplexID = 1, cinemaID = 1, movieID = 100;
		String movieType = "3D";
		Calendar aDate = new GregorianCalendar(2017, 11, 5, 5, 00);
		Calendar startTime = (Calendar) aDate.clone();
		Calendar endTime = (Calendar) (new GregorianCalendar(2017, 11, 5, 8, 30)).clone();
		control.addMovieScreening(cineplexID, cinemaID, movieID, movieType, startTime, endTime);
		printCineplex(control.getCineplexList());
		
		// Test the functionlity of update movie screening of model.Cineplex Controller
		System.out.println("---------------------------------------");
		int screeningID = 2;
		aDate = new GregorianCalendar(2017, 11, 4, 22, 0);
		startTime = (Calendar) aDate.clone();
		endTime = (Calendar) (new GregorianCalendar(2017, 11, 5, 8, 30)).clone();
		
		control.updateMovieScreening(cineplexID, cinemaID, screeningID, movieID, movieType, startTime, endTime);
		printCineplex(control.getCineplexList());
		
		// Test the functionlity of remove movie screening of model.Cineplex Controller
		System.out.println("---------------------------------------");
		control.removeMovieScreening(2, cinemaID, screeningID);
		printCineplex(control.getCineplexList());
		*/
		
		// Using Serializable
		/*
		 * writeSampleFile();


		List list;
		try	{
				// read from serialized file the list of professors
				list = (ArrayList) readSerializedObject("test.dat");
				for (int i = 0 ; i < list.size() ; i++) {
					model.Cineplex p = (model.Cineplex)list.get(i);
					System.out.println("Name is " + p.getName() );
					System.out.println("Location is " + p.getLocation() );
				}


		}  catch ( Exception e ) {
					System.out.println( "Exception >> " + e.getMessage() );
		}
		 */
	}

	public static List readSerializedObject(String filename) {
		List pDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			pDetails = (ArrayList) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		// print out the size
		//System.out.println(" Details Size: " + pDetails.size());
		//System.out.println();
		return pDetails;
	}

	public static void writeSerializedObject(String filename, List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
			//	System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


//	public static ArrayList<MovieScreening> createSampleForMovieScreening() {
////		    public model.MovieScreening(Calendar startTime, Calendar endTime,
////		int cinemaId, int movieID, String movieType){
//		ArrayList<MovieScreening> movieScreeningList = new ArrayList<>();
//		Calendar aDate, startTime, endTime;
//	}

	public static ArrayList<Cineplex> createSample(){
		ArrayList<Cineplex> cineplexList = new ArrayList<>();
		ArrayList<Cinema> cin = new ArrayList<>();
		char [][] seatsLayout = new char[8][15];
		for (int i = 0; i < seatsLayout.length; i++)
			for (int j = 0; j < seatsLayout[i].length && i != 6; j++)
				seatsLayout[i][j] = 'O';
		for (int i = 0; i < seatsLayout.length; i++){
			for (int j = 0; j < seatsLayout[i].length ; j++)
				System.out.print(seatsLayout[i][j]);
			System.out.println();
		}
		
		cin.add(new Cinema(1, "Regular", seatsLayout));
		seatsLayout[7][14] = ' ';
		cin.add(new Cinema(2, "Regular", seatsLayout));

		/*Calendar aDate, startTime, endTime;
		aDate = new GregorianCalendar(2013, 3, 5, 8, 00);
		startTime = (Calendar) aDate.clone();
		endTime = (Calendar) (new GregorianCalendar(2013, 3, 5, 8, 30)).clone();
		addMovieScreening(startTime, endTime, "2D", 1);
		ArrayList<model.MovieScreening> at = cin.get(0).getMovieScreenings();
		printMovieTimings(at);

		System.out.println("LOL!~~~~~~~~~~");
		startTime = (Calendar) (new GregorianCalendar(2013, 3, 5, 7, 51)).clone();
		endTime = (Calendar) (new GregorianCalendar(2013, 3, 5, 7, 55)).clone();
		System.out.println(cin.get(0).addMovieScreening(startTime, endTime, "2D", 2));
		printMovieTimings(at);

		System.out.println("LOL!~~~~~~~~~~");
		startTime = (Calendar) (new GregorianCalendar(2013, 3, 5, 7, 40)).clone();
		endTime = (Calendar) (new GregorianCalendar(2013, 3, 5, 7, 50)).clone();
		System.out.println(cin.get(1).addMovieScreening(startTime, endTime, "2D", 3));
		printMovieTimings(at);*/

		cineplexList.add(new Cineplex("Bugis model.Cineplex", "Bugis", cin));
		System.out.println("LOL3!~~~~~~~~~~");
		ArrayList<Cinema> cin2 = new ArrayList<>();
		seatsLayout[6][12] = ' ';
		cin2.add(new Cinema(3, "Platinum Suites", seatsLayout));
		seatsLayout[6][12] = 'O';
		cin2.add(new Cinema(4, "Regular", seatsLayout));

		/*aDate = new GregorianCalendar(2017, 3, 5, 8, 00);
		startTime = (Calendar) aDate.clone();
		endTime = (Calendar) (new GregorianCalendar(2017, 3, 5, 8, 30)).clone();
		cin2.get(0).addMovieScreening(startTime, endTime, "2D", 10);
		ArrayList<model.MovieScreening> at2 = cin2.get(0).getMovieScreenings();
		printMovieTimings(at2);

		System.out.println("LOL2!~~~~~~~~~~");
		startTime = (Calendar) (new GregorianCalendar(2017, 3, 5, 7, 51)).clone();
		endTime = (Calendar) (new GregorianCalendar(2017, 3, 5, 7, 55)).clone();
		System.out.println(cin2.get(0).addMovieScreening(startTime, endTime, "2D", 20));
		printMovieTimings(at2);

		System.out.println("LOL2!~~~~~~~~~~");
		startTime = (Calendar) (new GregorianCalendar(2017, 3, 5, 7, 40)).clone();
		endTime = (Calendar) (new GregorianCalendar(2017, 3, 5, 7, 50)).clone();
		System.out.println(cin2.get(1).addMovieScreening(startTime, endTime, "2D", 30));
		printMovieTimings(at2);*/

		cineplexList.add(new Cineplex("Bishan model.Cineplex", "Bishan", cin2));
		return cineplexList;
	}

	private static ArrayList<Movie> createSampleMovie(){
        ArrayList<String> reviews = new ArrayList<String>();
        reviews.add("It's great!");
        reviews.add("It sucks");
        ArrayList<Double> ratingList = new ArrayList<>();
        ratingList.add(2.0);
        ratingList.add(2.0);

        Movie movie1 = new Movie(1,"Kingsman","abc","Matthew Vaughn","Taron Egerton", "Now Showing");
        Movie movie2 = new Movie(2,"Marvel's Thor: Ragnarok", "abcd", "Taika Waititi","Chris Hemworth", "Now Showing");
        Movie movie3 = new Movie(3, "Ah Boys to Men 4","abcde", "Jack Neo", "Joshua Tan", "Now Showing",2,true,10,130,
                reviews, ratingList );
        ArrayList <Movie> movieLists = new ArrayList<>();
        movieLists.add(movie1);
        movieLists.add(movie2);
        movieLists.add(movie3);
        return movieLists;
    }

    private static ArrayList<Booking> createSampleBooking(){
	    Booking booking1 = new Booking(1,"Royston","91234567","behroyston@gmail.com",1);
        Booking booking2 = new Booking(1,"Amos","91234568","random@gmail.com",1);
        Booking booking3 = new Booking(1,"Yandao","91234569","random1@gmail.com",1);
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);
        bookings.add(booking2);
        bookings.add(booking3);

        return bookings;
    }

	public static void writeSampleFile(){
		ArrayList<Cineplex> cineplexList = createSample();
		writeSerializedObject("test.dat", cineplexList);
		System.out.println("DONE!"); 
	}
	
	public static void printCineplex(ArrayList<Cineplex> cineplexList){
		for (Cineplex cineplex : cineplexList){
			System.out.println("model.Cineplex Name: " + cineplex.getName());
			System.out.println("Location: " + cineplex.getLocation());
			System.out.println("Cinemas: ");
			printCinemas(cineplex.getCinemaList());
		}
	}
	
	public static void printCinemas(ArrayList<Cinema> cinemaList){
		for (Cinema cinema : cinemaList){
			System.out.print("CinemaID: " + cinema.getCinemaID());
			System.out.println(" (Class Type: " + cinema.getClassType() + ")");
			System.out.println("model.Seat Layout: ");
			char[][] seatLayout = cinema.getSeatLayout();
			for (int i = 0; i < seatLayout.length; i++){
				for (int j = 0; j < seatLayout[i].length; j++)
					if (seatLayout[i][j] == 'O')
						System.out.print("O");
					else
						System.out.print(" ");
				System.out.println();
			}
		}
	}

	/*public static void printMovieTimings(ArrayList<model.MovieScreening> at){
		for (model.MovieScreening movie: at){
			System.out.print("MovieID: " + movie.getMovieID() + " - ");
			System.out.print(movie.getStartTime().getTime() + " to ");
			System.out.println(movie.getEndTime().getTime());
		}
	}*/
}
