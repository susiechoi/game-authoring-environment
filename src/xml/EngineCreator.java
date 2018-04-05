package xml;

import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;

import authoring.AuthoringModel;

/**
 * Reads an 
 * 
 * @author Brendan Cheng
 *
 */

public class EngineCreator implements XMLReader {

	private Document dom;
	private XStream parser;
	
	public EngineCreator(String filename) {
		parser = new XStream();
		dom = readInFile(filename);
	}
	
	/**
	 * 
	 * @return
	 */
	public AuthoringModel createEngine() {
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
