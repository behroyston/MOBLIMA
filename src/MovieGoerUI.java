import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MovieGoerUI {

    private static MovieGoerUI Instance = null;

    private Seat[][] seats;

    public static MovieGoerUI getInstance() {
        if (Instance == null) {
            Instance = new MovieGoerUI();
        }
        return Instance;
    }

    // traverses through MovieController.getCineplex(position).getCinema(position).getMovieScreening(position).getSeatsForThisMovie
    public void showSeatsAvailability(){
        if (seats == null)
            setDummySeats();
        int x;
        int y;
        String order[] = {"A","B","C","D","E","F","G","H"};
        System.out.print("   ");

        // Construct 1 to 15 for horizontal label;
        for (int i = 1; i < 16; i++) {
            if (i == 8) {
                System.out.print("   " + i);
            }
            else if (i < 10) {
                System.out.print(" " + i);
            }
            else System.out.print(i);
        }

        System.out.println();

        for (y = 7; y > -1; y--) {
            // Construct the label A - H for the Vertical Label
            System.out.print(order[y]);
            System.out.print("   ");

            for (x = 0; x < 7; x++) {
                showIsBooked(x,y);
            }
            System.out.print("  ");
            for (x = 7 ; x < 15; x++) {
                showIsBooked(x,y);
            }

            System.out.print("  ");
            System.out.println(order[y]);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Book Seat\n2. Go Back to Main Menu");
        int choice = sc.nextInt();
        switch (choice) {
            case (1): bookTickets();
                break;
            case (2): //TODO: Add in the show main menu function
                break;
        }
    }

    private void bookTickets(){
        Scanner sc = new Scanner(System.in);
        List<String> stringList = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        String alphabet;
        int number;
        System.out.println("\"Please enter the Seat Alphabet you wished to book!");
        while (!sc.hasNext("[ABCDEFGHabcdefgh]")) {
            System.out.println("That's not an Alphabet");
            sc.next(); // this is important!
        }
        alphabet = sc.next();

        do {
            System.out.println("Please enter a valid number!");
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number!");
                sc.next(); // this is important!
            }
            number = sc.nextInt();
        } while (number <= 0);

        int verticalIndex = stringList.indexOf(alphabet);

        Seat selectedSeat = seats[number-1][verticalIndex];
        if (!selectedSeat.getIsBooked()) {
            //TODO AddBooking through the BookingManager
            //BookingController.getInstance().addBooking(//user,);
            seats[number - 1][verticalIndex].setIsBooked(true);
        }
        else
            System.out.println("The seat is booked! Please select another seat.");
        showSeatsAvailability();
    }

    private void showIsBooked(int horizontalLabel, int verticalLabel){

        if (seats[horizontalLabel][verticalLabel].getIsBooked())
            System.out.print("X ");
        else
            System.out.print("O ");
    }

    private void setDummySeats(){
        seats = new Seat[15][8];
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 15; x++){
                Seat newSeat = new Seat((x+1)*(y+1));
                seats[x][y] = newSeat;
            }
        }
    }

}
