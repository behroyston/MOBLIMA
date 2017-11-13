package model;

import controller.CineplexController;

import java.util.Calendar;

public class MovieScreening {

    private int movieScreeningID;

    private int movieID;

    private int cinemaID;

    private Calendar startTime;

    private Calendar endTime;

    private String movieType;

    private Seat[][] seats;

    private boolean isExpired;

    public MovieScreening(int movieScreeningID, Calendar startTime, Calendar endTime, String movieType,
                          int cinemaID, int movieID){
        this.movieScreeningID = movieScreeningID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaID = cinemaID;
        this.movieType = movieType;
        this.movieID = movieID;
        isExpired = false;

        // for now it's 15 by 8
        char[][] seatLayout = CineplexController.getInstance().getCinema(cinemaID).getSeatLayout();
        int y = seatLayout[0].length;
        int x = seatLayout.length;
        seats = new Seat[x][y];
        for (int i = 0; i < y; i++){
            for (int j = 0; j < x; j++) {
                seats[j][i] = new Seat(j*i+(j+1));
            }
        }
    }

    public MovieScreening(int movieScreeningID, Calendar startTime, Calendar endTime, String movieType,
                          int cinemaID, int movieID, Seat[][] seats){
        this.movieScreeningID = movieScreeningID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaID = cinemaID;
        this.movieType = movieType;
        this.movieID = movieID;
        isExpired = false;
        this.seats = seats;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
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

    public boolean getIsExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public int getMovieScreeningID() {
        return movieScreeningID;
    }

    public void setMovieScreeningID(int movieScreeningID) {
        this.movieScreeningID = movieScreeningID;
    }
}
