package controller;

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
 * A controller to perform storing and retrieval of model to/from database.
 * @author Wong Jing Lun
 * @version 1.0
 * @since 2017-11-06
 */
public abstract class DatabaseController {
	/**
	 * Delimiter to seperate model in the database
	 */
	protected static final String DELIMITER = ";";
	
	/**
	 * Root Directory of the Database
	 */
	protected static final String BASEDIR = "database/";
	
	/**
	 * Buffer in database for retrieval of model
	 */
	private static List<String> data;

	public DatabaseController(){
		data = new ArrayList<>();
	}

	/**
	 * Method to allow specialized controllers to handle the structure
	 * of the model file during model retrieval
	 */
	protected abstract void readDB();
	
	/**
	 * Method to allow specialized controllers to handle the structure of 
	 * model file during model storing
	 */
	protected abstract void writeDB();

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
	
	public File[] getListofFiles(String folderName){
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		List<File> relevantFiles = new ArrayList<>();
		for (File f : listOfFiles)
			if (f.isFile() && f.getName().charAt(0) != '.')
				relevantFiles.add(f);
		File [] outputFiles = new File[relevantFiles.size()];
		return relevantFiles.toArray(outputFiles);
	}

	public File[] getListofFiles(String folderName, String fileName){
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		List<File> relevantFiles = new ArrayList<>();
		for (File f : listOfFiles)
			if (f.isFile() && f.getName().charAt(0) != '.' && f.getName().equals(fileName))
				relevantFiles.add(f);
		File [] outputFiles = new File[relevantFiles.size()];
		return relevantFiles.toArray(outputFiles);
	}
	
	/**
	 * Store a specified model into database
	 * @param fileName	Name of the model file to store model
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
	 * Retrieve a specified model from database
	 * @param fileName		Name of the model file
	 * @return				Retrieved model in the file
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
