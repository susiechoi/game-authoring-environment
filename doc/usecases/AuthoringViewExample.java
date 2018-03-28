/**
 * 
 * View component of the Authoring environment's MVC 
 * Takes care of the population of fields/areas in the Authoring environment, and receives user customization 
 * 		(e.g. when users click options to customize Tower/Enemy/Path)
 * @author Susie Choi 
 * 
 */

package usecases; 

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

class AuthoringViewExample {

	private FileIOExample myFileIO; 
	private BooleanProperty myUserActed; 
	private String myChangedField;
	private String myUserSelection; 

	protected AuthoringViewExample(FileIOExample fileIO) {
		myFileIO = fileIO; 
		myUserActed = new SimpleBooleanProperty(false); 
	}

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
			myChangedField = menuToPopulate.getPurpose();
			myUserSelection = selection;
		});
	}
	
	protected BooleanProperty myUserActed() {
		return myUserActed; 
	}

	protected String getChangedField() {
		return myChangedField;
	}
	
	protected String getUserSelection() {
		return myUserSelection; 
	}

}