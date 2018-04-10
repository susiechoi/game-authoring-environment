
/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) tower object
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

class AdjustTowerScreen extends AdjustNewOrExistingScreen {
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";

	private TextField myNameField;
	private ComboBox<String> myImageDropdown;
	private ImageView myImageDisplay; 
	private Slider myTowerHealthValueSlider;
	private Slider myTowerHealthUpgradeCostSlider;
	private Slider myTowerHealthUpgradeValueSlider;
	private Slider myTowerValueSlider;
	private Slider myTowerUpgradeCostSlider;
	private Slider myTowerUpgradeValueSlider; 
	private ComboBox<String> myProjectileImage;
	private double myProjectileDamage;
	private double myProjectileUpgradeCost;
	private double myProjectileUpgradeValue;
	private double myProjectileSpeed; 
	private double myLauncherUpgradeCost;
	private double myLauncherValue;
	private double myLauncherUpgradeValue;
	private double myLauncherSpeed;
	private double myLauncherRange; 

	protected AdjustTowerScreen(AuthoringView view, String selectedObjectName) {
		super(view, selectedObjectName);
		myProjectileImage = new ComboBox<String>();
		myProjectileDamage = 0.0; 
		myProjectileUpgradeCost = 0.0;
		myProjectileUpgradeValue = 0.0;
		myProjectileSpeed = 0.0; 
		myLauncherValue = 0.0;
		myLauncherUpgradeCost = 0.0; 
		myLauncherUpgradeValue = 0.0;
		myLauncherSpeed = 0.0;
		myLauncherRange = 0.0;
	}

