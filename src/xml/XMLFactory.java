package xml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import authoring.AuthoredGame;
import authoring.frontend.exceptions.MissingPropertiesException;
import jdk.internal.jline.internal.Log;

/**
 * Static class that creates XMLWriters/XMLReaders to handle data parsing. Holds a main method for testing purposes.
 *
 * @author Brendan Cheng
 *
 */

public class XMLFactory {

    public XMLFactory() {
	
    }
    
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
		try {
			Class<?> writer = Class.forName("xml." + writerType);
			try {
			    return (XMLWriter) writer.newInstance();
			} catch (InstantiationException e) {
			    // TODO Auto-generated catch block
			    Log.debug(e);
			} catch (IllegalAccessException e) {
			    // TODO Auto-generated catch block
			    Log.debug(e);
			} catch (IllegalArgumentException e) {
			    // TODO Auto-generated catch block
			    Log.debug(e);
			}
		} catch (ClassNotFoundException | SecurityException e) {
			// TODO Auto-generated catch block
			Log.debug(e);
		}
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
		try {
			Class<?> writer = Class.forName("xml." + readerType);
			Constructor<?> c = writer.getConstructor();
			try {
			    return (XMLReader) c.newInstance();
			} catch (InstantiationException e) {
			    // TODO Auto-generated catch block
			    Log.debug(e);
			} catch (IllegalAccessException e) {
			    // TODO Auto-generated catch block
			    Log.debug(e);
			} catch (IllegalArgumentException e) {
			    // TODO Auto-generated catch block
			    Log.debug(e);
			} catch (InvocationTargetException e) {
			    // TODO Auto-generated catch block
			    Log.debug(e);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			Log.debug(e);
		}
		return null;
	}
	
	public static void main(String[] args) throws MissingPropertiesException {
	    XMLFactory f = new XMLFactory();
	    AuthoringModelWriter p = (AuthoringModelWriter) XMLFactory.generateWriter("AuthoringModelWriter");
	    //AuthoredGame a = new AuthoredGame();
	    //p.write(a, "test1");
	    AuthoringModelReader r = (AuthoringModelReader) XMLFactory.generateReader("AuthoringModelReader");
	    AuthoredGame b = r.createModel("test1");
//	    if (b!= null)
//		System.out.println("not Null");
	}

}

