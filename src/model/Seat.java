package model;
/**
 * Represents a Seat and its attributes.
 * @author Royston
 * @version 1.0
 * @since 2017-11-06
 */
public class Seat {
	/*
	 * The unique identifier of the seat
	 */
	private int seatID;

	/**
	 * Availability of the seat for booking.
	 */
	private boolean isBooked;

	/**
	 * Creates a Seat object with its unique identifier of the seat.
	 * @param seatID	SeatID of the seat.
	 */
	public Seat(int seatID) {
		this.seatID = seatID;
	}

	/**
	 * Get the availability of the seat for booking.
	 * @return	Availability of the seat for boking.
	 */
	public boolean getIsBooked(){
		return isBooked;
	}
	
	/**
	 * Set the availability of the seat for booking.
	 * @param isBooked	Availability of the seat booking.
	 */
	public void setIsBooked(boolean isBooked){
		this.isBooked = isBooked;
	}

	/**
	 * Gets the unique identifier of the seat.
	 * @return	SeatID of the seat.
	 */
	public int getSeatID(){
		return seatID;
	}

}
