package usecases; 

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

class AuthoringViewExample {

	private FileIO myFileIO; 

	protected AuthoringViewExample(FileIO fileIO) {
		myFileIO = fileIO; 
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
			// must pass menuToPopulate.getPurpose and selection to AuthoringController to influence Model (temp view) 
		});
	}


}