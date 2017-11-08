public class Seat {

    private int seatID;

    private boolean isBooked;

    public Seat(int seatID) {
        this.seatID = seatID;
    }

    public boolean getIsBooked(){
        return isBooked;
    }

    public void setIsBooked(boolean isBooked){
        this.isBooked = isBooked;
    }

    public int getSeatID(){
        return seatID;
    }

}
