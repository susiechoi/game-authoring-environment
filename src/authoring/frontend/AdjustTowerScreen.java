package authoring.frontend;

import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
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
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException{
		VBox vb = new VBox(); 
		HBox towerNameSelect = new HBox();
		TextField nameInputField = getUIFactory().makeTextField(""); 
		towerNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getPropertiesReader().findVal(ENGLISH_PROMPT_FILE, "TowerName"));
		HBox towerImageSelect = new HBox();
		ComboBox<String> towerImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TOWER_IMAGES));
		towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Tower", "English") + " " , TOWER_IMAGES, 50, getErrorCheckedPrompt("LoadImage", "English"), getErrorCheckedPrompt("NewImage", "English"),
				getErrorCheckedPrompt("NewImage", getView().getLanguage()), towerImageDropdown);
		HBox projectileImageSelect = new HBox(); 
		ComboBox<String> projectileImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(PROJECTILE_IMAGES));
		projectileImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Projectile", "English") + " " , PROJECTILE_IMAGES, 50, getErrorCheckedPrompt("LoadImage", "English"), getErrorCheckedPrompt("NewImage", "English"),
				getErrorCheckedPrompt("NewImage", getView().getLanguage()), projectileImageDropdown);
		ArrayList<String> dummyTowerAbilities = new ArrayList<String>(); // TODO read in abilities
		dummyTowerAbilities.add("Freeze");
		dummyTowerAbilities.add("Fire");
		ComboBox<String> towerAbilityDropdown = getUIFactory().makeTextDropdown("", dummyTowerAbilities);
		HBox towerAbility = getUIFactory().addPromptAndSetupHBox("", towerAbilityDropdown, getErrorCheckedPrompt("TowerAbility", getView().getLanguage()));	
		Slider towerRangeSlider = getUIFactory().setupSlider("towerRangeSlider", DEFAULT_TOWER_MAX_RANGE);
		HBox towerRange = getUIFactory().addPromptAndSetupHBox("towerRangeSlider", towerRangeSlider, getErrorCheckedPrompt("TowerRange", getView().getLanguage()));
		Slider towerPriceSlider = getUIFactory().setupSlider("towerPriceSlider", DEFAULT_TOWER_MAX_PRICE);
		HBox towerPrice = getUIFactory().addPromptAndSetupHBox("towerPriceSlider", towerPriceSlider, getErrorCheckedPrompt("TowerPrice", getView().getLanguage()));
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
