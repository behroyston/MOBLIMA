import ui.MovieGoerUI;
import ui.StaffUI;

import java.util.Scanner;

public class Main {
	/**
	 * Main method to execute program
	 * @param args
	 */
    public static void main(String[] args) {
        MovieGoerUI mMovieGoerUI = MovieGoerUI.getInstance();
        StaffUI mStaffUI = StaffUI.getInstance();
        
        Scanner sc = new Scanner(System.in);
        int choice;
        do{
        	System.out.println("Are you a staff or a customer?");
        	System.out.println("1) Staff");
        	System.out.println("2) Customer");
        	System.out.println("3) Quit");
        	System.out.print("Enter choice: ");
        	choice = sc.nextInt();
        	switch (choice){
        	case 1:
        		mStaffUI.display();
        		break;
        	case 2:
        		mMovieGoerUI.display();
        		break;
        	case 3:
        		break;
        	default:
        		System.out.println("Invalid choice! Please re-enter...");
        	}
        }while(choice != 3);
        
		System.out.println("Thank you for using MOBLIMA!");
    }
}
