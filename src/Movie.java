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


    public Movie(int movieId, String movieName, String synosis, String director, String cast){
        this.movieId = movieId;
        this.movieName = movieName;
        this.synopsis = synosis;
        this.director = director;
        this.cast = cast;
    }

    public int getId(){
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    /**
     * @return average rating
     */
    public double getRating(){
        return avg_rating;
    }

    //change parameter in class diagram
    /**
     * calculate and update average rating
     * @param ratingList
     */
    public void setRating(ArrayList<Double> ratingList){
        double sum = 0;
        for (double rating : ratingList)
            sum += rating;
        avg_rating = sum/ratingList.size();
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    //change the name for this method in class diagram
    /**
     * add a new review
     * @param reviews
     */
    public void addReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<Double> getRatingList() {
        return ratingList;
    }

    /**
     * add a new rating to radingList
     * @param rating
     */
    public void addRating(double rating) {
        ratingList.add(rating);
    }

    public double getTicketSales() {
        return ticketSales;
    }

    public void addTicketSales(double ticketSales){
        ticketSales++;
    }

    public boolean getIsShowing(boolean isShowing){
        return isShowing;
    }
}