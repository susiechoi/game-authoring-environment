package xml;

/**
 * Static class that creates XMLWriters/XMLReaders to handle data parsing.
 * 
 * @author Brendan Cheng
 *
 */

public class XMLFactory {

	/**
	 * Uses reflection to create an XMLWriter of the specified type
	 * 
	 * @param writerType
	 * String representation of class name of desired writer type
	 * 
	 * @return
	 * Instance of that implementation of XMLWriter
	 */
	public static XMLWriter generateWriter(String writerType) {
		return null;
	}
	
	/**
	 * Uses reflection to create an XMLReader of the specified type
	 * 
	 * @param readerType
	 * String representation of class name of desired reader type
	 * 
	 * @return
	 * Instance of that implementation of XMLReader
	 */
	public static XMLReader generateReader(String readerType) {
		return null;
	}
	
}
