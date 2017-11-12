import java.util.ArrayList;

public class Movie{
    private int movieId;
    private String movieName;
    private String status;
    private String synopsis;
    private String director;
    private String cast;
    private double avg_rating;
    private ArrayList<String> reviews;
    private ArrayList<Double> ratingList;
    private boolean isShowing;
    private double ticketSales;
    private int duration;


    public Movie(int movieId, String movieName, String synosis, String director, String cast){
        this.movieId = movieId;
        this.movieName = movieName;
        this.synopsis = synosis;
        this.director = director;
        this.cast = cast;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getStatus() {
        return status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDirector() {
        return director;
    }

    public String getCast() {
        return cast;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public ArrayList<Double> getRatingList() {
        return ratingList;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public double getTicketSales() {
        return ticketSales;
    }

    public int getDuration() {
        return duration;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return average rating
     */
    public double getRating(){
        return avg_rating;
    }

    /**
     * calculate and update average rating
     */
    private void setAvgRating(){
        double sum = 0;
        for (double rating : ratingList)
            sum += rating;
        avg_rating = sum/ratingList.size();
    }

    public void addReview(String review){
        reviews.add(review);
    }

    public void addRating(double rating){
        ratingList.add(rating);
    }

    public void addTicketSales(double sales){
        ticketSales += sales;
    }


}