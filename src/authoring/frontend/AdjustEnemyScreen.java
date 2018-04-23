/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) enemy object
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustNewOrExistingScreen {

	public static final String OBJECT_TYPE = "Enemy";
	public static final String ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final String ENEMY_FIELDS = "default_objects/EnemyFields.properties";

	private String myObjectName; 
	private TextField myNameField; 
	private ComboBox<String> myImageDropdown;
	private Slider mySpeedSlider;
	private Slider myInitialHealthSlider; 
	private Slider myHealthImpactSlider; 
	private Slider myValueSlider; 

	protected AdjustEnemyScreen(AuthoringView view, String selectedObjectName) {
		super(view, selectedObjectName, ENEMY_FIELDS, OBJECT_TYPE);
		myObjectName = selectedObjectName; 
	}

	@Override
	protected Parent populateScreenWithFields() {
		VBox vb = new VBox(); 	
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeEnemy")));

		HBox enemyImageSelect = new HBox();
		ComboBox<String> enemyImageDropdown = new ComboBox<String>();
		ImageView imageDisplay = new ImageView(); 
		try {
			enemyImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(ENEMY_IMAGES));
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		myImageDropdown = enemyImageDropdown;  
		myImageDropdown.addEventHandler(ActionEvent.ACTION, e -> {
			getView().setObjectAttribute("Enemy", myObjectName, "myImage", myImageDropdown.getSelectionModel().getSelectedItem()); 
		});
		try {
			enemyImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", ENEMY_IMAGES, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), enemyImageDropdown, imageDisplay);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(enemyImageSelect);

		Slider enemySpeedSlider = getUIFactory().setupSlider("enemySpeedSlider",  getMyMaxSpeed()); 
		mySpeedSlider = enemySpeedSlider; 
		HBox enemySpeed = getUIFactory().setupSliderWithValue("", enemySpeedSlider, getErrorCheckedPrompt("EnemySpeed"));
		vb.getChildren().add(enemySpeed);
		mySpeedSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "mySpeed", newValue);
		});

		Slider enemyInitialHealthSlider = getUIFactory().setupSlider("enemyInitialHealthSlider",  getMyMaxHealthImpact()); 
		myInitialHealthSlider = enemyInitialHealthSlider; 
		HBox initialHealth = getUIFactory().setupSliderWithValue("enemyInitialHealthSlider", enemyInitialHealthSlider, getErrorCheckedPrompt("EnemyInitialHealth")); 
		vb.getChildren().add(initialHealth);
		myInitialHealthSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myInitialHealth", newValue);
		});

		Slider enemyHealthImpactSlider = getUIFactory().setupSlider("enemyImpactSlider",  getMyMaxHealthImpact()); 
		myHealthImpactSlider = enemyHealthImpactSlider; 
		HBox enemyImpact = getUIFactory().setupSliderWithValue("enemyImpactSlider", enemyHealthImpactSlider, getErrorCheckedPrompt("EnemyHealthImpact")); 
		vb.getChildren().add(enemyImpact);
		myHealthImpactSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthImpact", newValue);
		});

		Slider enemyValueSlider = getUIFactory().setupSlider("EnemyValueSlider", getMyMaxPrice());
		myValueSlider = enemyValueSlider;
		HBox enemyValue = getUIFactory().setupSliderWithValue("EnemyValueSlider", enemyValueSlider, getErrorCheckedPrompt("EnemyValue"));
		vb.getChildren().add(enemyValue);
		myValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myKillReward", newValue);
		});

		Button backButton = setupBackButton();

		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");			
		});

		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);

		return vb;
	}

	protected TextField getNameField() {
		return myNameField; 
	}


}
