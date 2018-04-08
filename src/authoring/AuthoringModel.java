

package authoring;

import java.util.List;

import data.GameData;
import engine.level.Level;
import engine.path.Path;

/**
 * 
 * @author Susie Choi 
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller through applyChange method regarding what/how to change an object
 * 
 */
public class AuthoringModel implements GameData {
    
    public AuthoringModel() {
	
    }
    
    /**
     * Returns a list of levels to be used by the Game Engine in a play
     * 
     * @return List(Level): list of levels for the current play
     */
    public List<Level> getLevels() {
	//TODO should return list of level objects to game engine
	return null;
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
