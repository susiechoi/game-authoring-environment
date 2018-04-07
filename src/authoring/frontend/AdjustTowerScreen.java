package authoring.frontend;

import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustTowerScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/GameAuthoringStartScreen.css";
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String PROJECTILE_IMAGES = "images/ProjectileImageNames.properties"; 
	public static final String ENGLISH_PROMPT_FILE = "languages/English/Prompts.properties"; //TODO: shouldn't be hardcoded! need to get language to frontend
	public static final String ENGLISH_ERROR_FILE = "languages/English/Errors.properties";
	public static final int DEFAULT_MAX_RANGE = 500; 
	public static final int DEFAULT_MAX_PRICE = 500;  

	protected AdjustTowerScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}

	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException{		
		VBox vb = new VBox(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeTower", "English")));

		TextField nameInputField = getUIFactory().makeTextField(""); 
		HBox towerNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getPropertiesReader().findVal(ENGLISH_PROMPT_FILE, "TowerName"));
		vb.getChildren().add(towerNameSelect);

		HBox towerImageSelect = new HBox();
		ComboBox<String> towerImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TOWER_IMAGES));
		towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Tower", "English") + " " , TOWER_IMAGES, 50, getErrorCheckedPrompt("NewImage", "English"), getErrorCheckedPrompt("LoadImage", "English"),
				getErrorCheckedPrompt("NewImageName", getView().getLanguage()), towerImageDropdown);
		vb.getChildren().add(towerImageSelect);

		makeHealthComponents(vb);
		makeProjectileComponents(vb);
		makeLauncherComponents(vb);
		
		HBox backAndApply = setupBackAndApplyButton(); 
		vb.getChildren().add(backAndApply);

		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		Scene screen = new Scene(sp, 1500, 900); 
		
		getUIFactory().applyTextFieldFocusAction(screen, nameInputField);
		return screen;
	}

	private void makeHealthComponents(VBox vb) {
		Slider towerHealthValueSlider = getUIFactory().setupSlider("TowerHealthValueSlider", DEFAULT_MAX_PRICE);
		HBox towerHealthValue = getUIFactory().setupSliderWithValue("TowerHealthValueSlider", towerHealthValueSlider, getErrorCheckedPrompt("TowerHealthValue", getView().getLanguage()));
		vb.getChildren().add(towerHealthValue);

		Slider towerHealthUpgradeCostSlider = getUIFactory().setupSlider("TowerHealthUpgradeCostSlider", DEFAULT_MAX_PRICE);
		HBox towerHealthUpgradeCost = getUIFactory().setupSliderWithValue("TowerHealthUpgradeCostSlider", towerHealthUpgradeCostSlider, getErrorCheckedPrompt("TowerHealthUpgradeCost", getView().getLanguage()));
		vb.getChildren().add(towerHealthUpgradeCost);

		Slider towerHealthUpgradeValueSlider = getUIFactory().setupSlider("TowerHealthUpgradeValueSlider", DEFAULT_MAX_PRICE);
		HBox towerHealthUpgradeValue = getUIFactory().setupSliderWithValue("TowerHealthUpgradeValueSlider", towerHealthUpgradeValueSlider, getErrorCheckedPrompt("TowerHealthUpgradeValue", getView().getLanguage()));
		vb.getChildren().add(towerHealthUpgradeValue);
	}
	
	private void makeProjectileComponents(VBox vb) throws MissingPropertiesException {
		ComboBox<String> projectileImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(PROJECTILE_IMAGES));
		HBox projectileImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Projectile", "English") + " " , PROJECTILE_IMAGES, 50, getErrorCheckedPrompt("NewImage", "English"), getErrorCheckedPrompt("LoadImage", "English"),getErrorCheckedPrompt("NewImageName", getView().getLanguage()), projectileImageDropdown);
		vb.getChildren().add(projectileImageSelect);

		ArrayList<String> dummyProjectileAbilities = new ArrayList<String>(); // TODO read in abilities
		dummyProjectileAbilities.add("Freeze");
		dummyProjectileAbilities.add("Fire");
		ComboBox<String> projectileAbilityDropdown = getUIFactory().makeTextDropdown("", dummyProjectileAbilities);
		HBox projectileAbility = getUIFactory().addPromptAndSetupHBox("", projectileAbilityDropdown, getErrorCheckedPrompt("ProjectileAbility", getView().getLanguage()));	
		vb.getChildren().add(projectileAbility);

		Slider projectileDamageSlider = getUIFactory().setupSlider("ProjectileDamageSlider", DEFAULT_MAX_RANGE);
		HBox projectileDamage = getUIFactory().setupSliderWithValue("ProjectileDamageSlider", projectileDamageSlider, getErrorCheckedPrompt("ProjectileDamage", getView().getLanguage()));
		vb.getChildren().add(projectileDamage);

		Slider projectileValueSlider = getUIFactory().setupSlider("ProjectileValueSlider", DEFAULT_MAX_PRICE);
		HBox projectileValue = getUIFactory().setupSliderWithValue("ProjectileValueSlider", projectileValueSlider, getErrorCheckedPrompt("ProjectileValue", getView().getLanguage()));
		vb.getChildren().add(projectileValue);

		Slider projectileUpgradeCostSlider = getUIFactory().setupSlider("ProjectileUpgradeCostSlider", DEFAULT_MAX_PRICE);
		HBox projectileUpgradeCost = getUIFactory().setupSliderWithValue("ProjectileUpgradeCostSlider", projectileUpgradeCostSlider, getErrorCheckedPrompt("ProjectileUpgradeCost", getView().getLanguage()));
		vb.getChildren().add(projectileUpgradeCost);

		Slider projectileUpgradeValueSlider = getUIFactory().setupSlider("ProjectileUpgradeValueSlider", DEFAULT_MAX_PRICE);
		HBox projectileUpgradeValue = getUIFactory().setupSliderWithValue("ProjectileUpgradeValueSlider", projectileUpgradeValueSlider, getErrorCheckedPrompt("ProjectileUpgradeValue", getView().getLanguage()));
		vb.getChildren().add(projectileUpgradeValue);
	}
	
	private void makeLauncherComponents(VBox vb) {
		Slider launcherValueSlider = getUIFactory().setupSlider("LauncherValueSlider", DEFAULT_MAX_PRICE);
		HBox launcherValue = getUIFactory().setupSliderWithValue("LauncherValueSlider", launcherValueSlider, getErrorCheckedPrompt("LauncherValue", getView().getLanguage()));
		vb.getChildren().add(launcherValue);

		Slider launcherUpgradeCostSlider = getUIFactory().setupSlider("LauncherUpgradeCostSlider", DEFAULT_MAX_PRICE);
		HBox launcherUpgradeCost = getUIFactory().setupSliderWithValue("LauncherUpgradeCostSlider", launcherUpgradeCostSlider, getErrorCheckedPrompt("LauncherUpgradeCost", getView().getLanguage()));
		vb.getChildren().add(launcherUpgradeCost);

		Slider launcherUpgradeValueSlider = getUIFactory().setupSlider("LauncherUpgradeValueSlider", DEFAULT_MAX_PRICE);
		HBox launcherUpgradeValue = getUIFactory().setupSliderWithValue("LauncherUpgradeValueSlider", launcherUpgradeValueSlider, getErrorCheckedPrompt("LauncherUpgradeValue", getView().getLanguage()));
		vb.getChildren().add(launcherUpgradeValue);

		Slider launcherRateSlider = getUIFactory().setupSlider("LauncherRateSlider", DEFAULT_MAX_RANGE);
		HBox launcherRate = getUIFactory().setupSliderWithValue("LauncherRateSlider", launcherRateSlider, getErrorCheckedPrompt("LauncherRate", getView().getLanguage()));
		vb.getChildren().add(launcherRate);

		Slider launcherRangeSlider = getUIFactory().setupSlider("LauncherRangeSlider", DEFAULT_MAX_RANGE);
		HBox launcherRange = getUIFactory().setupSliderWithValue("LauncherRangeSlider", launcherRangeSlider, getErrorCheckedPrompt("LauncherRange", getView().getLanguage()));
		vb.getChildren().add(launcherRange);
	}

}
