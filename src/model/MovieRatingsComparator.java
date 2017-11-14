package model;

import java.util.Comparator;

public class MovieRatingsComparator implements Comparator<Movie> {
		public int compare(Movie m1, Movie m2) {
			double movierating1 = m1.getAvg_rating();
			double movierating2 = m2.getAvg_rating();

			//ascending order
			return Double.compare(movierating2, movierating1);
		}

}
