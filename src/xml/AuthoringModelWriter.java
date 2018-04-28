package xml;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import data.GameData;

public class AuthoringModelWriter implements XMLWriter {

	/**
	 * Implementation of XMLWriter that writes an XML file specifying the data written while authoring a game, including tower types, tower functionality, 
	 * enemy types, and path. Utilizes XStream methods to write data about each object in AuthoringModel that is passed to write() method.
	 * 
	 * @author Brendan Cheng 3/31/2018
	 */

	private Document d;
	private File file;
	private XStream parser;

	/**
	 * Initializes file that will be written
	 */
	public AuthoringModelWriter() {
		try {
			d = XMLDocumentBuilder.initializeDoc();
		} catch (ParserConfigurationException p) {
			System.out.println("Bad configuration"); // update exception
		}
		parser = new XStream(new StaxDriver());
		parser.autodetectAnnotations(true);
//		parser.registerConverter(new ObservableListConverter(parser.getMapper()));
	}

    /**
     * Implementation of write method that saves data for authoring to SavedModels folder
     */
    @Override
    public void write(GameData g, String filepath) throws BadGameDataException {
	// check type
	if (!g.getClass().getSimpleName().equals("AuthoredGame")) {
	    throw new BadGameDataException("Incorrect GameData: Must use AuthoringModel object to store correct data");
	}
//	file = new File("SavedModels/" + filepath + ".xml");
//	// Write data using XStream
//	Element root = d.createElement("GameRules");
//	root.appendChild(XMLDocumentBuilder.addData(d, "AuthoringModel", parser.toXML(g)));
//	// Save data
//	try {
//	    XMLDocumentBuilder.saveXMLFile(d, file);
//	} catch (TransformerFactoryConfigurationError | TransformerException e) {
//	    // TODO replace with error pop up?
//	    System.out.println("Error configuring XML file");
//	}
//    }
	
	XMLDocumentBuilder.stringToXML(parser.toXML(g), "SavedModels/" + filepath + ".xml");

    }

    
}

