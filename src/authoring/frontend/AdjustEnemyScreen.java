/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) enemy object
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustNewOrExistingScreen {

	public static final String ENEMY_IMAGES = "images/EnemyImageNames.properties";
		
	private TextField myNameField; 
	private ComboBox<String> myImageDropdown;
	private Slider mySpeedSlider;
	private Slider myInitialHealthSlider; 
	private Slider myHealthImpactSlider; 
	private Slider myValueSlider; 
	private Slider myUpgradeCostSlider; 
	private Slider myUpgradeValueSlider; 
	
	protected AdjustEnemyScreen(AuthoringView view, String selectedObjectName) {
		super(view, selectedObjectName);
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
		ImageView imageDisplay = new ImageView(); 
		try {
			dropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(ENEMY_IMAGES));
			myImageDropdown = dropdown; 
			enemyImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", ENEMY_IMAGES, 75, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), dropdown, imageDisplay);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(enemyImageSelect);

		Slider enemySpeedSlider = getUIFactory().setupSlider("enemySpeedSlider",  getMyMaxSpeed()); 
		mySpeedSlider = enemySpeedSlider; 
		HBox enemySpeed = getUIFactory().setupSliderWithValue("", enemySpeedSlider, getErrorCheckedPrompt("EnemySpeed"));
		vb.getChildren().add(enemySpeed);

		Slider enemyInitialHealthSlider = getUIFactory().setupSlider("enemyInitialHealthSlider",  getMyMaxHealthImpact()); 
		myInitialHealthSlider = enemyInitialHealthSlider; 
		HBox initialHealth = getUIFactory().setupSliderWithValue("enemyInitialHealthSlider", enemyInitialHealthSlider, getErrorCheckedPrompt("EnemyInitialHealth")); 
		vb.getChildren().add(initialHealth);
		
		Slider enemyHealthImpactSlider = getUIFactory().setupSlider("enemyImpactSlider",  getMyMaxHealthImpact()); 
		myHealthImpactSlider = enemyHealthImpactSlider; 
		HBox enemyImpact = getUIFactory().setupSliderWithValue("enemyImpactSlider", enemyHealthImpactSlider, getErrorCheckedPrompt("EnemyHealthImpact")); 
		vb.getChildren().add(enemyImpact);
		
		Slider enemyValueSlider = getUIFactory().setupSlider("EnemyValueSlider", getMyMaxPrice());
		myValueSlider = enemyValueSlider;
		HBox enemyValue = getUIFactory().setupSliderWithValue("EnemyValueSlider", enemyValueSlider, getErrorCheckedPrompt("EnemyValue"));
		vb.getChildren().add(enemyValue);

		Slider enemyUpgradeCostSlider = getUIFactory().setupSlider("EnemyUpgradeCostSlider", getMyMaxPrice());
		myUpgradeCostSlider = enemyUpgradeCostSlider; 
		HBox enemyUpgradeCost = getUIFactory().setupSliderWithValue("EnemyUpgradeCostSlider", enemyUpgradeCostSlider, getErrorCheckedPrompt("EnemyUpgradeCost"));
		vb.getChildren().add(enemyUpgradeCost);

		Slider enemyUpgradeValueSlider = getUIFactory().setupSlider("EnemyUpgradeValueSlider", getMyMaxUpgradeIncrement());
		myUpgradeValueSlider = enemyUpgradeValueSlider; 
		HBox enemyUpgradeValue = getUIFactory().setupSliderWithValue("EnemyUpgradeValueSlider", enemyUpgradeValueSlider, getErrorCheckedPrompt("EnemyUpgradeValue"));
		vb.getChildren().add(enemyUpgradeValue);

		Button backButton = setupBackButtonSuperclass();
		
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			getView().makeEnemy(getIsNewObject(), myNameField.getText(), imageDisplay.getImage(), mySpeedSlider.getValue(), myInitialHealthSlider.getValue(), myHealthImpactSlider.getValue(), myValueSlider.getValue(), myUpgradeCostSlider.getValue(), myUpgradeValueSlider.getValue());
		});
		
		HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);

		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		return sp;
	}
	
	/**
	 * The following methods are getters for features/fields on the Screen
	 * To be invoked by the Screen subclasses that manage population of fields with existing object attributes 
	 */
	
	protected void populateFieldsWithData() {
		myNameField.setText(getMySelectedObjectName());
		
		setEditableOrNot(myNameField, getIsNewObject());
		
		getUIFactory().setComboBoxToValue(myImageDropdown,getView().getObjectAttribute("Enemy", getMySelectedObjectName(), "myImage")); 
		
		getUIFactory().setSliderToValue(mySpeedSlider, getView().getObjectAttribute("Enemy", getMySelectedObjectName(), "mySpeed"));
		
		getUIFactory().setSliderToValue(myHealthImpactSlider, getView().getObjectAttribute("Enemy", getMySelectedObjectName(), "myHealthImpact"));
			
		getUIFactory().setSliderToValue(myValueSlider, getView().getObjectAttribute("Enemy", getMySelectedObjectName(), "myKillReward"));
		
		getUIFactory().setSliderToValue(myUpgradeCostSlider, getView().getObjectAttribute("Enemy", getMySelectedObjectName(), "myKillUgradeCost"));
		
		getUIFactory().setSliderToValue(myUpgradeValueSlider, getView().getObjectAttribute("Enemy", getMySelectedObjectName(), "myKillUpgradeValue"));
	}
	

}
