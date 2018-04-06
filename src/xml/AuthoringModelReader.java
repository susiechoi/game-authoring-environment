package xml;

import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;

import authoring.AuthoringModel;

/**
 * Reads an XML document using XStream to reconstruct an AuthoringModel object to either be further edited by authoring environment or to create a game engine
 * in the game play environment
 * 
 * @author Brendan Cheng
 *
 */

public class AuthoringModelReader implements XMLReader {

	private Document dom;
	private XStream parser;
	
	public AuthoringModelReader(String filename) {
		parser = new XStream();
		dom = readInFile(filename);
	}
	
	/**
	 * 
	 * @return
	 */
	public AuthoringModel createModel() {
		return null;
	}
	
	@Override
	public Document readInFile(String filename) {
		return null;
	}

	@Override
	public Object getObjectData(String elemName) {
		// TODO Auto-generated method stub
		return null;
	}

}
