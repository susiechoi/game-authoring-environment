/**
 * 
 * @author Susie Choi 
 * View component of the Authoring environment's MVC 
 * Takes care of the population of fields/areas in the Authoring environment, and receives user customization 
 * 		(e.g. when users click options to customize Tower/Enemy/Path)
 * 
 */

package doc.usecases; 

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

class AuthoringViewExample {

	private FileIOExample myFileIO; 
	private BooleanProperty myUserActed; 
	private String myAffectedObject; 
	private String myChangedObjectField;
	private String myUserSelection; 

	protected AuthoringViewExample(FileIOExample fileIO) {
		myFileIO = fileIO; 
		myUserActed = new SimpleBooleanProperty(false); 
	}

	/**
	 * 
	 * Populates the options in a new dropdown menu 
	 * @param dropdownPurpose - the purpose associated with a dropdown
	 * @param options - options to be included in the dropdown menu 
	 * 
	 */
	private void populateOptions(String dropdownPurpose, List<String> options) {
		ObservableList<String> optionsAsObservableList = FXCollections.observableArrayList();
		for (String option : options) {
			optionsAsObservableList.add(option);
		}
		DropdownMenu menuToPopulate = new DropdownMenu(dropdownPurpose, new ComboBox<String>()); 
		menuToPopulate.setItems(optionsAsObservableList);
		menuToPopulate.selectionReceived().addListener((arg0, arg1, arg2)->{
			String selection = menuToPopulate.getSelection((int) arg2);
			myUserActed.set(!myUserActed.get());
			myChangedObjectField = menuToPopulate.getPurpose();
			myUserSelection = selection;
		});
	}
	
	/**
	 * Indicates whether user has selected something from a dropdown menu
	 * @return true/false that changes upon every user selection
	 */
	protected BooleanProperty myUserActed() {
		return myUserActed; 
	}

	/**
	 * Indicates what object the user wants to change  (e.g. Tower1) 
	 * @return String name of the object the user wants to change
	 */
	protected String getMyAffectedObject() {
		return myAffectedObject; 
	}
	
	/**
	 * Indicates what field of the object that the user wants to change
	 * @return String name of the field in the object that the user wants to change
	 */
	protected String getChangedField() {
		return myChangedObjectField;
	}
	
	/**
	 * Indicates what the user wants to change the field of the object ot 
	 * @return String change-to value of the field 
	 */
	protected String getUserSelection() {
		return myUserSelection; 
	}

}