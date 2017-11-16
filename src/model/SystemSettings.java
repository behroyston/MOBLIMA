package model;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Represents a SystemSetting object which stores the settings that can contribute to the ticket price.
 * @author Amos
 * @version 1.0
 * @since 2017-11-06
 */
public class SystemSettings {

	/**
	 * Instance of the SystemSettings.
	 */
	private static SystemSettings instance = null;

	/**
	 * Base Price of the movie ticket.
	 */
	private double basePrice;

	/**
	 * Surcharge of the 3D Movie.
	 */
	private double threeDExtra;

	/**
	 * Surcharge of the Platinum Cinema Class.
	 */
	private double platinumExtra;

	/**
	 * Discount for the Seniors.
	 */
	private double seniorDiscount;

	/**
	 * Discount for children.
	 */
	private double childDiscount;

	/**
	 * Surcharge of the Weekends or holiday.
	 */
	private double weekend_HolidayExtra;

	/**
	 * Surcharge of the Gold Cinema Class.
	 */
	private double goldExtra;

	/**
	 * Holidays set by the Staff.
	 */
	private ArrayList<Calendar> holidays;

	/**
	 * Creates a SystemSettings object. It will attempt to read from the database.
	 * If there is no settings found in database, it will load the default settings.
	 */
	private SystemSettings() {
		holidays = new ArrayList<>();
		try{
			readSettings("database/SystemSettings.dat");
		}
		catch(IOException io){
			basePrice=10;
			threeDExtra=5;
			platinumExtra=7;
			seniorDiscount=3;
			childDiscount=2;
			weekend_HolidayExtra=2;
			goldExtra=5;
			writeSettings("database/SystemSettings.dat");
		}
	}

	/**
	 * Get the base price of the movie ticket.
	 * @return	Base price of the movie ticket.
	 */
	public double getBasePrice() {
		return this.basePrice;
	}


	/**
	 * Set the base price of the movie ticket.
	 * @param basePrice	Base price of the movie ticket.
	 */
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	/**
	 * Get the 3D surcharge of the movie ticket.
	 * @return	3D surcharge of the movie ticket.
	 */
	public double getThreeDExtra() {
		return this.threeDExtra;
	}

