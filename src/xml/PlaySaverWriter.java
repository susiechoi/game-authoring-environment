package xml;


import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import data.GameData;

/**
 * Implementation of XMLWriter that writes an XML file specifying the current state of a Play. Saved data includes tower info (placement, upgrades), projectile
 * info (velocity, direction of motion, and location), enemy info (locations and health of each enemy), Path info, and player info (i.e. health remaining,
 * resources, level number).
 * 
 * @author Brendan Cheng 3/31/2018
 */

public class PlaySaverWriter implements XMLWriter {

	private Document d;
	private XStream parser;
	private Element game;
	private File file;
	
	public PlaySaverWriter() {
		try {
			d = XMLDocumentBuilder.initializeDoc();
		} catch (ParserConfigurationException p) {
			System.out.println("Bad configuration");
		}
		parser = new XStream(new StaxDriver());
		game = d.createElement("game");
		d.appendChild(game);
	}

	@Override
	public void write(GameData g, String filepath) {
		// check type
		if (!g.getClass().getSimpleName().equals("PlayState")) {
			throw new BadGameDataException("Incorrect GameData: Must use PlayState object to store correct data");
		}
		//
		file = new File("SavedGames/" + filepath + ".xml");
		// Write data using XStream
		Element root = d.createElement("Game Rules");
		root.appendChild(XMLDocumentBuilder.addData(d, "PlayState", parser.toXML(g)));
		try {
			XMLDocumentBuilder.saveXMLFile(d, file);
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			// TODO replace with error pop up?
			System.out.println("Error configuring XML file");
		}
	}
}

