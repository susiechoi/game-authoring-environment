package xml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Static class that creates XMLWriters/XMLReaders to handle data parsing. Holds a main method for testing purposes.
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
	 * @param filename
	 * path to file that is being written to
	 *
	 * @return
	 * Instance of that implementation of XMLWriter
	 */
	public static XMLWriter generateWriter(String writerType, String filename) {
		try {
			Class writer = Class.forName(writerType);
			Constructor c = writer.getConstructor();
			try {
			    return (XMLWriter) c.newInstance(filename);
			} catch (InstantiationException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (IllegalAccessException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (IllegalArgumentException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (InvocationTargetException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Uses reflection to create an XMLReader of the specified type
	 *
	 * @param readerType
	 * String representation of class name of desired reader type
	 * 
	 * @param filename
	 * Path to file tat is being read
	 *
	 * @return
	 * Instance of that implementation of XMLReader
	 */
	public static XMLReader generateReader(String readerType, String filename) {
		try {
			Class writer = Class.forName(readerType);
			Constructor c = writer.getConstructor();
			try {
			    return (XMLReader) c.newInstance(filename);
			} catch (InstantiationException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (IllegalAccessException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (IllegalArgumentException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (InvocationTargetException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
	    
	}

}
