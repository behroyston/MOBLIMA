import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseController {
	protected static final String DELIMTER = ";";
	protected static final String BASEDIR = "database/";
	private static List<String> data;

	public DatabaseController(){
		data = new ArrayList<>();
	}

	public abstract void readDB();
	public abstract void writeDB();

	public boolean checkDirectoryExist(String filepath) {
		File f = new File(filepath);
		if (!f.exists()) {
			try {
				f.mkdirs();
			} catch (Exception ex) {
				System.out.println("Error! Unable to create new directory!");
				return false;
			}
		}
		return f.exists();
	}
	
	public void saveData(String fileName, List<String> data) {
		try {
			FileWriter fwStream = new FileWriter(fileName);
			BufferedWriter bwStream = new BufferedWriter(fwStream);
			PrintWriter pwStream = new PrintWriter(bwStream);
			for (String aData : data) {
				pwStream.println(aData);
			}
			pwStream.close();
		} 
		catch (Exception io) {
			System.out.println("Error! Cannot write to file.");
		}
	}

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
