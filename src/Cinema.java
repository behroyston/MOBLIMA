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
	 * The class of this cinema (e.g Platinum Movie Suites).
	 */
	private String classType;
	
	/**
	 * Seat layout of this cinema.
	 * Entries are 1 for a seat and 0 for a space.
	 */
	private int[][] seatLayout = new int[15][8];
	
	/**
	 * The list of seats for this cinema.
	 */
	//private ArrayList<Seat> seats;
	
	/**
	 * Creates a new Cinema with its given ID, classType and seats.
	 * Also initalise the movieScreenings.
	 * @param cinemaID Cinema ID corresponding to this cinema.
	 * @param classType Class of this cinema.
	 * @param seatLayout
	 */
	public Cinema(int cinemaID, String classType, int[][] seatLayout){
		this.cinemaID = cinemaID;
		this.classType = classType;
		setSeatLayout(seatLayout);
	}
		
	/**
	 * Gets the Cinema ID of this cinema.
	 * @return Cinema ID of this cinema.
	 */
	public int getCinemaID(){
		return cinemaID;
	}
	
	/**
	 * Change the Cinema ID of this cinema.
	 * @param cinemaID New Cinema ID of this cinema.
	 */
	public void setCinemaID(int cinemaID){
		this.cinemaID = cinemaID;
	}
	
	/**
	 * Gets the class of this cinema.
	 * @return Class of this cinema.
	 */
	public String getClassType(){
		return classType;
	}
	
	/**
	 * Change the class of this cinema.
	 * @param classType New class of this cinema.
	 */
	public void setClassType(String classType){
		this.classType = classType;
	}
	
	/**
	 * Get the seat layout of this cinema.
	 * @return Seat layout of this cinema
	 */
	public int[][] getSeatLayout(){
		return seatLayout;
	}
	
	
	/**
	 * Set the seat layout for this cinema.
	 * @param layout New seat layout of this cinema
	 */
	public void setSeatLayout(int[][] layout){
		if (layout.length != seatLayout.length){		// Check that the structure is the same
			System.out.println("Seat layout not of correct dimensions!");
			return;
		}
		for (int i = 0; i < layout.length; i++){
			if (layout[i].length == seatLayout[i].length) // Check that the structure is the same	
				System.arraycopy(layout[i], 0, seatLayout[i], 0, layout[i].length);
			else{
				System.out.println("Seat layout not of correct dimensions!");
				return;
			}
		}
	}
	
	// Below are the old codes pending removal
	/**
	 * Gets the list of movie screenings for this cinema.
	 * @return List of movie screenings.
	 */
	/*public ArrayList<MovieScreening> getMovieScreenings(){
		return movieScreenings;
	}*/
	
	/**
	 * Checks for any timeslot clashes in the movie screenings for this cinema
	 * and find the appropriate index to insert the new movie screening into the
	 * list to ensure it remains sorted.
	 * @param startTime Starting time of the new movie screening.
	 * @param endTime 	Ending time of the new movie screening.
	 * @return			-1 if there is a clash. Non-negative value indicating the index to insert otherwise.
	 */
	/*private int checkForTimeClash(Calendar startTime, Calendar endTime){
		int index = -1;
		for (int i = 0; i < movieScreenings.size(); i++){
			if (startTime.after(movieScreenings.get(i).getEndTime()))	// Ensure that movieScreenings remains sorted
				index = i;
			else if (endTime.compareTo(movieScreenings.get(i).getStartTime()) >= 0)	// Avoid clashes in timing
				return -1;
		}
		return index+1;
	}*/
	
	/**
	 * Add a new movie screening for this cinema. It will also
	 * checks for any possible crashes of timing of the screenings.
	 * @param startTime	Starting time of the movie screening.
	 * @param endTime 	Ending time of the movie screening.
	 * @param movieType	Movie type of the movie screening.
	 * @param movieID	Movie ID of the movie being screened.
	 * @return			Returns true if movie screening is added successfully. Returns false otherwise.
	 */
	/*public boolean addMovieScreening(Calendar startTime, Calendar endTime, String movieType, int movieID){
		int index = checkForTimeClash(startTime, endTime);
		if (index == -1 || startTime.after(endTime))
			return false;
		movieScreenings.add(index, new MovieScreening(startTime, endTime, movieType, movieID));
		return true;
	}*/
	
	/**
	 * Update a new movie screening for this cinema. It will also checks
	 * for any possible crashes of timing based on the update.
	 * @param screeningID	ID of the movie screening.
	 * @param startTime		Starting time of the movie screening.
	 * @param endTime		Ending time of the movie screening.
	 * @param movieType		Movie type of the movie screening.
	 * @param movieID		Movie ID of the movie being screened.
	 * @return 				Returns true if movie screening is updated successfully. Returns false otherwise.
	 */
	/*public boolean updateMovieScreening(int screeningID, Calendar startTime, Calendar endTime, String movieType, int movieID){
		if (screeningID < 0 || screeningID >= movieScreenings.size()){
			System.out.println("Error! Invalid movie screening selected!");
			return false;
		}	
		else{
			// Check if the changes are alright first before committing the change (by using a backup copy)
			ArrayList<MovieScreening> temp = new ArrayList<>(movieScreenings);	
			if (!removeMovieScreening(screeningID) || !addMovieScreening(startTime, endTime, movieType, movieID)){
				movieScreenings = temp;
				return false;
			}
		}
		return true;
	}*/

	/**
	 * Remove a movie screening from this cinema. It will checks
	 * if the movie screening exists before removal.
	 * @param screeningID	ID of the movie screening.
	 * @return				Returns true if movie screening is removed successfully. Returns false otherwise.
	 */
	/* Find for the movieID and timeslot to remove, return true if it remove successfully and return false otherwise */
	/*public boolean removeMovieScreening(int screeningID){
		if (screeningID < 0 || screeningID >= movieScreenings.size()){
			System.out.println("Error! Invalid movie screening selected!");
			return false;
		}
		else
			movieScreenings.remove(screeningID);
		return true;
	}*/

	
}
