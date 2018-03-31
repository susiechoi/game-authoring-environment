package authoring.frontend;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PartsFactory {

	public static Button makeButton(String buttonText, double length, double height) {
		Button newButton = new Button(buttonText);
		newButton.setPrefWidth(length);
		newButton.setPrefHeight(height);
		return newButton; 
	}
	
	public static ComboBox<String> makeTextDropdown(List<String> dropdownOptions, double length, double height) {
		 ObservableList<String> options = FXCollections.observableArrayList(dropdownOptions);
		 ComboBox<String> newDropdown = new ComboBox<String>(options);
		 newDropdown.setPrefWidth(length);
		 newDropdown.setPrefHeight(height);
		 newDropdown.getSelectionModel().selectFirst(); 
		 return newDropdown; 
	}
	
	public static ComboBox<Image> makeImageDropdown(List<Image> dropdownImages, double length, double height) {
		 ObservableList<Image> imageOptions = FXCollections.observableArrayList(dropdownImages);
		 ComboBox<Image> newDropdown = new ComboBox<Image>(imageOptions);
		 newDropdown.setPrefWidth(length);
		 newDropdown.setPrefHeight(height);
		 newDropdown.getSelectionModel().selectFirst(); 
		 return newDropdown; 
	}
	
	public static ScrollPane makeTextScrollPane(List<String> options, double length, double height) {
		ScrollPane newPane = new ScrollPane();
		String allOptions = "";
		for (String s : options) {
			allOptions += s+"\n";
		}
		newPane.setContent(new Text(allOptions));
		newPane.setPrefWidth(length);
		newPane.setPrefHeight(height);
		return newPane; 
	}
	
	public static ScrollPane makeImageScrollPane(File[] files, double length, double height) {
		ScrollPane newPane = new ScrollPane();
		VBox imageConsolidator = new VBox();
		for (File f : files) {
			ImageView oneImageView = new ImageView();
			oneImageView.setImage(new Image(f.toURI().toString()));
			imageConsolidator.getChildren().add(oneImageView);
		}
		newPane.setContent(imageConsolidator);
		newPane.setPrefWidth(length);
		newPane.setPrefHeight(height);
		return newPane; 
	}
	
}
