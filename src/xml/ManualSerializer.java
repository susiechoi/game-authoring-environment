package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import authoring.AuthoringModel;
import authoring.frontend.exceptions.ObjectNotFoundException;
import data.GameData;
import engine.level.Level;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;

/**
 * ']]]]]]]n']jm
 * 
 * @author Brendan Cheng
 *
 */

public class ManualSerializer implements XMLWriter {

    private Document d;
    private File f;
    private XStream parser;
    
    public ManualSerializer() {
	try {
		d = XMLDocumentBuilder.initializeDoc();
	} catch (ParserConfigurationException p) {
		System.out.println("Bad configuration"); // update exception
	}
	parser = new XStream(new StaxDriver());
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
	List<Level> levels = new ArrayList<>();
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
    
    /**
     * Creates the XML data for a serialized level
     * @param l	Level to be serialized
     * @return	String representation of serialized level
     */
    private String serializeLevel(Level l) {
	String enemyData = serializeEnemies(l.getAllEnemies(), l);
	String towerData = serializeTowers(l.getAllTowers(), l);
	return null;
    }
    
    /**
     * Generates XML data for the enemies in a level
     * @param enemies	List of String names of enemy types
     * @param level	Level object of interest
     * @return	String representation of serialized enemies
     */
    private String serializeEnemies(List<String> enemies, Level level) {
	String s = "";
	for (String enemy:enemies) {
	    Enemy e = level.getEnemy(enemy);
	    s = s + parser.toXML(e) + "\n";
	}
	return s;
    }
    
    /**
     * Generates XML data for the towers in a level
     * @param towers	List of String names of tower types
     * @param level	Level object of interest
     * @return	String representation of serialized towers
     */
    private String serializeTowers(List<String> towers, Level level) {
	String s = "";
	for (String tower:towers) {
	    Tower t = level.getTower(tower);
	    s = s + parser.toXML(t) + "\n";
	}
	return s;
    }

}
