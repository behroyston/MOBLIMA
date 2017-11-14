package model;

import java.util.Calendar;

public class MovieScreening {

    private int movieScreeningID;

    private int movieID;

    private int cinemaID;

    private Calendar startTime;

    private Calendar endTime;

    private MovieClassType movieClassType;

    private Seat[][] seats;

    private boolean isExpired;

    public MovieScreening(int movieScreeningID, Calendar startTime, Calendar endTime, MovieClassType movieType,
                          int cinemaID, int movieID){
        this.movieScreeningID = movieScreeningID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaID = cinemaID;
        this.movieClassType = movieType;
        this.movieID = movieID;
        isExpired = false;

        // for now it's 8 by 15
        char[][] seatLayout = new char[8][15];
                //CineplexController.getInstance().getCinema(cinemaID).getSeatLayout();
        int x = seatLayout[0].length;
        int y = seatLayout.length;
        seats = new Seat[y][x];
        for (int i = 0; i < y; i++){
            for (int j = 0; j < x; j++) {
                if (j != 7) {
                    seats[i][j] = new Seat(j * i + (j + 1));
                }
            }
        }
    }

    public MovieScreening(int movieScreeningID, Calendar startTime, Calendar endTime, MovieClassType movieType,
                          int cinemaID, int movieID, Seat[][] seats){
        this.movieScreeningID = movieScreeningID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaID = cinemaID;
        this.movieClassType = movieType;
        this.movieID = movieID;
        isExpired = false;
        this.seats = seats;
    }

    public MovieClassType getMovieClassType() {
        return movieClassType;
    }

    public void setMovieClassType(MovieClassType movieClassType) {
        this.movieClassType = movieClassType;
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

    public void printMovieScreeningSeatsInfo(){
        for (int i = 0; i < seats.length; i++){
            System.out.print(i+1 + " ");
            for (int j = 0; j < seats[i].length; j++)
                if (j == 7) {
                    System.out.print(" ");
                }
                else if (!seats[i][j].getIsBooked())
                    System.out.print("O");
                else
                    System.out.print("X");
            System.out.println();
        }
    }

    public void printMovieScreeningInfo(){
        System.out.println("MovieScreeningID: " + movieScreeningID);
        System.out.println("MovieID: " + movieID);
        System.out.println("CinemaID: " + cinemaID);
        System.out.println("Movie Class Type: " + movieClassType);

    }

    public void getStartDateTime(){
        Calendar cal = startTime;
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);      // 0 to 11
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        System.out.printf("%4d/%02d/%02d %02d:%02d\n",
        year, month+1, day, hour, minute);
    }
}
