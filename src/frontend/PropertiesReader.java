package frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import authoring.frontend.exceptions.*;
import javafx.scene.image.Image;

/**
 * @author susiechoi
 * Used for obtaining information about the keys and values in a Properties file. 
 * Useful for obtaining ColorPalette or saved UserCommand/Variable information, which is manipulated throughout the course of the program. 
 * Use by creating a PropertiesReader object with the filepath as a String argument. From there, can get a map of 
 * keys->vals in the PropertiesFile, search for a given key/val in the file, etc. 
 */
public class PropertiesReader {
	
	public String findKey(String filepath, String targetVal) throws MissingPropertiesException {
		Properties properties = loadProperties(filepath);
		Map<String, String> readInProperties = read(properties);
		for (String key : readInProperties.keySet()) {
			if (readInProperties.get(key).equals(targetVal)) {
				return key;
			}
		}
		return ""; 
	}
	
	public String findVal(String filepath, String target) throws MissingPropertiesException {
		Properties properties = loadProperties(filepath);
		Map<String, String> readInProperties = read(properties);
		if (readInProperties.containsKey(target)) {
			return readInProperties.get(target);
		}
		return ""; 
	}
	
	public List<String> allKeys(String filepath) throws MissingPropertiesException {
		Properties properties = loadProperties(filepath);
		Map<String, String> readInProperties = read(properties);
		ArrayList<String> allKeys = new ArrayList<String>(); 
		for (String key : readInProperties.keySet()) {
			allKeys.add(key);
		}
		return allKeys; 
	}
	
	public Map<String, Image> keyToImageMap(String filepath, double imageLength, double imageHeight) throws MissingPropertiesException {
		Map<String, Image> imageMap = new HashMap<String, Image>(); 
		Properties properties = loadProperties(filepath);
		for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String val = properties.getProperty(key);
			try {
				imageMap.put(key, new Image(new FileInputStream(val), imageLength, imageHeight, false, false));
			
			} catch (FileNotFoundException e1) {
				throw new MissingPropertiesException(val);
			}
		}
		return imageMap; 
	}
	
	protected Properties loadProperties(String filepath) throws MissingPropertiesException {
		Properties properties = new Properties(); 
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(filepath));
			properties.load(in);
		} catch (Exception e) {
			throw new MissingPropertiesException(filepath);
		}
		return properties; 
	}

	protected Map<String, String> read(Properties properties) {
		Map<String, String> readInProperties = new HashMap<String, String>(); 
		for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String val = properties.getProperty(key);
			readInProperties.put(key, val);
		}
		return readInProperties; 
	}
	
	public List<String> findVals(String filepath) throws MissingPropertiesException {
		Properties properties = loadProperties(filepath);
		List<String> vals = new ArrayList<String>(); 
		for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String val = properties.getProperty(key);
			vals.add(val);
			System.out.println(val);
		}
		return vals; 
	}

}