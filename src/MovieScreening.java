import java.util.Calendar;

public class MovieScreening {

    private Calendar startTime;

    private Calendar endTime;
    
    private int cinemaID;

    private String movieType;

    private int movieID;

    private Seat[][] seats;

    private boolean isExpired;

    public MovieScreening(Calendar startTime, Calendar endTime,
                          int cinemaId, int movieID){
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaID = cinemaId;
        //this.movieType = movieType;
        this.movieID = movieID;
        isExpired = false;

        // now it's 15 by 8 for this movie.
        seats = new Seat[15][8];
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public int getMovieId() {
        return movieID;
    }

    public void setMovieId(int movieID) {
        this.movieID = movieID;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setSeats(Seat[][] seatsForThisMovie) {
        this.seats = seatsForThisMovie;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Seat[][] getSeats() {
        return seats;
    }



}
