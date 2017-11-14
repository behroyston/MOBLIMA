package model;

import java.util.Comparator;

public class MovieSalesComparator implements Comparator<Movie>{
	public int compare(Movie m1, Movie m2) {
		double movieSales1 = m1.getTicketSales();
		double movieSales2 = m2.getTicketSales();

		//ascending order
		return Double.compare(movieSales2, movieSales1);
	}
}