	/**
	 * Set the 3D surcharge of the movie ticket.
	 * @param setThreeDExtra	New 3D surcharge of the movie ticket.
	 */
	public void setThreeDExtra(double threeDExtra) {
		this.threeDExtra = threeDExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	/**
	 * Get the Platinum surcharge of cinema class.
	 * @return	Platinum surcharge of the cinema class.
	 */
	public double getPlatinumExtra() {
		return this.platinumExtra;
	}

	/**
	 * Set the Platinum surcharge of cinema class.
	 * @param platinumExtra	Platinum surcharge of the cinema class.
	 */
	public void setPlatinumExtra(double platinumExtra) {
		this.platinumExtra = platinumExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	/**
	 * Get the discount for seniors.
	 * @return	Discount for seniors.
	 */
	public double getSeniorDiscount() {
		return this.seniorDiscount;
	}

	/**
	 * Set the discount for seniors.
	 * @param seniorDiscount	New discount for seniors.
	 */
	public void setSeniorDiscount(double seniorDiscount) {
		this.seniorDiscount = seniorDiscount;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	/**
	 * Get the discount for children.
	 * @return	Discount for children.
	 */
	public double getChildDiscount() {
		return this.childDiscount;
	}
	/**
	 * Set the discount for children.
	 * @param childDiscount	New discount for children.
	 */
	public void setChildDiscount(double childDiscount) {
		this.childDiscount = childDiscount;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	/**
	 * Get the surcharge for weekend or holiday.
	 * @return	Surcharge for weekend or holiday.
	 */
	public double getWeekend_HolidayExtra() {
		return this.weekend_HolidayExtra;
	}
	/**
	 * Set the surcharge for weekend or holiday.
	 * @param weekend_HolidayExtra	New surcharge for weekend or holiday.
	 */
	public void setWeekend_HolidayExtra(double weekend_HolidayExtra) {
		this.weekend_HolidayExtra = weekend_HolidayExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	/**
	 * Get the Gold class surcharge.
	 * @return	Surcharge for gold class.
	 */
	public double getGoldExtra() {
		return this.goldExtra;
	}

	/**
	 * Set the gold class surcharge.
	 * @param goldExtra	New surcharge for gold class.
	 */
	public void setGoldExtra(double goldExtra) {
		this.goldExtra = goldExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	/**
	 * Get the list of holidays.
	 * @return	List of holidays.
	 */
	public ArrayList<Calendar> getHolidays() {
		return this.holidays;
	}

	/**
	 * Add a holiday. It will also check if it the holiday date is already added in to the system.
	 * @param holiday	Holiday date.
	 * @return			true if successfully added. false otherwise.
	 */
	public boolean addHoliday(Calendar holiday) {
		for (Calendar cal : holidays)
			if (holiday.compareTo(cal) == 0)
				return false;
		holidays.add(holiday);
		writeSettings("database/SystemSettings.dat");
		return true;
	}

	/**
	 * Gets the channel reference of the SystemSettings.
	 * Creates the channel reference if it do not exists.
	 * @return Instance of the SystemSettings.
	 */
	public static SystemSettings getInstance(){
		if(instance == null) {
			instance = new SystemSettings();
		}
		return instance;
	}

	/**
	 * Read the settings to file. It follows the order: Base price, platinum surcharge, senior discount,
	 * children discount, weekend/holiday surcharge, gold class surchage, list of holidays.
	 * @param fileName		File name to read the settings.
	 * @throws IOException	File not found.
	 */
	public void readSettings(String fileName) throws IOException{
		FileReader frStream = new FileReader(fileName);
		BufferedReader brStream = new BufferedReader(frStream);
		String text = brStream.readLine();
		StringTokenizer aStr = new StringTokenizer(text, ";");
		basePrice = Double.parseDouble(aStr.nextToken());
		platinumExtra = Double.parseDouble(aStr.nextToken());
		seniorDiscount = Double.parseDouble(aStr.nextToken());
		childDiscount = Double.parseDouble(aStr.nextToken());
		weekend_HolidayExtra = Double.parseDouble(aStr.nextToken());
		goldExtra = Double.parseDouble(aStr.nextToken());
		holidays.clear();
		while (aStr.hasMoreTokens()){
			Calendar cal = Calendar.getInstance();
			Calendar holiday = (Calendar)cal.clone();
			holiday.setTimeInMillis(Long.parseLong(aStr.nextToken()));
			holidays.add(holiday);
		}
		brStream.close();
	}

	/**
	 * Write the settings to file. It follows the order: Base price, platinum surcharge, senior discount,
	 * children discount, weekend/holiday surcharge, gold class surchage, list of holidays.
	 * These are appended in sequence on 1 line.
	 * @param fileName		File name to write the settings.
	 */
	public void writeSettings(String fileName) {
		try{
			FileWriter fwStream = new FileWriter(fileName);
			BufferedWriter bwStream = new BufferedWriter(fwStream);
			PrintWriter pwStream = new PrintWriter(bwStream);
			StringBuilder str = new StringBuilder();
			str.setLength(0); // Reset Buffer
			str.append(basePrice);		
			str.append(';');
			str.append(platinumExtra);
			str.append(';');
			str.append(seniorDiscount);
			str.append(';');
			str.append(childDiscount);
			str.append(';');
			str.append(weekend_HolidayExtra);
			str.append(';');
			str.append(goldExtra);
			str.append(';');
			for (Calendar cal : holidays){
				str.append(cal.getTimeInMillis());
				str.append(';');
			}
			pwStream.println(str.toString());
			pwStream.close();
		}
		catch (IOException io){
			System.out.println("Error. Failed to save System Settings!");
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
