package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PropertiesReader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	public static final String TOWER_IMAGES = "images/TestImageNames.properties";
	public static final String ENEMY_IMAGES = "images/EnemiesImageNames.properties";
	public static final int DEFAULT_ENEMY_MAX_HEALTH_IMPACT = 3; 
	public static final int DEFAULT_ENEMY_MAX_$_IMPACT = 50; 
	public static final int DEFAULT_ENEMY_MAX_SPEED = 100; 


	protected AdjustEnemyScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}

	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {
		VBox vb = new VBox(); 	
		
		TextField nameInputField = getUIFactory().makeTextField("");
		HBox enemyNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("EnemyName", getView().getLanguage()));
		HBox enemyImageSelect = new HBox();
		ComboBox<String> dropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(ENEMY_IMAGES));
		
		enemyImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", TOWER_IMAGES, 75, getErrorCheckedPrompt("LoadImage", getView().getLanguage()), getErrorCheckedPrompt("NewImage", getView().getLanguage()),
				getErrorCheckedPrompt("NewImage", getView().getLanguage()), dropdown);
		System.out.println("here");
		Slider enemySpeedSlider = getUIFactory().setupSlider("enemySpeedSlider",  DEFAULT_ENEMY_MAX_SPEED); 
		HBox enemySpeed = getUIFactory().addPromptAndSetupHBox("", enemySpeedSlider, getErrorCheckedPrompt("EnemySpeed", getView().getLanguage()));
		Slider enemyHealthSlider = getUIFactory().setupSlider("enemyHealthImpactSlider",  DEFAULT_ENEMY_MAX_HEALTH_IMPACT); 
		HBox enemyHealthImpact = getUIFactory().addPromptAndSetupHBox("enemyHealthImpactSlider", enemyHealthSlider, getErrorCheckedPrompt("EnemyHealth", getView().getLanguage())); 
		Slider enemyImpactSlider = getUIFactory().setupSlider("enemyMoneyImpactSlider",  DEFAULT_ENEMY_MAX_$_IMPACT); 
		HBox enemy$Impact = getUIFactory().addPromptAndSetupHBox("enemyMoneyImpactSlider", enemyImpactSlider, getErrorCheckedPrompt("EnemyImpact", getView().getLanguage())); 

		HBox backAndApply = setupBackAndApplyButton(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText("Build Your Enemy"));
		vb.getChildren().add(enemyNameSelect);
		vb.getChildren().add(enemyImageSelect);
		vb.getChildren().add(enemySpeed);
		vb.getChildren().add(enemyHealthImpact);
		vb.getChildren().add(enemy$Impact);
		vb.getChildren().add(backAndApply);

		Scene screen = new Scene(vb, 1500, 900); 
		getUIFactory().applyTextFieldFocusAction(screen, nameInputField);
		return screen;
	}

}
