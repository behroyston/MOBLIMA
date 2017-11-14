package model;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class SystemSettings implements Serializable{

	//Attributes
	private static SystemSettings instance = null;
	private double base_price;
	private double threeDExtra;
	private double platinumExtra;
	private double seniorDiscount;
	private double childDiscount;
	private double weekend_HolidayExtra;
	
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
		SystemSettings sS = SystemSettings.getInstance();
		writeSettings("SystemSettings.dat", sS);
		return;
	}
	
	//access private 3d extra
	public double getThreeDExtra() {
		return this.threeDExtra;
	}
	public void setThreeDExtra(double threeDExtra) {
		this.threeDExtra = threeDExtra;
		SystemSettings sS = SystemSettings.getInstance();
		writeSettings("SystemSettings.dat", sS);
		return;
	}
	
	//access private plat xtra
	public double getPlatinumExtra() {
		return this.platinumExtra;
	}
	//mutate private plat xtra
	public void setPlatinumExtra(double platinumExtra) {
		this.platinumExtra = platinumExtra;
		SystemSettings sS = SystemSettings.getInstance();
		writeSettings("SystemSettings.dat", sS);
		return;
	}
	
	//access private snr disc
	public double getSeniorDiscount() {
		return this.seniorDiscount;
	}
	//mutate private snr disc
	public void setSeniorDiscount(double seniorDiscount) {
		this.seniorDiscount = seniorDiscount;
		SystemSettings sS = SystemSettings.getInstance();
		writeSettings("SystemSettings.dat", sS);
		return;
	}
	
	//access private child disc
	public double getChildDiscount() {
		return this.childDiscount;
	}
	//mutate private child disc
	public void setChildDiscount(double childDiscount) {
		this.childDiscount = childDiscount;
		SystemSettings sS = SystemSettings.getInstance();
		writeSettings("SystemSettings.dat", sS);
		return;
	}
	
	//access private wkend/hols xtra
	public double getWeekend_HolidayExtra() {
		return this.weekend_HolidayExtra;
	}
	//mutate private wkend/hols xtra
	public void setWeekend_HolidayExtra(double weekend_HolidayExtra) {
		this.weekend_HolidayExtra = weekend_HolidayExtra;
		SystemSettings sS = SystemSettings.getInstance();
		writeSettings("SystemSettings.dat", sS);
		return;
	}
	
	//static 'instance' method
	public static SystemSettings getInstance(){
		if(instance == null) {
			instance = new SystemSettings();
			readSettings("SystemSettings.dat");
		}
		return instance;
	}
	
	
	public static SystemSettings readSettings(String filename) {
		SystemSettings sS = SystemSettings.getInstance();
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			sS = (SystemSettings) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		// print out the size
		//System.out.println(" Details Size: " + sSDetails.size());
		//System.out.println();
		return sS;
	}

	public static void writeSettings(String filename, SystemSettings sS) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(sS);
			out.close();
		//	System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	//for testing purposes
	/*public static void main(String[] args) {
		
		SystemSettings sS = SystemSettings.getInstance();
		
		try	{
				// read from serialized file
				sS = (SystemSettings)readSettings("SystemSettings.dat");
				System.out.println("Base Price: " + sS.getBase_price() );
				

				// write to serialized file - update/insert/delete
				writeSettings("SystemSettings.dat", sS);

		}  catch ( Exception e ) {
					System.out.println( "Exception >> " + e.getMessage() );
		}
	}*/

}
