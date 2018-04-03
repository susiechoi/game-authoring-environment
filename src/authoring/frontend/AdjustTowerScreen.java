package authoring.frontend;

import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
		}
		TextField nameInputField = (TextField) towerNameSelect.getChildren().get(1);
		
//		ImageView towerImageDisplay = new ImageView(); 
		HBox towerImageSelect = new HBox();
		try {
			towerImageSelect = getUIFactory().setupImageSelector(myPropertiesReader, "Tower ", TOWER_IMAGES, 50);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		} 
//		ImageView projectileImageDisplay = new ImageView(); 
		HBox projectileImageSelect = new HBox(); 
		try {
			projectileImageSelect = getUIFactory().setupImageSelector(myPropertiesReader, "Projectile ", PROJECTILE_IMAGES, 30);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		}

		ArrayList<String> dummyTowerAbilities = new ArrayList<String>();
		dummyTowerAbilities.add("Freeze");
		dummyTowerAbilities.add("Fire");
		HBox towerAbility = getUIFactory().setupPromptAndDropdown("", "Tower Ability: ", dummyTowerAbilities);
		
		HBox towerRange = getUIFactory().setupPromptAndSlider("towerRangeSlider", "Tower Range: ", DEFAULT_TOWER_MAX_RANGE); 
		HBox towerPrice = getUIFactory().setupPromptAndSlider("towerPriceSlider", "Tower Price: ", DEFAULT_TOWER_MAX_PRICE); 
		HBox backAndApply = setupBackAndApplyButton(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText("Build Your Tower"));
		vb.getChildren().add(towerNameSelect);
		vb.getChildren().add(towerImageSelect);
//		vb.getChildren().add(towerImageDisplay);
		vb.getChildren().add(projectileImageSelect);
//		vb.getChildren().add(projectileImageDisplay);
		vb.getChildren().add(towerAbility);
		vb.getChildren().add(towerRange);
		vb.getChildren().add(towerPrice);
		vb.getChildren().add(backAndApply);
		
		Scene screen = new Scene(vb, 1500, 900); 
		getUIFactory().applyTextFieldFocusAction(screen, nameInputField);
		return screen;
	}

}
