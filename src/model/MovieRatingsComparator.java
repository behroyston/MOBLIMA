package model;

import java.util.Comparator;

/**
 * Class to compare between ratings of movies.
 * This class implements the Comparator within the Java Collection Framework.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public class MovieRatingsComparator implements Comparator<Movie> {
	/**
	 * Compare the ratings of 2 movies and do so in ascending order
	 */
	public int compare(Movie m1, Movie m2) {
		double movierating1 = m1.getAvg_rating();
		double movierating2 = m2.getAvg_rating();

		//ascending order
		return Double.compare(movierating2, movierating1);
	}

}
