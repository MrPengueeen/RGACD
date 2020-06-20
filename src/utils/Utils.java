package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utils {
	
	
	
	public static String getMainDir() {
		
		
		
		String mainDir = "";
		try {
			String configDirectory = System.getProperty("user.home") + "/rgacd/config.property";
			File configFile = new File(configDirectory);
			FileInputStream fileInput = new FileInputStream(configFile);
			Properties property = new Properties();
			property.load(fileInput);
			mainDir = property.getProperty("Directory");
			fileInput.close();
			System.out.println(mainDir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mainDir;
	}

}
