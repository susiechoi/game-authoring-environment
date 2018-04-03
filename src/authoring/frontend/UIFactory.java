/**
 * Class for generating common front end UI elmenets, e.g. comboboxes, sliders
 * To ensure consistency of front end design
 * @author susiechoi 
 */

package authoring.frontend;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UIFactory {

	/**
	 * Makes Text object for displaying titles of screens to the user
	 * @param titleText is String text displayed as title
	 * @return Text object with this String
	 */
	public Text makeScreenTitleText(String titleText) {
		Text screenTitle = new Text(titleText);
		screenTitle.setId("screenTitle");
		return screenTitle;
	}

	public Button makeImageButton(String id, Image buttonImage) {
		Button newButton = new Button();
		ImageView buttonImageView = new ImageView(buttonImage);
		newButton.setGraphic(buttonImageView);
		newButton.setId(id);
		return newButton; 
	}

	public Button makeTextButton(String id, String buttonText) {
		Button newButton = new Button(buttonText);
		newButton.setId(id);
		return newButton; 
	}

	public ComboBox<String> makeTextDropdownButtonEnable(String id, List<String> dropdownOptions, EventHandler<ActionEvent> chooseAction,
			EventHandler<ActionEvent> noChoiceAction, String prompt){
		ComboBox<String> dropdown = makeTextDropdown(id, dropdownOptions);
		dropdown.setOnAction( e ->{
			if(!dropdown.getValue().equals(prompt)){
				chooseAction.handle(e);
			}
			else {
				noChoiceAction.handle(e);
			}
		});
		return dropdown;
	}

	public ComboBox<String> makeTextDropdown(String id, List<String> dropdownOptions) {
		ObservableList<String> options = FXCollections.observableArrayList(dropdownOptions);
		ComboBox<String> newDropdown = new ComboBox<String>(options);
		newDropdown.getSelectionModel().selectFirst(); 
		newDropdown.setId(id);
		return newDropdown; 
	}

	/**
	 * Creates HBox with a text prompt to the left of a user input text field
	 * @param promptString - text prompt 
	 * @return HBox 
	 */
	protected HBox setupPromptAndTextField(String id, String promptString) {
		HBox hb = new HBox(); 
		Text prompt = new Text(promptString); 
		TextField tf = new TextField(); 
		hb.getChildren().add(prompt);
		hb.getChildren().add(tf); 
		hb.setId(id);
		return hb; 
	}

	/**
	 * Creates HBox with a text prompt to the left, combobox/dropdown to the right
	 * @param promptString - text prompt 
	 * @param dropdownOptions - String options to populate dropdown
	 * @return HBox 
	 */
	protected HBox setupPromptAndDropdown(String id, String promptString, List<String> dropdownOptions) {
		HBox hb = new HBox(); 
		Text prompt = new Text(promptString); 
		ComboBox<String> dropdown = makeTextDropdown("", dropdownOptions);
		dropdown.getSelectionModel().selectFirst();
		hb.getChildren().add(prompt);
		hb.getChildren().add(dropdown); 
		hb.setId(id);
		return hb; 
	}

	protected HBox setupPromptAndSlider(String id, String promptString, int sliderMax) {
		HBox hb = new HBox();
		Text prompt = new Text(promptString);
		Slider slider = new Slider(0, sliderMax, (0 + sliderMax) / 2);
		Text sliderValue = new Text(String.format("%03d", (int)(double)slider.getValue()));
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				sliderValue.setText(String.format("%03d", (int)(double)new_val));
			}
		});
		hb.getChildren().add(prompt);
		hb.getChildren().add(slider);
		hb.getChildren().add(sliderValue);
		hb.setId(id);
		return hb; 
	}

	public ScrollPane makeTextScrollPane(String id, List<String> options) {
		ScrollPane newPane = new ScrollPane();
		String allOptions = "";
		for (String s : options) {
			allOptions += s+"\n";
		}
		newPane.setContent(new Text(allOptions));
		newPane.setId(id);
		return newPane; 
	}

	public ScrollPane makeImageScrollPane(String id, File[] files) {
		ScrollPane newPane = new ScrollPane();
		VBox imageConsolidator = new VBox();
		for (File f : files) {
			ImageView oneImageView = new ImageView();
			oneImageView.setImage(new Image(f.toURI().toString()));
			imageConsolidator.getChildren().add(oneImageView);
		}
		newPane.setContent(imageConsolidator);
		newPane.setId(id);
		return newPane; 
	}

	public HBox setupImageSelector(PropertiesReader propertiesReader, String description, String propertiesFilepath, double imageSize) throws MissingPropertiesException {
		Map<String, Image> enemyImageOptions;
		enemyImageOptions = propertiesReader.keyToImageMap(propertiesFilepath, imageSize, imageSize);

		ArrayList<String> imageNames = new ArrayList<String>(enemyImageOptions.keySet());
		imageNames.add("Load New Image");

		final ArrayList<Image> images = new ArrayList<Image>(enemyImageOptions.values()); 
		ImageView imageDisplay = new ImageView(); 
		imageDisplay.setImage(images.get(0));
		ComboBox<String> imageOptionsDropdown = makeTextDropdown("", imageNames);
		imageOptionsDropdown.getSelectionModel().selectFirst();

		HBox imageSelect = new HBox();
		Text prompt = new Text(description+"Image: ");

		final FileChooser fileChooser = new FileChooser();
		//		Button loadNewImageButton = makeTextButton("loadButton", "Load New Image");
		//		loadNewImageButton.setOnMouseClicked((event)-> {
		//			File file = fileChooser.showOpenDialog(new Stage());
		//			imageDisplay.setImage(new Image(file.toURI().toString(), imageSize, imageSize, false, false));
		//		});
		imageSelect.getChildren().add(prompt);
		imageSelect.getChildren().add(imageOptionsDropdown);
		//		imageSelect.getChildren().add(loadNewImageButton);

		imageOptionsDropdown.getSelectionModel().selectedIndexProperty().addListener(( arg0, arg1,  arg2) ->{
			if ((int) arg2 == imageNames.size()-1) {
				File file = fileChooser.showOpenDialog(new Stage());
				imageDisplay.setImage(new Image(file.toURI().toString(), imageSize, imageSize, false, false));
			}
			else {
				imageDisplay.setImage(images.get((int) arg2));
			}
		});
		imageSelect.getChildren().add(imageDisplay);

		return imageSelect; 
	}

	public void applyTextFieldFocusAction(Scene screen, TextField textField) {
		screen.setOnMousePressed(event -> {
			if (!textField.equals(event.getSource())) {
				textField.getParent().requestFocus();
			}
		});
	}

}