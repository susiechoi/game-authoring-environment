package authoring.frontend;

import java.util.ArrayList;

import java.util.Map;
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class AdjustTowerScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String PROJECTILE_IMAGES = "images/ProjectileImageNames.properties";
	public static final String ENGLISH_PROMPT_FILE = "prompts/EnglishPrompts.properties"; //TODO: shouldn't be hardcoded! need to get language to frontend
	public static final String ENGLISH_ERROR_FILE = "errors/EnglishErrors.properties";
	public static final int DEFAULT_TOWER_MAX_RANGE = 500; 
	public static final int DEFAULT_TOWER_MAX_PRICE = 500; 

	private PropertiesReader myPropertiesReader; 

	protected AdjustTowerScreen() {
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
		myPropertiesReader = new PropertiesReader();
	}

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		HBox towerNameSelect = new HBox();
		try {
		towerNameSelect = getUIFactory().setupPromptAndTextField("", myPropertiesReader.findVal(ENGLISH_PROMPT_FILE, "TowerName")); 
		}
		catch(MissingPropertiesException e){
			try {
			showError(myPropertiesReader.findVal(ENGLISH_ERROR_FILE, "NoFile"));
			}
			catch (MissingPropertiesException e2) {
				showError("Missing a properties file! Defaulting to English");
			}
			towerNameSelect = getUIFactory().setupPromptAndTextField("", "Tower Name: ");
		}
		ImageView towerImageDisplay = new ImageView(); 
		HBox towerImageSelect = new HBox();
		try {
			towerImageSelect = getUIFactory().setupImageSelector(myPropertiesReader, "Tower ", TOWER_IMAGES, towerImageDisplay, 50);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		} 
		ImageView projectileImageDisplay = new ImageView(); 
		HBox projectileImageSelect = new HBox(); 
		try {
			projectileImageSelect = getUIFactory().setupImageSelector(myPropertiesReader, "Projectile ", PROJECTILE_IMAGES, projectileImageDisplay, 30);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		}

		ArrayList<String> dummyTowerAbilities = new ArrayList<String>();
		dummyTowerAbilities.add("Freeze");
		dummyTowerAbilities.add("Fire");
		HBox towerAbility = getUIFactory().setupPromptAndDropdown("", "Tower Ability: ", dummyTowerAbilities);
		
		VBox towerRange = getUIFactory().setupPromptAndSlider("towerRangeSlider", "Tower Range: ", DEFAULT_TOWER_MAX_RANGE); 
		VBox towerPrice = getUIFactory().setupPromptAndSlider("towerPriceSlider", "Tower Price: ", DEFAULT_TOWER_MAX_PRICE); 
		HBox backAndApply = setupBackAndApplyButton(); 

		vb.getChildren().add(towerNameSelect);
		vb.getChildren().add(towerImageSelect);
		vb.getChildren().add(towerImageDisplay);
		vb.getChildren().add(projectileImageSelect);
		vb.getChildren().add(projectileImageDisplay);
		vb.getChildren().add(towerAbility);
		vb.getChildren().add(towerRange);
		vb.getChildren().add(towerPrice);
		vb.getChildren().add(backAndApply);
		return new Scene(vb, 1500, 900); 
	}

	private HBox setupImageSelector(String description, String propertiesFilepath, ImageView imageDisplay, double imageSize) {
		Map<String, Image> towerImageOptions;
		try {
			towerImageOptions = myPropertiesReader.keyToImageMap(propertiesFilepath, imageSize, imageSize);
		} catch (MissingPropertiesException e) {
			return new HBox(); // TODO fix
		}
		ArrayList<String> imageNames = new ArrayList<String>(towerImageOptions.keySet());
		final ArrayList<Image> images = new ArrayList<Image>(towerImageOptions.values()); 
		imageDisplay.setImage(images.get(0));
		ComboBox<String> imageOptionsDropdown = getUIFactory().makeTextDropdown("", imageNames);
		imageOptionsDropdown.getSelectionModel().selectFirst();
		
		HBox imageSelect = new HBox();
		Text prompt = new Text(description+"Image: ");
		Button loadNewImageButton = new Button("Load New Image");
		loadNewImageButton.setId("loadButton");
		imageSelect.getChildren().add(prompt);
		imageSelect.getChildren().add(imageOptionsDropdown);
		imageSelect.getChildren().add(loadNewImageButton);

		// image selection view 
		imageOptionsDropdown.getSelectionModel().selectedIndexProperty().addListener(( arg0, arg1,  arg2) ->{
			imageDisplay.setImage(images.get((int) arg2));
		});
		
		return imageSelect; 
	}

}
