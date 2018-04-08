/**
 * @author susiechoi
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

abstract class AdjustEnemyScreen extends AdjustNewOrExistingScreen {

	public static final String ENEMY_IMAGES = "images/EnemyImageNames.properties";
		
	private TextField myNameField; 
	private ComboBox<String> myImageDropdown;
	private Slider mySpeedSlider;
	private Slider myHealthImpactSlider; 
	private Slider myValueSlider; 
	private Slider myUpgradeCostSlider; 
	private Slider myUpgradeValueSlider; 
	
	protected AdjustEnemyScreen(AuthoringView view) {
		super(view);
	}

	@Override
	protected Parent populateScreenWithFields() {
		VBox vb = new VBox(); 	
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeEnemy")));

		TextField nameInputField = getUIFactory().makeTextField("");
		myNameField = nameInputField; 
		HBox enemyNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("EnemyName"));
		vb.getChildren().add(enemyNameSelect);

		HBox enemyImageSelect = new HBox();
		ComboBox<String> dropdown = new ComboBox<String>();
		try {
			dropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(ENEMY_IMAGES));
			myImageDropdown = dropdown; 
			enemyImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", ENEMY_IMAGES, 75, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), dropdown);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(enemyImageSelect);

		Slider enemySpeedSlider = getUIFactory().setupSlider("enemySpeedSlider",  myMaxSpeed); 
		mySpeedSlider = enemySpeedSlider; 
		HBox enemySpeed = getUIFactory().setupSliderWithValue("", enemySpeedSlider, getErrorCheckedPrompt("EnemySpeed"));
		vb.getChildren().add(enemySpeed);

		Slider enemyHealthImpactSlider = getUIFactory().setupSlider("enemyImpactSlider",  myMaxHealthImpact); 
		myHealthImpactSlider = enemyHealthImpactSlider; 
		HBox enemyImpact = getUIFactory().setupSliderWithValue("enemyImpactSlider", enemyHealthImpactSlider, getErrorCheckedPrompt("EnemyHealthImpact")); 
		vb.getChildren().add(enemyImpact);
		
		Slider enemyValueSlider = getUIFactory().setupSlider("EnemyValueSlider", myMaxPrice);
		myValueSlider = enemyValueSlider;
		HBox enemyValue = getUIFactory().setupSliderWithValue("EnemyValueSlider", enemyValueSlider, getErrorCheckedPrompt("EnemyValue"));
		vb.getChildren().add(enemyValue);

		Slider enemyUpgradeCostSlider = getUIFactory().setupSlider("EnemyUpgradeCostSlider", myMaxPrice);
		myUpgradeCostSlider = enemyUpgradeCostSlider; 
		HBox enemyUpgradeCost = getUIFactory().setupSliderWithValue("EnemyUpgradeCostSlider", enemyUpgradeCostSlider, getErrorCheckedPrompt("EnemyUpgradeCost"));
		vb.getChildren().add(enemyUpgradeCost);

		Slider enemyUpgradeValueSlider = getUIFactory().setupSlider("EnemyUpgradeValueSlider", myMaxUpgradeIncrement);
		myUpgradeValueSlider = enemyUpgradeValueSlider; 
		HBox enemyUpgradeValue = getUIFactory().setupSliderWithValue("EnemyUpgradeValueSlider", enemyUpgradeValueSlider, getErrorCheckedPrompt("EnemyUpgradeValue"));
		vb.getChildren().add(enemyUpgradeValue);

		Button backButton = setupBackButtonSuperclass();
		
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			getView().makeEnemy(getView().getLevel(), getIsNewObject(), myNameField.getText(), myImageDropdown.getValue(), mySpeedSlider.getValue(), myHealthImpactSlider.getValue(), myValueSlider.getValue(), myUpgradeCostSlider.getValue(), myUpgradeValueSlider.getValue());
		});
		
		HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);

		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		return sp;
	}
	
	protected abstract void populateFieldsWithData();
	
	protected TextField getMyNameField() {
		return myNameField;
	} 
	
	protected ComboBox<String> getMyImageDropdown() {
		return myImageDropdown;
	}
	
	protected Slider getMySpeedSlider() {
		return mySpeedSlider;
	}
	
	protected Slider getMyHealthImpactSlider() {
		return myHealthImpactSlider;
	}
	
	protected Slider getMyValueSlider() {
		return myValueSlider;
	} 
	
	protected Slider getMyUpgradeCostSlider() {
		return myUpgradeCostSlider;
	} 
	
	protected Slider getMyUpgradeValueSlider() {
		return myUpgradeValueSlider;
	}

}
