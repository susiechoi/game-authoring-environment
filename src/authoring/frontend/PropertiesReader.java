package authoring.frontend;

import java.io.File;
import java.io.FileInputStream;
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
class PropertiesReader {
	
	protected String findVal(String filepath, String target) throws MissingPropertiesException {
		Properties properties = loadProperties(filepath);
		Map<String, String> readInProperties = read(properties);
		if (readInProperties.containsKey(target)) {
			return readInProperties.get(target);
		}
		return ""; 
	}
	
	protected List<String> allKeys(String filepath) throws MissingPropertiesException {
		Properties properties = loadProperties(filepath);
		Map<String, String> readInProperties = read(properties);
		ArrayList<String> allKeys = new ArrayList<String>(); 
		for (String key : readInProperties.keySet()) {
			allKeys.add(key);
		}
		return allKeys; 
	}
	
	protected List<Image> allValsAsImages(String filepath, double imageLength, double imageHeight) throws MissingPropertiesException {
		List<Image> imageList = new ArrayList<Image>();
		Properties properties = loadProperties(filepath);
		for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String val = properties.getProperty(key);
			imageList.add(new Image(val, imageLength, imageHeight, true, false));
		}
		return imageList; 
	}
	
	private Properties loadProperties(String filepath) throws MissingPropertiesException {
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

	private Map<String, String> read(Properties properties) {
		Map<String, String> readInProperties = new HashMap<String, String>(); 
		for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements(); ) {
			String key = (String)e.nextElement();
			String val = properties.getProperty(key);
			readInProperties.put(key, val);
		}
		return readInProperties; 
	}

}