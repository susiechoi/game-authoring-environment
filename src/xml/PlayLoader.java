package xml;

import java.io.File;

import com.thoughtworks.xstream.XStream;

import authoring.AuthoringModel;

public class PlayLoader implements XMLReader {

    private XStream parser;
    
    public PlayLoader() {
	parser = new XStream();
    }
    
    @Override
    public AuthoringModel createModel(String filename) {
	    File f = new File("SavedModels/" + filename);
	    AuthoringModel g = (AuthoringModel) parser.fromXML(f);
	    return g;
    }
    
}
