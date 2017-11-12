import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A controller to perform storing and retrieval of data to/from database.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public abstract class DatabaseController {
	/**
	 * Delimiter to seperate data in the database
	 */
	protected static final String DELIMITER = ";";
	
	/**
	 * Root Directory of the Database
	 */
	protected static final String BASEDIR = "database/";
	
	/**
	 * Buffer in database for retrieval of data
	 */
	private static List<String> data;

	public DatabaseController(){
		data = new ArrayList<>();
	}

	/**
	 * Method to allow specialized controllers to handle the structure
	 * of the data file during data retrieval
	 */
	public abstract void readDB();
	
	/**
	 * Method to allow specialized controllers to handle the structure of 
	 * data file during data storing 
	 */
	public abstract void writeDB();

	/**
	 * Check if the directory in database exists.
	 * Create the directory if it does not exists.
	 * @param dirPath	Directory path of the database
	 * @return			True if it the directory exists or created successfully. False otherwise.
	 */
	public boolean checkDirectoryExist(String dirPath) {
		File f = new File(dirPath);
		if (!f.exists()) 
			f.mkdirs();
		return f.exists();
	}
	
	/**
	 * Store a specified data into database
	 * @param fileName	Name of the data file to store data
	 * @param dat		Data to store into the database
	 */
	public void saveData(String fileName, List<String> dat) {
		try {
			FileWriter fwStream = new FileWriter(fileName);
			BufferedWriter bwStream = new BufferedWriter(fwStream);
			PrintWriter pwStream = new PrintWriter(bwStream);
			for (String aDat : dat) 
				pwStream.println(aDat);
			pwStream.close();
		} 
		catch (Exception io) {
			System.out.println("Error! Cannot write to file.");
		}
	}

	/**
	 * Retrieve a specified data from database
	 * @param fileName		Name of the data file
	 * @return				Retrieved data in the file
	 * @throws IOException	File do not exists
	 */
	public List<String> retrieveData(String fileName) throws IOException{
		data.clear();
		FileReader frStream = new FileReader(fileName);
		BufferedReader brStream = new BufferedReader(frStream);
		String text;
		while((text = brStream.readLine()) != null)
			data.add(text);
		brStream.close();
		return data;
	}
}
