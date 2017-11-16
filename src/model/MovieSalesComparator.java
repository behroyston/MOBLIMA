package model;

import java.util.Comparator;

/**
 * Class to compare between sales of movies.
 * This class implements the Comparator within the Java Collection Framework.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieSalesComparator implements Comparator<Movie>{
	/**
	 * Compare the sales of 2 movies and do so in ascending order
	 */
	public int compare(Movie m1, Movie m2) {
		double movieSales1 = m1.getTicketSales();
		double movieSales2 = m2.getTicketSales();

		//ascending order
		return Double.compare(movieSales2, movieSales1);
	}
}
