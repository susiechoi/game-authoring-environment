package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import jdk.internal.jline.internal.Log;

///Put this at the top of the file:
import java.io.File;
import java.net.MalformedURLException;

public class TestWriter {
    
    private File file;
    private Document d;
    private XStream parser;
    
    public TestWriter() {
	parser = new XStream(new StaxDriver());
	try {
	    d = XMLDocumentBuilder.initializeDoc();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    Log.debug(e);
	}
    }
//public void write(List<String> l, String filename) {
//    
//BufferedWriter out;
//try {
//    out = new BufferedWriter(new FileWriter("SavedModels/" + filename + ".xml"));
//    out.write(parser.toXML(l));
//    out.close();
//} catch (IOException e1) {
//    // TODO Auto-generated catch block
//    e1.printStackTrace();
//}
//}
	public void write(List<String> l, String filepath) {
	    XMLDocumentBuilder.stringToXML(parser.toXML(l), "SavedModels/" + filepath + ".xml");
//		file = new File("SavedModels/" + filepath + ".xml");
//		try {
//		    parser.toXML(l, new FileWriter(file));
//		} catch (IOException e) {
//		    // TODO Auto-generated catch block
//		    Log.debug(e);
//		}
	}
//		// Write data using XStream
////		Element root = d.createElement("GameRules");
//		
//		//System.out.println(parser.toXML(l));
////		root.appendChild(XMLDocumentBuilder.addData(d, "AuthoringModel", parser.toXML(l)));
//		try {
//			XMLDocumentBuilder.saveXMLFile(d, file);
//		} catch (TransformerFactoryConfigurationError | TransformerException e) {
//			// TODO replace with error pop up?
//			System.out.println("Error configuring XML file");
//			Log.debug(e);
//		}
//	}
	
	public List<String> read(String filepath) {
	    File f = new File("SavedModels/" + filepath + ".xml");
	    return (List<String>) parser.fromXML(f);
	}
    
    public static void main(String[] args) {
	TestWriter x = new TestWriter();
	ArrayList<String> l = new ArrayList<>();
	XStream parser = new XStream(new StaxDriver());
	l.add("A");
	l.add("B");
	x.write(l, "TesterFile");
	ArrayList<String> y = (ArrayList<String>) x.read("TesterFile");
    }

}
