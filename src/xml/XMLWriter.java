package xml;

import data.GameData;

/**
 * @author Brendan Cheng 3/29/2018
 * 
 * Interface for classes that write XML files. Methods are designed to set up XML files, write data in the XML files, and save the file. Can be extended as
 * abstract class to include write() method for saving an authored file or GameState of a current Play.
 *
 */

public interface XMLWriter {
	
	/**
	 * Writes the inputted data to an XML file using XStream and XMLDocumentBuilder methods. Saves the file at a specified filepath.
	 * 
	 * @param g		A GameData object (in this case, AuthorModel or GameState) whose data will be read and written to an XML file
	 * @param filepath		filepath to which file will be saved. Don't include .xml
	 */
	public void write(GameData g, String filepath);
	
	/**
	 * Changes the current GameData object that this is operating on.
	 * 
	 * @param g 	GameData object to change to
	 */
	public void change(GameData g);

}
