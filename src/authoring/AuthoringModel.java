package authoring;

import java.util.Map;
import java.util.TreeMap;

import engine.enemies.Enemy;
import engine.path.Path;
import engine.towers.Tower;

/**
 * 
 * @author Susie Choi 
 * @author Ben Hodgson 4/2/18
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller through applyChange method regarding what/how to change an object
 * 
 */
public class AuthoringModel {
    
    public AuthoringModel() {
	// map that maps tower String ID's to tower objects for easy accessing later
	Map<String, Tower> towerMap = new TreeMap<String, Tower>();
	// map that maps enemy String ID's to enemy objects for easy accessing later
	Map<String, Enemy> enemyMap = new TreeMap<String, Enemy>();
    }
    
    
    /**
     * Sets the path to be used in the level
     * 
     * @param path: The user generated path object to be used in the game
     */
    public void setPath(Path path) {
	
    }
    
    /**
     * Causes change to appropriate field of appropriate component of Model 
     * @param affectedObject - the name of the affected object
     * @param fieldToChange - field of the object that is to be changed
     * @param changeToValue - value to which the fieldToChange should be changed
     */
    private void applyChange(String affectOject, String fieldToChange, String changeToValue) {

    }

}
