package xml;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import data.GameData;

public class AuthoringModelWriter implements XMLWriter {

	/**
	 * Implementation of XMLWriter that writes an XML file specifying the data written while authoring a game, including tower types, tower functionality, 
	 * enemy types, and path. Utilizes XStream methods to write data about each object in AuthoringModel that is passed to write() method.
	 * 
	 * @author Brendan Cheng 3/31/2018
	 */
	
	private Document file;
	
	public AuthoringModelWriter() {
		try {
			file = XMLDocumentBuilder.initializeDoc();
		} catch (ParserConfigurationException p) {
			System.out.println("Bad configuration");
		}
	}
	
	@Override
	public void write(GameData g, String filepath) {
		
		// Save data
		File path = new File(filepath + ".xml");
		XMLDocumentBuilder.saveXMLFile(file, path);
	}

	@Override
	public void change(GameData g) {
		// TODO Auto-generated method stub
		
	}

}