	@Override
	public Parent populateScreenWithFields() {		
		VBox vb = new VBox(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeTower")));

		makeTowerComponents(vb);
		makeHealthComponents(vb);

		Button goToProjectileLauncherButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeProjectileLauncher"));
		vb.getChildren().add(goToProjectileLauncherButton);
		goToProjectileLauncherButton.setOnAction(e -> {
			getView().getScene().setRoot(new HBox());
			if(getIsNewObject()) {
				getView().loadScreen(new AdjustLauncherProjectileScreen(getView(), this, myNameField.getText()));
			}
			else {
				getView().loadScreen(new AdjustLauncherProjectileScreen(getView(), this, myNameField.getText())); 
			}
		});

		Button backButton = setupBackButton();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			try {
				if (myNameField.getText().equals(getMyDefaultName())) {
					myNameField.setText(getView().getErrorCheckedPrompt("ReplaceDefaultName"));
				}
				else {
					getView().makeTower(getIsNewObject(), myNameField.getText(), myImageDropdown.getValue(), 
							myTowerHealthValueSlider.getValue(),  myTowerHealthUpgradeCostSlider.getValue(),  myTowerHealthUpgradeValueSlider.getValue(), 
							myProjectileImage.getValue(), myProjectileDamage, myProjectileUpgradeCost, myProjectileUpgradeValue, myProjectileSpeed, 
							myLauncherValue, myLauncherUpgradeCost, myLauncherUpgradeValue, myLauncherSpeed, myLauncherRange,
							myTowerValueSlider.getValue(), myTowerUpgradeCostSlider.getValue(), myTowerUpgradeValueSlider.getValue());
					getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
				}
			} catch (NoDuplicateNamesException e1) {
				getView().loadErrorScreen("NoDuplicateNames");
			}
		});

		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);

		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);

		return sp;
	}

	protected void populateFieldsWithData() {
		myNameField.setText(getMySelectedObjectName());

		setEditableOrNot(myNameField, getIsNewObject());

		//		getUIFactory().setComboBoxToValue(myImageDropdown,getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myImage")); 

		getUIFactory().setSliderToValue(myTowerHealthValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthValue"));

		getUIFactory().setSliderToValue(myTowerHealthUpgradeCostSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthUpgradeCost"));

		getUIFactory().setSliderToValue(myTowerHealthUpgradeValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthUpgradeValue"));
		
		getUIFactory().setSliderToValue(myTowerValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myTowerValue"));

//		getUIFactory().setSliderToValue(myTowerUpgradeCostSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myUpgradeCost"));
//
//		getUIFactory().setSliderToValue(myTowerUpgradeValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myUpgradeValue"));

	}

	private void makeTowerComponents(VBox vb) {
		TextField nameInputField = getUIFactory().makeTextField(""); 
		myNameField = nameInputField; 

		HBox towerNameSelect = new HBox(); 
		towerNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("TowerName"));
		vb.getChildren().add(towerNameSelect);

		HBox towerImageSelect = new HBox();
		ComboBox<String> towerImageDropdown;
		myImageDisplay = new ImageView(); 
		try {
			towerImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TOWER_IMAGES));
			myImageDropdown = towerImageDropdown; 
			towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Tower") + " " , TOWER_IMAGES, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), towerImageDropdown, myImageDisplay);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(towerImageSelect);
		
		Slider towerValueSlider = getUIFactory().setupSlider("TowerValueSlider", getMyMaxPrice());
		myTowerValueSlider = towerValueSlider; 
		HBox towerValue = getUIFactory().setupSliderWithValue("TowerValueSlider", towerValueSlider, getErrorCheckedPrompt("TowerValue"));
		vb.getChildren().add(towerValue);

		Slider towerUpgradeCostSlider = getUIFactory().setupSlider("TowerUpgradeCostSlider", getMyMaxPrice());
		myTowerUpgradeCostSlider = towerUpgradeCostSlider;
		HBox towerUpgradeCost = getUIFactory().setupSliderWithValue("TowerUpgradeCostSlider", towerUpgradeCostSlider, getErrorCheckedPrompt("TowerUpgradeCost"));
		vb.getChildren().add(towerUpgradeCost);

		Slider towerUpgradeValueSlider = getUIFactory().setupSlider("TowerUpgradeValueSlider", getMyMaxPrice());
		myTowerUpgradeValueSlider = towerUpgradeValueSlider; 
		HBox towerUpgradeValue = getUIFactory().setupSliderWithValue("TowerUpgradeValueSlider", towerUpgradeValueSlider, getErrorCheckedPrompt("TowerUpgradeValue"));
		vb.getChildren().add(towerUpgradeValue);
	}

	private void makeHealthComponents(VBox vb) {
		Slider towerHealthValueSlider = getUIFactory().setupSlider("TowerHealthValueSlider", getMyMaxPrice());
		myTowerHealthValueSlider = towerHealthValueSlider; 
		HBox towerHealthValue = getUIFactory().setupSliderWithValue("TowerHealthValueSlider", towerHealthValueSlider, getErrorCheckedPrompt("TowerHealthValue"));
		vb.getChildren().add(towerHealthValue);

		Slider towerHealthUpgradeCostSlider = getUIFactory().setupSlider("TowerHealthUpgradeCostSlider", getMyMaxPrice());
		myTowerHealthUpgradeCostSlider = towerHealthUpgradeCostSlider;
		HBox towerHealthUpgradeCost = getUIFactory().setupSliderWithValue("TowerHealthUpgradeCostSlider", towerHealthUpgradeCostSlider, getErrorCheckedPrompt("TowerHealthUpgradeCost"));
		vb.getChildren().add(towerHealthUpgradeCost);

		Slider towerHealthUpgradeValueSlider = getUIFactory().setupSlider("TowerHealthUpgradeValueSlider", getMyMaxPrice());
		myTowerHealthUpgradeValueSlider = towerHealthUpgradeValueSlider; 
		HBox towerHealthUpgradeValue = getUIFactory().setupSliderWithValue("TowerHealthUpgradeValueSlider", towerHealthUpgradeValueSlider, getErrorCheckedPrompt("TowerHealthUpgradeValue"));
		vb.getChildren().add(towerHealthUpgradeValue);
	}

	protected void setLauncherProjectileValues(ComboBox<String> projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue, double projectileSpeed, 
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		myProjectileImage = projectileImage;
		myProjectileDamage = projectileDamage; 
		myProjectileUpgradeCost = projectileUpgradeCost;
		myProjectileUpgradeValue = projectileUpgradeValue;
		myProjectileSpeed = projectileSpeed; 
		myLauncherValue = launcherValue;
		myLauncherUpgradeCost = launcherUpgradeCost; 
		myLauncherUpgradeValue = launcherUpgradeValue;
		myLauncherSpeed = launcherSpeed;
		myLauncherRange = launcherRange; 
	}

}