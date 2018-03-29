package xml;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Brendan Cheng 3/29/2018
 * 
 * Interface for classes that write XML files. Methods are designed to set up XML files, write data in the XML files, and save the file. Can be extended as
 * abstract class to include write() method for saving an authored file or GameState of a current Play.
 *
 */

public interface XMLWriter {
	
	/**
	 * Creates new instance of a document to be written to
	 * 
	 * @return document to be written to
	 */
	public Document initializeDoc();
	
	/**
	 * Transforms a document object that has been written into a Document object into an XML file with the specified filepath and name
	 * 
	 * @param doc   Document being saved
	 * @param file  File object containing filepath to which doc will be written and saved
	 */
	public void saveXMLFile(Document doc, File file);
	
	/**
	 * Used to add String data to nodes in an XML file. The expected output should be as follows:
	 * <elemName> data </elemName>
	 * 
	 * @param doc: Document to be added
	 * @param elemName: Name of the node/element
	 * @param data: The data to be added
	 * @return A node to be added to the xml file
	 */
	public void addData(Document doc, String elemName, Object data);
	
	/**
	 * Creates a new node, adds it to file by appending it to a parent node, then returns that element to be used by other 
	 * 
	 * @param parent Parent node to be appended to
	 * @param name	 Node name/type
	 * @return		 Node that has been appended to parent
	 */
	public Element appendElement(Element parent, String name, Document file);
	
}
