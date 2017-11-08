import java.util.Calendar;

public class MovieScreening {

    private Calendar dateTime;

    private String movieType;

    private int movieId;

    private Seat[][] seatsForThisMovie;

    public MovieScreening(int i){
        movieId = i;
        dateTime = Calendar.getInstance();
        // now it's 15 by 8 for this movie.
        seatsForThisMovie = new Seat[15][8];
    }
    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Seat[][] getSeatsForThisMovie() {
        return seatsForThisMovie;
    }
}
