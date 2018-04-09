
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
	
	private ImageView myProjectileImage;
	private double myProjectileDamage;
	private double myProjectileUpgradeCost;
	private double myProjectileUpgradeValue;
	private double myLauncherUpgradeCost;
	private double myLauncherValue;
	private double myLauncherUpgradeValue;
	private double myLauncherSpeed;
	private double myLauncherRange; 
	
	protected AdjustTowerScreen(AuthoringView view, String selectedObjectName) {
		super(view, selectedObjectName);
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
		
		Button backButton = setupBackButtonSuperclass();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			try {
				getView().makeTower(getIsNewObject(), myNameField.getText(), myImageDisplay.getImage(), myTowerHealthValueSlider.getValue(),  myTowerHealthUpgradeCostSlider.getValue(),  myTowerHealthUpgradeValueSlider.getValue(), myProjectileImage.getImage(), myProjectileDamage, myProjectileUpgradeCost, myProjectileUpgradeValue, myLauncherValue, myLauncherUpgradeCost, myLauncherUpgradeValue, myLauncherSpeed, myLauncherRange);
			} catch (NoDuplicateNamesException e1) {
				getView().loadErrorScreen("NoDuplicateNames");
			}
			getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
		});
		
		HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);
				
		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		
		return sp;
	}

	protected void populateFieldsWithData() {
		myNameField.setText(getMySelectedObjectName());
		
		setEditableOrNot(myNameField, getIsNewObject());

		getUIFactory().setComboBoxToValue(myImageDropdown,getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myImage")); 

		getUIFactory().setSliderToValue(myTowerHealthValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthValue"));

		getUIFactory().setSliderToValue(myTowerHealthUpgradeCostSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthUpgradeCost"));

		getUIFactory().setSliderToValue(myTowerHealthUpgradeValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthUpgradeValue"));

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
	
	protected void setLauncherProjectileValues(ImageView projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		myProjectileImage = projectileImage;
		myProjectileDamage = projectileDamage; 
		myProjectileUpgradeCost = projectileUpgradeCost;
		myProjectileUpgradeValue = projectileUpgradeValue;
		myLauncherValue = launcherValue;
		myLauncherUpgradeCost = launcherUpgradeCost; 
		myLauncherUpgradeValue = launcherUpgradeValue;
		myLauncherSpeed = launcherSpeed;
		myLauncherRange = launcherRange; 
	}

}