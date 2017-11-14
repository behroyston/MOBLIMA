package model;

import controller.MovieGoerController;

import java.util.ArrayList;
import java.util.Scanner;

public class MovieGoer extends Person {

	//Attributes
	private int cusID;
	private int age;
	private boolean isStudent;
	
	Scanner sc = new Scanner(System.in);
	
	//Class constructor
	public MovieGoer(String password, String name, int mobileNumber, String email, int cusID, int age, boolean isStudent) {
		super(password, name, mobileNumber, email);//abstract
		this.cusID = cusID;
		this.age = age;
		this.isStudent = isStudent;
	}
	
	//access private ID
	public int getCusID() {
		return this.cusID;
	}
	//mutate private ID
	public void setCusID(int cusID) {
		this.cusID = cusID;
		return;
	}
	
	//login(abstract)
	public boolean login(String email, String password) {
		//same logic as staff
		ArrayList<MovieGoer> movieGoerList = MovieGoerController.getInstance().getMovieGoerList();
		
		for (int i=0; i < movieGoerList.size(); i++) {
			if (movieGoerList.get(i).getEmail() == email) {
				if (movieGoerList.get(i).getPassword() == password) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	//access private age
	public int getAge() {
		return this.age;
	}
	//mutate private age
	public void setAge(int age) {
		this.age = age;
		return;
	}
	
}
