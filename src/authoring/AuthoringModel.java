/**
 * 
 * @author Susie Choi 
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller through applyChange method regarding what/how to change an object
 * 
 */

package authoring;

import data.GameData;
import engine.path.Path;

public class AuthoringModel implements GameData {

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
