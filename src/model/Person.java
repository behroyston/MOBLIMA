//import java.util.Scanner;

public abstract class Person {

	//Attributes
	private String name;
	private int mobileNumber;
	private String password;
	private String email;
	
	//Class constructor
	public Person(String password, String name, int mobileNumber, String email) {
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.email = email;
	}
	
	//login(abstract)
	abstract public boolean login();
	
	//access private password
	public String getPassword() {
		return this.password;
	}
	//mutate private password
	public void setPassword(String password) {
		this.password = password;
		return;
	}
	
	//access private name
	public String getName() {
		return this.name;
	}
	
	//mutate private name
		public void setName(String name) {
			this.name = name;
			return;
		}
	
	//access private number
	public int getMobileNumber() {
		return this.mobileNumber;
	}
	
	//mutate private number
		public void setMobleNumber(int mobileNumber) {
			this.mobileNumber = mobileNumber;
			return;
		}
	
	//access private email
	public String getEmail() {
		return this.email;
	}
	
	//mutate private email
		public void setEmail(String email) {
			this.email = email;
			return;
		}
	
	//testrun
//	public static void main(String args[]) {
//		Person Person1 = new Person("CZ2002x", "kiminonawa", 99999999, "email@email.com");
//		System.out.print(Person1.getPassword());
//		System.out.print(Person1.getName());
//		System.out.print(Person1.getEmail());
//		System.out.print(Person1.getMobileNumber());
//		System.out.print("Set new Password: ");
//		Scanner sc = new Scanner(System.in);
//		String newPw = sc.next();
//		Person1.setPassword(newPw);
//		System.out.print(Person1.getPassword());
//	}
}
