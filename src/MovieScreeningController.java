import java.util.ArrayList;
import java.util.Calendar;

public class MovieScreeningController {

    private String DIR = "moviescreening/";
    private MovieScreeningController instance = null;
    private ArrayList<MovieScreening> movieScreenings;

    private MovieScreeningController(){}

    public MovieScreeningController getInstance() {
        if (instance == null)
            instance = new MovieScreeningController();
        return instance;
    }

    protected void readDB(){}

    protected void writeDB(){}

    public ArrayList<MovieScreening> getMovieScreenings(){
        return movieScreenings;
    }

    /**
     * Returns true if the new movieScreening is added successfully.
     *
     * @param cinemaID an absolute ID which identifies the unique cinema.
     * @param movieID an absolute ID which identifies the unique movie.
     * @param startTime the starting time of the movie
     * @return
     */
    public boolean addMovieScreening(int cinemaID, int movieID, Calendar startTime)
    {}

    /**
     * update the movieScreening given the new CinemaID, movieID and startTime
     * @param movieScreeningID the unique movieScreeningID to be updated.
     * @param newCinemaID an absolute ID which identifies the unique cinema.
     * @param newMovieID an absolute ID which identifies the unique movie.
     * @param newStartTime the new starting time of the movie
     * @return
     */
    public boolean updateMovieScreening(int movieScreeningID, int newCinemaID, int newMovieID, Calendar newStartTime)
    {}

    /**
     * removes the movieScreening based on its unique ID.
     * @param movieScreeningID
     * @return
     */
    public boolean removeMovieScreening(int movieScreeningID){}

    public boolean updateExpiry(){}

}
