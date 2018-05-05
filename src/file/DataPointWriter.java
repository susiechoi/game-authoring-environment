/**
 * Class for writing out datapoints to a file at a specified path 
 * Useful for recording metrics such as currency, health, and score during gameplay for future review 
 * @author susiechoi
 */
package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataPointWriter {
	
	public static final String DEFAULT_DATE_FORMAT = "MM-dd-yyyy_hh-mm-ss"; 
	public static final String DEFAULT_DATAPOINTS_FILEPATH = "graphing/";
	public static final String DEFAULT_FILENAME_SEPARATOR = "_";
	public static final int DEFAULT_SECOND_DIVISOR = 1000; 
	
	private PrintWriter myWriter; 

	/**
	 * Prepares a PrintWriter to write to a file at the specified path 
	 * with a calendar and time stamp. Creates the parent folders if they do not exist. 
	 * @param gameName - name of the game that the file will be stamped with
	 * @param subfolder - path of the file 
	 * @throws FileNotFoundException - the file failed to be created
	 */
	public DataPointWriter(String gameName, String subfolder) throws FileNotFoundException {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		String formattedDate = df.format(c.getTime());
		
		String scoreFileName = DEFAULT_DATAPOINTS_FILEPATH+subfolder+gameName+DEFAULT_FILENAME_SEPARATOR+formattedDate; 
		File scoreFile = new File(scoreFileName);
		scoreFile.getParentFile().mkdirs(); 
		myWriter = new PrintWriter(scoreFile);
	}
	
	/**
	 * Writes immediately to the file with a timestamp and the int datapoint passed in as argument
	 * @param y - int to record with timestamp in file 
	 */
	public void recordDataPoint(int y) {
		long recordedX = System.currentTimeMillis() / DEFAULT_SECOND_DIVISOR; 
		this.myWriter.write(Long.toString(recordedX)+" ");
		this.myWriter.write(Integer.toString(y)+"\n");
		this.myWriter.flush();
	}
	
}
