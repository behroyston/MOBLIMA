package model;

/**
 * Represents a Cinema in a Cineplex.
 * A cinema contains seats and have a defined schedule of screenings.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public class Cinema{
	/**
	 * The cinema ID corresponding to this cinema.
	 */
	private int cinemaID;

	/**
	 * The class of this cinema (e.g Platinum).
	 */
	private CinemaClassType cinemaClassType;


	/**
	 * Seat layout of this cinema.
	 * Entries are 1 for a seat and 0 for a space.
	 */
	private char[][] seatLayout = new char[8][15];

	/**
	 * Creates a new Cinema with its given ID, classType and seat layout.
	 * @param cinemaID 			Cinema ID corresponding to this cinema.
	 * @param cinemaClassType 	Class of this cinema.
	 * @param seatLayout		Seat Layout of the cinema.
	 */
	public Cinema(int cinemaID, CinemaClassType cinemaClassType, char[][] seatLayout){
		this.cinemaID = cinemaID;
		this.cinemaClassType = cinemaClassType;
		setSeatLayout(seatLayout);
	}

	/**
	 * Print this cinema attributes
	 */
	public void printCinemaInfo(){
		System.out.println("Cinema " + cinemaID + "(Class Type: " + cinemaClassType + ")");
	}

	/**
	 * Gets the Cinema ID of this cinema.
	 * @return Cinema ID of this cinema.
	 */
	public int getCinemaID(){
		return cinemaID;
	}

	/**
	 * Change the model.Cinema ID of this cinema.
	 * @param cinemaID New model.Cinema ID of this cinema.
	 */
	public void setCinemaID(int cinemaID){
		this.cinemaID = cinemaID;
	}

	/**
	 * Gets the class of this cinema.
	 * @return Class of this cinema.
	 */
	public CinemaClassType getCinemaClassType() {
		return cinemaClassType;
	}
	/**
	 * Change the class of this cinema.
	 * @param cinemaClassType New class of this cinema.
	 */
	public void setCinemaClassType(CinemaClassType cinemaClassType) {
		this.cinemaClassType = cinemaClassType;
	}

	/**
	 * Get the seat layout of this cinema.
	 * @return Seat layout of this cinema
	 */
	public char[][] getSeatLayout(){
		return seatLayout;
	}


	/**
	 * Set the seat layout for this cinema.
	 * @param layout New seat layout of this cinema
	 */
	public void setSeatLayout(char[][] layout){
		if (layout.length != seatLayout.length){		// Check that the structure is the same
			System.out.println("model.Seat layout not of correct dimensions!");
			return;
		}
		for (int i = 0; i < layout.length; i++){
			if (layout[i].length == seatLayout[i].length) // Check that the structure is the same	
				System.arraycopy(layout[i], 0, seatLayout[i], 0, layout[i].length);
			else{
				System.out.println("model.Seat layout not of correct dimensions!");
				return;
			}
		}
	}
}
