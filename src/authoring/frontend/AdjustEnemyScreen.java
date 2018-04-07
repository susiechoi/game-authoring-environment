package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	public static final String ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final int DEFAULT_ENEMY_MAX_HEALTH_IMPACT = 3; 
	public static final int DEFAULT_ENEMY_MAX_$_IMPACT = 50; 
	public static final int DEFAULT_ENEMY_MAX_SPEED = 100; 

	protected AdjustEnemyScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}

	@Override
	public Parent makeScreenWithoutStyling(){
		VBox vb = new VBox(); 	
		
		TextField nameInputField = getUIFactory().makeTextField("");
		HBox enemyNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("EnemyName"));
		HBox enemyImageSelect = new HBox();
		try {
		ComboBox<String> dropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(ENEMY_IMAGES));
		enemyImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", ENEMY_IMAGES, 75, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
				getErrorCheckedPrompt("NewImageName"), dropdown);
		}
		catch(MissingPropertiesException e) {
		    getView().loadErrorScreen("NoImageFile");
		}
		Slider enemySpeedSlider = getUIFactory().setupSlider("enemySpeedSlider",  DEFAULT_ENEMY_MAX_SPEED); 
		HBox enemySpeed = getUIFactory().addPromptAndSetupHBox("", enemySpeedSlider, getErrorCheckedPrompt("EnemySpeed"));
		Slider enemyHealthSlider = getUIFactory().setupSlider("enemyHealthImpactSlider",  DEFAULT_ENEMY_MAX_HEALTH_IMPACT); 
		HBox enemyHealthImpact = getUIFactory().addPromptAndSetupHBox("enemyHealthImpactSlider", enemyHealthSlider, getErrorCheckedPrompt("EnemyHealthImpact")); 
		Slider enemyImpactSlider = getUIFactory().setupSlider("enemyMoneyImpactSlider",  DEFAULT_ENEMY_MAX_$_IMPACT); 
		HBox enemy$Impact = getUIFactory().addPromptAndSetupHBox("enemyMoneyImpactSlider", enemyImpactSlider, getErrorCheckedPrompt("EnemyCurrencyImpact")); 

		HBox backAndApply = setupBackAndApplyButton(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText("Build Your Enemy"));
		vb.getChildren().add(enemyNameSelect);
		vb.getChildren().add(enemyImageSelect);
		vb.getChildren().add(enemySpeed);
		vb.getChildren().add(enemyHealthImpact);
		vb.getChildren().add(enemy$Impact);
		vb.getChildren().add(backAndApply);

		return vb;
	}

}
