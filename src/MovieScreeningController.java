import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 */
public class MovieScreeningController extends DatabaseController {

    private String DIR = "moviescreening/";
    private MovieScreeningController instance = null;
    private ArrayList<MovieScreening> movieScreenings;

    private MovieScreeningController(){}

    public MovieScreeningController getInstance() {
        if (instance == null)
            instance = new MovieScreeningController();
        return instance;
    }

    @Override
    public void readDB(){
        movieScreenings.clear();
        if (checkDirectoryExist(BASEDIR + DIR)) {
            File folder = new File(BASEDIR + DIR);
            File[] listOfFiles = folder.listFiles();
            try{
                for (File f : listOfFiles) {
                    if (f.isFile()){
                        List<String> text = retrieveData(BASEDIR + DIR + f.getName());

                        // MovieScreening Attributes
                        StringTokenizer aStr = new StringTokenizer(text.get(0), DELIMTER);
                        String  = aStr.nextToken();			// Name
                        String cineplexLocation = aStr.nextToken();		// Location

                        Calendar startTime = Calendar.getInstance();
                        startTime.setTimeInMillis(Long.parseLong(aStr.nextToken()));	// Start Time
                        Calendar endTime = Calendar.getInstance();
                        endTime.setTimeInMillis(Long.parseLong(aStr.nextToken()));		// End Time

                        String movieType = aStr.nextToken();							// Movie Type
                        int movieID = Integer.parseInt(aStr.nextToken());				// Movie ID

                    }
                }
            }
            catch (IOException io){
                System.out.println("Error! Unable to retrieve data from file.");
            }

        } else {
            System.out.println("Error, Directory not found! Database for Cineplex is not loaded!");
        }
    }

    @Override
    public void writeDB(){}

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
    {
        Calendar endTime = startTime;
        endTime.add(Calendar.MINUTE, MovieController.getInstance().getMovie(movieID).getDuration());
        for (int i = 0; i < movieScreenings.size() - 1 ; i ++){
            MovieScreening movieScreening = movieScreenings.get(i);
            if (movieScreening.getCinemaID() == cinemaID){
                // if startTime is lesser than one of the moviescreening's endTime
                if (startTime.compareTo(movieScreening.getEndTime()) == -1) {
                    System.out.println("This moviescreening's timing clashes with another moviescreening's timing!");
                    return false;
                }
            }
        }
        MovieScreening newMovieScreening = new MovieScreening(startTime, endTime, cinemaID,movieID);
        movieScreenings.add(newMovieScreening);
        return true;
    }

    /**
     * update the movieScreening given the new CinemaID, movieID and startTime
     * @param movieScreeningID the unique movieScreeningID to be updated.
     * @param newCinemaID an absolute ID which identifies the unique cinema.
     * @param newMovieID an absolute ID which identifies the unique movie.
     * @param newStartTime the new starting time of the movie
     * @return
     */
    public boolean updateMovieScreening(int movieScreeningID, int newCinemaID, int newMovieID, Calendar newStartTime)
    {
        MovieScreening oldMovieScreening = removeMovieScreening(movieScreeningID);
        if (addMovieScreening(newCinemaID,newMovieID,newStartTime))
            return true;
        else {
            addMovieScreening(oldMovieScreening.getCinemaID(),oldMovieScreening.getMovieID(),oldMovieScreening.getStartTime());
            return false;
        }
    }

    /**
     * removes the movieScreening based on its unique ID.
     * @param movieScreeningID
     * @return
     */
    public MovieScreening removeMovieScreening(int movieScreeningID){
        for (int i = 0; i < movieScreenings.size() - 1; i++)
        {
            if (movieScreenings.get(i).get)
        }
    }

    public boolean updateExpiry(){}

}
