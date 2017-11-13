import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

public class SystemSettings{

	//Attributes
	private static SystemSettings instance = null;
	private double base_price=10;
	private double threeDExtra=1.5;
	private double platinumExtra=1.5;
	private double seniorDiscount=0.5;
	private double childDiscount=0.5;
	private double weekend_HolidayExtra=1.5;
	
	//Class constructor
	private SystemSettings() {
	}
	
	//access private base price
	public double getBase_price() {
		return this.base_price;
	}
	//mutate private base price
	public void setBase_price(double base_price) {
		this.base_price = base_price;
		return;
	}
	
	//access private 3d extra
	public double getThreeDExtra() {
		return this.threeDExtra;
	}
	public void setThreeDExtra(double threeDExtra) {
		this.threeDExtra = threeDExtra;
		return;
	}
	
	//access private plat xtra
	public double getPlatinumExtra() {
		return this.platinumExtra;
	}
	//mutate private plat xtra
	public void setPlatinumExtra(double platinumExtra) {
		this.platinumExtra = platinumExtra;
		return;
	}
	
	//access private snr disc
	public double getSeniorDiscount() {
		return this.seniorDiscount;
	}
	//mutate private snr disc
	public void setSeniorDiscount(double seniorDiscount) {
		this.seniorDiscount = seniorDiscount;
		return;
	}
	
	//access private child disc
	public double getChildDiscount() {
		return this.childDiscount;
	}
	//mutate private child disc
	public void setChildDiscount(double childDiscount) {
		this.childDiscount = childDiscount;
		return;
	}
	
	//access private wkend/hols xtra
	public double getWeekend_HolidayExtra() {
		return this.weekend_HolidayExtra;
	}
	//mutate private wkend/hols xtra
	public void setWeekend_HolidayExtra(double weekend_HolidayExtra) {
		this.weekend_HolidayExtra = weekend_HolidayExtra;
		return;
	}
	
	//static 'instance' method
	public static SystemSettings getInstance(){
		if(instance == null) {
			instance = new SystemSettings();
		}
		return instance;
	}
	
	//Serializeable
	//for testing purposes
	//WIP!!!
	public static List readSettings(String filename) {
		List sSDetails = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			sSDetails = (ArrayList) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		// print out the size
		//System.out.println(" Details Size: " + sSDetails.size());
		//System.out.println();
		return sSDetails;
	}

	public static void writeSettings(String filename, List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		//	System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		List list;
		try	{
				// read from serialized file
				list = (ArrayList)SystemSettings.readSettings("SystemSettings.dat");
				for (int i = 0 ; i < list.size() ; i++) {
					SystemSettings sS = (SystemSettings)list.get(i);
					System.out.println("Base Price: " + sS.getBase_price() );
				}

				// write to serialized file - update/insert/delete
				SystemSettings.writeSettings("SystemSettings.dat", list);

		}  catch ( Exception e ) {
					System.out.println( "Exception >> " + e.getMessage() );
		}
	}

}
