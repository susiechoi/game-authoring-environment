package xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

///Put this at the top of the file:
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;

import data.GameData;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TestWriter extends Application {
    
    private File file;
    private Document d;
    private XStream parser;
    
    public TestWriter() {
	parser = new XStream(new StaxDriver());
	try {
	    d = XMLDocumentBuilder.initializeDoc();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
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
//		    e.printStackTrace();
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
	System.out.println(parser.toXML(l));
	x.write(l, "TesterFile");
	ArrayList<String> y = (ArrayList<String>) x.read("TesterFile");
	System.out.println(y.toString());
	System.out.println("<?xml version=\"1.0\" ?>");
	//launch(args);
	
    }
    @Override
    public void start(Stage arg0) throws Exception {
	Stage s = new Stage();
	Group parent = new Group();
	Scene scene = new Scene(parent, 1000, 1000);
	s.setScene(scene);
	File f = new File("/images/flow.png");
	try {
	    HBox h = new HBox();
	    LocatedImage i = new LocatedImage(f.toURI().toURL().toString());
	    h.getChildren().add(i);
	    parent.getChildren().add(h);
	    String path = parser.toXML(i.getUrl());
	    s.show();
	    System.out.println(path);
	    LocatedImage img = new LocatedImage((String) parser.fromXML(path));
	    System.out.println(img.getUrl() + "\n");
	    System.out.println(parser.toXML(img));
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
