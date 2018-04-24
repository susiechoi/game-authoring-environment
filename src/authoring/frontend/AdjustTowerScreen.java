/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) tower object
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


class AdjustTowerScreen extends AdjustNewOrExistingScreen {

	public static final String OBJECT_TYPE = "Tower";
<<<<<<< HEAD
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
=======
	public static final String TOWER_IMAGE_PREFIX = "images/ThemeSpecificImages/TowerImages/";
	public static final String TOWER_IMAGE_SUFFIX = "TowerImageNames.properties";
>>>>>>> b19bd205d47b3737d514c2c8231e74570c89bd12
	public static final String TOWER_FIELDS = "default_objects/TowerFields.properties";
	public static final String DEFAULT_PROJECTILE_IMAGE = "Bullet";

	private String myObjectName; 
	private ComboBox<String> myImageDropdown;
	private Slider myTowerHealthValueSlider;
	private Slider myTowerHealthUpgradeCostSlider;
	private Slider myTowerHealthUpgradeValueSlider;
	private Slider myTowerValueSlider;
	private Slider myTowerUpgradeCostSlider;
	private Slider myTowerUpgradeValueSlider; 

	protected AdjustTowerScreen(AuthoringView view, String selectedObjectName) {
		super(view, selectedObjectName, TOWER_FIELDS, OBJECT_TYPE);
		myObjectName = selectedObjectName; 
	}

	/**
	 * Populates screen with ComboBoxes/TextFields/Buttons/Sliders/Selectors needed to 
	 * fully specify a Tower object
	 * @see authoring.frontend.AdjustNewOrExistingScreen#populateScreenWithFields()
	 */
	@Override
	protected Parent populateScreenWithFields() {		
		VBox vb = new VBox(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeTower")));

		makeTowerComponents(vb);
		makeHealthComponents(vb);

		Button goToProjectileLauncherButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeProjectileLauncher"));
		vb.getChildren().add(goToProjectileLauncherButton);
		goToProjectileLauncherButton.setOnAction(e -> {
			getView().goForwardFrom(this.getClass().getSimpleName()+"Apply", myObjectName);
		});
		Button backButton = setupBackButton(); 
		vb.getChildren().add(backButton);
		return vb;
	}

	private void makeTowerComponents(VBox vb) {

		HBox towerImageSelect = new HBox();
		ComboBox<String> towerImageDropdown = new ComboBox<String>();
		ImageView imageDisplay = new ImageView(); 
		try {
			towerImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TOWER_IMAGE_PREFIX+getView().getTheme()+TOWER_IMAGE_SUFFIX));
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		myImageDropdown = towerImageDropdown;  
		myImageDropdown.addEventHandler(ActionEvent.ACTION, e -> {
			getView().setObjectAttribute("Tower", myObjectName, "myImage", myImageDropdown.getSelectionModel().getSelectedItem()); 
		});
		try {
			towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", TOWER_IMAGE_PREFIX+getView().getTheme()+TOWER_IMAGE_SUFFIX, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), towerImageDropdown, imageDisplay);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(towerImageSelect);


		Slider towerValueSlider = getUIFactory().setupSlider("TowerValueSlider", getMyMaxPrice());
		myTowerValueSlider = towerValueSlider; 
		HBox towerValue = getUIFactory().setupSliderWithValue("TowerValueSlider", towerValueSlider, getErrorCheckedPrompt("TowerValue"));
		vb.getChildren().add(towerValue);
		myTowerValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myTowerValue", newValue);
		});

	}

	private void makeHealthComponents(VBox vb) {
		Slider towerHealthValueSlider = getUIFactory().setupSlider("TowerHealthValueSlider", getMyMaxPrice());
		myTowerHealthValueSlider = towerHealthValueSlider; 
		HBox towerHealthValue = getUIFactory().setupSliderWithValue("TowerHealthValueSlider", towerHealthValueSlider, getErrorCheckedPrompt("TowerHealthValue"));
		vb.getChildren().add(towerHealthValue);
		myTowerHealthValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthValue", newValue);
		});

		Slider towerHealthUpgradeCostSlider = getUIFactory().setupSlider("TowerHealthUpgradeCostSlider", getMyMaxPrice());
		myTowerHealthUpgradeCostSlider = towerHealthUpgradeCostSlider;
		HBox towerHealthUpgradeCost = getUIFactory().setupSliderWithValue("TowerHealthUpgradeCostSlider", towerHealthUpgradeCostSlider, getErrorCheckedPrompt("TowerHealthUpgradeCost"));
		vb.getChildren().add(towerHealthUpgradeCost);
		myTowerHealthUpgradeCostSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthUpgradeCost", newValue);
		});

		Slider towerHealthUpgradeValueSlider = getUIFactory().setupSlider("TowerHealthUpgradeValueSlider", getMyMaxPrice());
		myTowerHealthUpgradeValueSlider = towerHealthUpgradeValueSlider; 
		HBox towerHealthUpgradeValue = getUIFactory().setupSliderWithValue("TowerHealthUpgradeValueSlider", towerHealthUpgradeValueSlider, getErrorCheckedPrompt("TowerHealthUpgradeValue"));
		vb.getChildren().add(towerHealthUpgradeValue);
		myTowerHealthUpgradeValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthUpgradeValue", newValue);
		});
	}

	protected String getSelectedImage() {
		return myImageDropdown.getValue(); 
	}

	protected double getTowerHealthValue() {
		return myTowerHealthValueSlider.getValue(); 
	}

	protected double getTowerHealthUpgradeCost() {
		return myTowerHealthUpgradeCostSlider.getValue(); 
	}

	protected double getTowerHealthUpgradeValue() {
		return myTowerHealthUpgradeValueSlider.getValue(); 
	}

	protected double getTowerValue() {
		return myTowerValueSlider.getValue(); 
	}

	protected double getTowerUpgradeCost() {
		return myTowerUpgradeCostSlider.getValue(); 
	}

	protected double getTowerUpgradeValue() {
		return myTowerUpgradeValueSlider.getValue(); 
	}

}