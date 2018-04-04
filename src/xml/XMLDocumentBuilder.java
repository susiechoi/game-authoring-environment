package xml;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Brendan Cheng
 * 
 *	Class containing static methods that are common to writing all XML files.
 */

public class XMLDocumentBuilder {

	
	/**
	 * Creates new instance of a document to be written to
	 * 
	 * @return document to be written to
	 * @throws ParserConfigurationException 
	 */
	public static Document initializeDoc() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.newDocument();
	}
	
	/**
	 * Transforms a document object that has been written into a Document object into an XML file with the specified filepath and name
	 * 
	 * @param doc   Document being saved
	 * @param file  File object containing filepath to which doc will be written and saved
	 * @throws TransformerFactoryConfigurationError 
	 * @throws TransformerException 
	 */
	public static void saveXMLFile(Document doc, File file) throws TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult newFile = new StreamResult(file);
		transformer.transform(source, newFile);
	}
	
	/**
	 * Used to add String data to nodes in an XML file. The expected output should be as follows:
	 * <elemName> data </elemName>
	 * 
	 * @param doc: Document to be added
	 * @param elemName: Name of the node/element
	 * @param data: The data to be added
	 * @return A node to be added to the xml file
	 */
	public static Node addData(Document doc, String elemName, Object data) {
		Element e = doc.createElement(elemName);
		//e.appendChild(doc.createTextNode(data));
		return e;
	}
	
	/**
	 * Creates a new node, adds it to file by appending it to a parent node, then returns that element to be used by other 
	 * 
	 * @param parent Parent node to be appended to
	 * @param name	 Node name/type
	 * @return		 Node that has been appended to parent
	 */
	public static Element appendElement(Element parent, String name, Document file) {
		return null;
	}
	
	
}
