package model;

import java.io.*;
import java.util.List;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Calendar;

public class SystemSettings implements Serializable{

	//Attributes
	private static SystemSettings instance = null;
	private double basePrice;
	private double threeDExtra;
	private double platinumExtra;
	private double seniorDiscount;
	private double childDiscount;
	private double weekend_HolidayExtra;
	private double goldExtra;
	private ArrayList<Calendar> holidays;

	//Class constructor
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

	//access private base price
	public double getBasePrice() {
		return this.basePrice;
	}
	//mutate private base price
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	//access private 3d extra
	public double getThreeDExtra() {
		return this.threeDExtra;
	}
	public void setThreeDExtra(double threeDExtra) {
		this.threeDExtra = threeDExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	//access private plat xtra
	public double getPlatinumExtra() {
		return this.platinumExtra;
	}
	//mutate private plat xtra
	public void setPlatinumExtra(double platinumExtra) {
		this.platinumExtra = platinumExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	//access private snr disc
	public double getSeniorDiscount() {
		return this.seniorDiscount;
	}
	//mutate private snr disc
	public void setSeniorDiscount(double seniorDiscount) {
		this.seniorDiscount = seniorDiscount;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	//access private child disc
	public double getChildDiscount() {
		return this.childDiscount;
	}
	//mutate private child disc
	public void setChildDiscount(double childDiscount) {
		this.childDiscount = childDiscount;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	//access private wkend/hols xtra
	public double getWeekend_HolidayExtra() {
		return this.weekend_HolidayExtra;
	}
	//mutate private wkend/hols xtra
	public void setWeekend_HolidayExtra(double weekend_HolidayExtra) {
		this.weekend_HolidayExtra = weekend_HolidayExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}

	//access private gold class extra
	public double getGoldExtra() {
		return this.goldExtra;
	}
	
	//mutate private gold class extra
	public void setGoldExtra(double goldExtra) {
		this.goldExtra = goldExtra;
		writeSettings("database/SystemSettings.dat");
		return;
	}
	
	public ArrayList<Calendar> getHolidays() {
		return this.holidays;
	}
	
	public boolean addHoliday(Calendar holiday) {
		for (Calendar cal : holidays)
			if (holiday.compareTo(cal) == 0)
				return false;
		holidays.add(holiday);
		writeSettings("database/SystemSettings.dat");
		return true;
	}

	//static 'instance' method
	public static SystemSettings getInstance(){
		if(instance == null) {
			instance = new SystemSettings();
		}
		return instance;
	}


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
