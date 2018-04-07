package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/GameAuthoringStartScreen.css";
	public static final String ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final int DEFAULT_ENEMY_MAX_HEALTH_IMPACT = 3; 
	public static final int DEFAULT_ENEMY_MAX_$_IMPACT = 50; 
	public static final int DEFAULT_ENEMY_MAX_SPEED = 100; 
	public static final int DEFAULT_MAX_RANGE = 500; 
	public static final int DEFAULT_MAX_PRICE = 500;  

	protected AdjustEnemyScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}

	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {
		VBox vb = new VBox(); 	
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeEnemy", "English")));

		TextField nameInputField = getUIFactory().makeTextField("");
		HBox enemyNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("EnemyName", getView().getLanguage()));
		vb.getChildren().add(enemyNameSelect);

		HBox enemyImageSelect = new HBox();
		ComboBox<String> dropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(ENEMY_IMAGES));
		enemyImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", ENEMY_IMAGES, 75, getErrorCheckedPrompt("NewImage", getView().getLanguage()), getErrorCheckedPrompt("LoadImage", getView().getLanguage()),
				getErrorCheckedPrompt("NewImageName", getView().getLanguage()), dropdown);
		vb.getChildren().add(enemyImageSelect);

		Slider enemySpeedSlider = getUIFactory().setupSlider("enemySpeedSlider",  DEFAULT_ENEMY_MAX_SPEED); 
		HBox enemySpeed = getUIFactory().setupSliderWithValue("", enemySpeedSlider, getErrorCheckedPrompt("EnemySpeed", getView().getLanguage()));
		vb.getChildren().add(enemySpeed);

		Slider enemySlider = getUIFactory().setupSlider("enemyImpactSlider",  DEFAULT_ENEMY_MAX_HEALTH_IMPACT); 
		HBox enemyImpact = getUIFactory().setupSliderWithValue("enemyImpactSlider", enemySlider, getErrorCheckedPrompt("EnemyImpact", getView().getLanguage())); 
		vb.getChildren().add(enemyImpact);

		Slider enemyImpactSlider = getUIFactory().setupSlider("enemyMoneyImpactSlider",  DEFAULT_ENEMY_MAX_$_IMPACT); 
		HBox enemy$Impact = getUIFactory().setupSliderWithValue("enemyMoneyImpactSlider", enemyImpactSlider, getErrorCheckedPrompt("EnemyCurrencyImpact", getView().getLanguage())); 
		vb.getChildren().add(enemy$Impact);
		
		Slider enemyValueSlider = getUIFactory().setupSlider("EnemyValueSlider", DEFAULT_MAX_PRICE);
		HBox enemyValue = getUIFactory().setupSliderWithValue("EnemyValueSlider", enemyValueSlider, getErrorCheckedPrompt("EnemyValue", getView().getLanguage()));
		vb.getChildren().add(enemyValue);

		Slider enemyUpgradeCostSlider = getUIFactory().setupSlider("EnemyUpgradeCostSlider", DEFAULT_MAX_PRICE);
		HBox enemyUpgradeCost = getUIFactory().setupSliderWithValue("EnemyUpgradeCostSlider", enemyUpgradeCostSlider, getErrorCheckedPrompt("EnemyUpgradeCost", getView().getLanguage()));
		vb.getChildren().add(enemyUpgradeCost);

		Slider enemyUpgradeValueSlider = getUIFactory().setupSlider("EnemyUpgradeValueSlider", DEFAULT_MAX_PRICE);
		HBox enemyUpgradeValue = getUIFactory().setupSliderWithValue("EnemyUpgradeValueSlider", enemyUpgradeValueSlider, getErrorCheckedPrompt("EnemyUpgradeValue", getView().getLanguage()));
		vb.getChildren().add(enemyUpgradeValue);

		HBox backAndApply = setupBackAndApplyButton(); 
		vb.getChildren().add(backAndApply);

		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		Scene screen = new Scene(sp, 1500, 900); 
		getUIFactory().applyTextFieldFocusAction(screen, nameInputField);
		return screen;
	}

}
