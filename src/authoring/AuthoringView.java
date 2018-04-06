package authoring;

import java.io.File;
import java.util.List;

import javafx.scene.Scene;

/**
 * 
 * @author Ben Hodgson 3/27/18
 * @author Susie Choi 
 * Class that implements the Screen interface to display authoring specific screens 
 * in the user interface. Represents the View component of the authoring environment's MVC. 
 * 
 */

public class AuthoringView {
    
    
    	public Scene loadAuthoringView() {
    	    
    	    return null;
    	}

	/**
	 * 
	 * Populates the options in a new dropdown menu 
	 * @param dropdownPurpose - the purpose associated with a dropdown
	 * @param options - options to be included in the dropdown menu 
	 * 
	 */
	private void populateOptions(String dropdownPurpose, List<String> options) {
	}

	/**
	 * Takes an array of files and generates a list of possible options available to the user.
	 * Presents one option for each files.
	 * 
	 * @param files: the files representing different options available to the user
	 * @return List<String>: a list of String objects representing file names
	 */
	public List<String> getDisplayOptions(File[] files) {
		return null;
	}

	/**
	 * Indicates what object the user wants to change  (e.g. Tower1) 
	 * @return String name of the object the user wants to change
	 */
	protected String getMyAffectedObject() {
		return null; 
	}

	/**
	 * Indicates what field of the object that the user wants to change
	 * @return String name of the field in the object that the user wants to change
	 */
	protected String getChangedField() {
		return null;
	}

	/**
	 * Indicates what the user wants to change the field of the object ot 
	 * @return String change-to value of the field 
	 */
	protected String getUserSelection() {
		return null; 
	}

}
