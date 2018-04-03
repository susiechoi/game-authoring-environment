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
	public static final String ENGLISH_PROMPT_FILE = "languages/English/Prompts.properties"; //TODO: shouldn't be hardcoded! need to get language to frontend
	public static final String ENGLISH_ERROR_FILE = "languages/English/Errors.properties";
	public static final int DEFAULT_TOWER_MAX_RANGE = 500; 
	public static final int DEFAULT_TOWER_MAX_PRICE = 500; 

	protected AdjustTowerScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		HBox towerNameSelect = new HBox();
		try {
			towerNameSelect = getUIFactory().setupPromptAndTextField("", getPropertiesReader().findVal(ENGLISH_PROMPT_FILE, "TowerName")); 
		}
		catch(MissingPropertiesException e){
			try {
			showError(getPropertiesReader().findVal(ENGLISH_ERROR_FILE, "NoFile"));
			}
			catch (MissingPropertiesException e2) {
				showError("Missing a properties file! Defaulting to English");
			}
		}
		TextField nameInputField = (TextField) towerNameSelect.getChildren().get(1);
		
		HBox towerImageSelect = new HBox();
		try {
			towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Tower", "English") + " " , TOWER_IMAGES, 50, getErrorCheckedPrompt("LoadImage", "English"), getErrorCheckedPrompt("NewImage", "English"));
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		} 
		HBox projectileImageSelect = new HBox(); 
		try {
			projectileImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Projectile", "English") + " " , PROJECTILE_IMAGES, 50, getErrorCheckedPrompt("LoadImage", "English"), getErrorCheckedPrompt("NewImage", "English"));
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		}

		ArrayList<String> dummyTowerAbilities = new ArrayList<String>(); // TODO read in abilities
		dummyTowerAbilities.add("Freeze");
		dummyTowerAbilities.add("Fire");
		HBox towerAbility = getUIFactory().setupPromptAndDropdown("", "Tower Ability: ", dummyTowerAbilities);
		
		HBox towerRange = getUIFactory().setupPromptAndSlider("towerRangeSlider", "Tower Range: ", DEFAULT_TOWER_MAX_RANGE); 
		HBox towerPrice = getUIFactory().setupPromptAndSlider("towerPriceSlider", "Tower Price: ", DEFAULT_TOWER_MAX_PRICE); 
		HBox backAndApply = setupBackAndApplyButton(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText("Build Your Tower"));
		vb.getChildren().add(towerNameSelect);
		vb.getChildren().add(towerImageSelect);
		vb.getChildren().add(projectileImageSelect);
		vb.getChildren().add(towerAbility);
		vb.getChildren().add(towerRange);
		vb.getChildren().add(towerPrice);
		vb.getChildren().add(backAndApply);
		
		Scene screen = new Scene(vb, 1500, 900); 
		getUIFactory().applyTextFieldFocusAction(screen, nameInputField);
		return screen;
	}

}
