import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents a Cineplex which contains at least 3 Cinemas.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public class Cineplex{
	/**
	 * Name of this cineplex.
	 */
	private String name;
	
	/**
	 * The location of this cineplex.
	 */
	private String location;
	
	/**
	 * The list of cinemas in this cineplex.
	 */
	private ArrayList<Cinema> cinemaList;
	
	/**
	 * Creates a cineplex given its name, location and the cinemas in it.
	 * @param name			Name of this cineplex.
	 * @param location		Location of this cineplex.
	 * @param cinemaList	List of the cinemas in this cineplex
	 */
	public Cineplex(String name, String location, ArrayList<Cinema> cinemaList){
		this.name = name;
		this.location = location;
		this.cinemaList = cinemaList;
	}
	
	
	/**
	 * Gets the name of this cineplex.
	 * @return Name of this cineplex.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Change the name of this cineplex.
	 * @param name New name of this cineplex.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Gets the location of this cineplex.
	 * @return Location of this cineplex.
	 */
	public String getLocation(){
		return location;
	}
	
	/**
	 * Change the location of this cineplex.
	 * @param location New location of this cineplex.
	 */
	public void setLocation(String location){
		this.location = location;
	}
	
	/**
	 * Gets the list of cinemas in this cineplex.
	 * @return List of cinemas in this cineplex.
	 */
	public ArrayList<Cinema> getCinemaList(){
		return cinemaList;
	}
}
