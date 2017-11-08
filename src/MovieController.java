import java.util.ArrayList;

public class MovieController{

    private String DIR = "movie/";
    /**
     * the instance of this database, default is NULL
     */
    private static MovieController Instance = null;

    private ArrayList<Movie> movieList;

    /**
     * MovieController can only be created itself
     */
    private MovieController() { }

    //reardDB
    //writeDB

    /**
     * create new database if there's no database
     * @return MovieController
     */
    public static MovieController getInstance() {
        if (Instance == null)
            Instance = new MovieController();
        return Instance;
    }

    //add this method to class diagram
    /**
     * exposes the private directory to initialize the retrieval of data.
     * @return MovieController path
     */
    public String getDir(){
        return DIR;
    }

    public ArrayList<Movie> getMovieList(){
        return movieList;
    }

    /**
     * add Movie into the movieList after staff has key in all the details required by the StaffUI.
     * @param movieId
     * @param movieName
     * @param synopsis
     * @param director
     * @param cast
     */
    public void addMovie(int movieId, String movieName, String synopsis, String director, String cast){
        Movie newMovie = new Movie(movieId, movieName, synopsis, director, cast);
        movieList.add(newMovie);
    }

    /**
     * get the Movie using movieId
     * @param movieId
     * @return Movie
     */
     public Movie getMovie(int movieId){
         for (Movie movie : movieList)
             if (movie.getId()== movieId)
                 return movie;
    }

}