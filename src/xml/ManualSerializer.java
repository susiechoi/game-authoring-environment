package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import authoring.AuthoringModel;
import authoring.frontend.exceptions.ObjectNotFoundException;
import data.GameData;
import engine.level.Level;

public class ManualSerializer implements XMLWriter {

    private Document d;
    private File f;
    
    public ManualSerializer() {
	try {
		d = XMLDocumentBuilder.initializeDoc();
	} catch (ParserConfigurationException p) {
		System.out.println("Bad configuration"); // update exception
	}
    }
    
    @Override
    public void write(GameData g, String filepath) {
	if (!g.getClass().getSimpleName().equals("AuthoringModel")) {
	    throw new BadGameDataException("Incorrect GameData: Must use AuthoringModel object to store correct data");
	}
	AuthoringModel am = (AuthoringModel) g;
	ArrayList<Level> levels = (ArrayList<Level>) getLevels(am);
	
    }
    
    /**
     * Access levels in an AuthoringModel and create List containing those levels
     * 
     * @param am	AuthoringModel to access data from
     * @return		List of Level objects
     */
    private List<Level> getLevels(AuthoringModel am) {
	ArrayList<Level> levels = new ArrayList<>();
	for (int i = 0; i < am.getLevels().size(); i++) {
	    try {
		Level l = am.levelCheck(i);
		levels.add(l);
	    } catch (ObjectNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return levels;
    }
    
    private String serializeLevel(Level l) {
	ArrayList<String> enemies = (ArrayList<String>) l.getAllEnemies();
	ArrayList<String> towers = (ArrayList<String>) l.getAllTowers();
	return null;
    }
    
    private String serializeEnemies(List<String> enemies) {
	
    }
    
    private String serializeTowers(List<String> towers) {
	
    }

}
