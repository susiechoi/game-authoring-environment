/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) enemy object
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
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

	public static final String OBJECT_DESCRIPTION = "Enemy";
	public static final String ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final String ENEMY_FIELDS = "default_objects/EnemyFields.properties";

	private TextField myNameField; 
	private ComboBox<String> myImageDropdown;
	private Slider mySpeedSlider;
	private Slider myInitialHealthSlider; 
	private Slider myHealthImpactSlider; 
	private Slider myValueSlider; 

	protected AdjustEnemyScreen(AuthoringView view, String selectedObjectName) {
		super(view, selectedObjectName, ENEMY_FIELDS, OBJECT_DESCRIPTION);
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
		ComboBox<String> enemyImageDropdown = new ComboBox<String>();
		ImageView imageDisplay = new ImageView(); 
		try {
			enemyImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(ENEMY_IMAGES));
			myImageDropdown = enemyImageDropdown; 
			enemyImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", ENEMY_IMAGES, 75, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), enemyImageDropdown, imageDisplay);
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

		Button backButton = setupBackButton();

		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			if (validNameField(myNameField)) {
				try {
					getView().makeEnemy(getIsNewObject(), myNameField.getText(), myImageDropdown.getValue(), mySpeedSlider.getValue(), myInitialHealthSlider.getValue(), myHealthImpactSlider.getValue(), myValueSlider.getValue(), 0, 0);
					getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");			
				} catch(NoDuplicateNamesException e1) {
					getView().loadErrorAlert("NoDuplicateNames");
				}
			}
		});

		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);

		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		return sp;
	}

	protected TextField getNameField() {
		return myNameField; 
	}


}
