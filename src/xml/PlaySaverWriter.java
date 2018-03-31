package xml;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;

import data.GameData;

/**
 * Implementation of XMLWriter that writes an XML file specifying the current state of a Play. Saved data includes tower info (placement, upgrades), projectile
 * info (velocity, direction of motion, and location), enemy info (locations and health of each enemy), Path info, and player info (i.e. health remaining,
 * resources, level number).
 * 
 * @author Brendan Cheng 3/31/2018
 */

public class PlaySaverWriter implements XMLWriter {

	Document d;
	
	public PlaySaverWriter() {
		try {
			d = XMLDocumentBuilder.initializeDoc();
		} catch (ParserConfigurationException p) {
			System.out.println("Bad configuration");
		}
	}
	
	@Override
	public void write(GameData g, String filepath) {
		// check type
		if (!g.getClass().getSimpleName().equals("GameState")) {
			throw new BadGameDataException("Incorrect GameData: Must use GameState object to store correct data");
		}
		//
		
		// Save data
		File path = new File(filepath + ".xml");
		try {
			XMLDocumentBuilder.saveXMLFile(d, path);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			// TODO replace with error pop up?
			System.out.println("Error configuring XML file");
		}
	}

	@Override
	public void change(GameData g) {
		// TODO Auto-generated method stub
		
	}

}
