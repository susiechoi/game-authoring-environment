/**
 * 
 * Object that wraps a ComboBox and associates it with a purpose
 * Allows for the population of many ComboBoxes with different purposes 
 * Facilitates the process of connecting user selections with the fields those selections relate to 
 * @author Susie Choi 
 * 
 */

package usecases;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class DropdownMenu {

	private String myPurpose; // can also represent this with polymorphism
	private ComboBox<String> myComboBox;
	
	public DropdownMenu(String purpose, ComboBox<String> comboBox) {
		myPurpose = purpose; 
		myComboBox = comboBox; 
	}
	
	public String getPurpose() {
		return myPurpose;
	}

	public void setItems(ObservableList<String> items) {
		myComboBox.setItems(items);
	}
	
	public ReadOnlyIntegerProperty selectionReceived() {
		return myComboBox.getSelectionModel().selectedIndexProperty(); 
	}
	
	public String getSelection(int index) {
		return myComboBox.getItems().get(index);
	}
	
	public ObservableList<String> getItems() {
		return myComboBox.getItems();
	}
	
}
