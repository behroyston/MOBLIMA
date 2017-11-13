import java.util.ArrayList;

public class MovieGoerController {
	//Attributes
	private String DIR = "moviegoer/";
	
	private ArrayList<MovieGoer> movieGoerList;
	
	private static MovieGoerController instance = null;
	
	//Class constructor
	private MovieGoerController() {
		
	}
	
	//database methods
	protected void readDB() {
			//to be added
		}
	protected void writeDB() {
			//to be added
		}
	
	//static 'instance' method	
	public static MovieGoerController getInstance(){
		if(instance == null) {
			instance = new MovieGoerController();
		}
		return instance;
	}
	
	//get private arraylist for login
	public ArrayList<MovieGoer> getMovieGoerList(){
        return movieGoerList;
	}
	
}
