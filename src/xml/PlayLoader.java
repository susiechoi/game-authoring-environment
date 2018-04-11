//package xml;
//
//import java.io.File;
//
//import com.thoughtworks.xstream.XStream;
//
//import engine.PlayState;
//
//public class PlayLoader implements XMLReader {
//
//    private XStream parser;
//    
//    public PlayLoader() {
//	parser = new XStream();
//    }
//    
//    @Override
//    public PlayState createModel(String filename) {
//	    File f = new File("SavedPlays/" + filename + ".xml");
//	    PlayState g = (PlayState) parser.fromXML(f);
//	    return g;
//    }
//    
//}
