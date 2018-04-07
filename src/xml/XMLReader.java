package xml;

import data.GameData;

/**
 * @author Brendan Cheng 3/29/2018
 * 
 * Interface for classes that read XML files. Methods are designed to parse XML files of different formats. Files are formatted differently depending on data that
 * they store (i.e. a saved game state file is formatted differently than a game engine specifications file).
 *
 */

public interface XMLReader {

	/**
	 * Main functional unit of an XMLReader object. Uses XStream to parse a file and return a GameData object.
	 * 
	 * @param filename name of file to be read
	 * @return AuthoringModel based on specified file
	 */
	public GameData createModel(String filename);
	
	/**
	 * Reads a node in the XML file and accesses its data. Could be implemented in many ways.
	 * 
	 * @param elemName Tagname of node to be read
	 * @return Object that is created based on data in Node
	 */
	public Object getObjectData(String elemName);
	
}
