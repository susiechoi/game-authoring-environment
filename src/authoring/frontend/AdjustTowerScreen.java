/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) tower object
 */

package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


class AdjustTowerScreen extends AdjustNewOrExistingScreen {

    public static final String OBJECT_TYPE = "Tower";
    public static final String TOWER_IMAGE_PREFIX = "images/ThemeSpecificImages/TowerImages/";
    public static final String TOWER_IMAGE_SUFFIX = "TowerImageNames.properties";
    public static final String TOWER_FIELDS = "default_objects/TowerFields.properties";
    public static final String DEFAULT_PROJECTILE_IMAGE = "Bullet";
    private Slider myTowerHealthUpgradeValueSlider;
    private Slider myTowerHealthUpgradeCostSlider;
    private Slider myTowerHealthValueSlider;
    private Slider myTowerValueSlider;
    
    private String myObjectName; 
    private Object myHealthUpgradeValue;
    private Object myHealthUpgradeCost;
    private Object myHealthValue;
    private Object myTowerValue;


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
	    setProperty(OBJECT_TYPE, myObjectName, "HealthProperty", myHealthUpgradeCost, myHealthUpgradeValue, myHealthValue);
	    setProperty(OBJECT_TYPE, myObjectName, "ValueProperty", myTowerValue);
	    getView().goForwardFrom(this.getClass().getSimpleName()+"Apply", myObjectName);
	});
	Button backButton = setupBackButton(); 
	vb.getChildren().add(backButton);
	return vb;
    }

    private void makeTowerComponents(VBox vb) {

	HBox towerImageSelect = makeImageSelector("Tower","", TOWER_IMAGE_PREFIX + getView().getTheme() + TOWER_IMAGE_SUFFIX);
	vb.getChildren().add(towerImageSelect);


	myTowerValueSlider = getUIFactory().setupSlider("TowerValueSlider", getMyMaxPrice());
	HBox towerValue = getUIFactory().setupSliderWithValue("TowerValueSlider", myTowerValueSlider, getErrorCheckedPrompt("TowerValue"));
	vb.getChildren().add(towerValue);
	myTowerValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myTowerValue = newValue;
	    //    getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myTowerValue", newValue);
	});

    }

    private void makeHealthComponents(VBox vb) {
	myTowerHealthValueSlider = getUIFactory().setupSlider("TowerHealthValueSlider", getMyMaxPrice());
	HBox towerHealthValue = getUIFactory().setupSliderWithValue("TowerHealthValueSlider", myTowerHealthValueSlider, getErrorCheckedPrompt("TowerHealthValue"));
	vb.getChildren().add(towerHealthValue);
	myTowerHealthValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myHealthValue = newValue;
	    //    getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthValue", newValue);
	});

	myTowerHealthUpgradeCostSlider = getUIFactory().setupSlider("TowerHealthUpgradeCostSlider", getMyMaxPrice());
	HBox towerHealthUpgradeCost = getUIFactory().setupSliderWithValue("TowerHealthUpgradeCostSlider", myTowerHealthUpgradeCostSlider, getErrorCheckedPrompt("TowerHealthUpgradeCost"));
	vb.getChildren().add(towerHealthUpgradeCost);
	myTowerHealthUpgradeCostSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myHealthUpgradeCost = newValue;
	    //    getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthUpgradeCost", newValue);
	});

	myTowerHealthUpgradeValueSlider = getUIFactory().setupSlider("TowerHealthUpgradeValueSlider", getMyMaxPrice());
	HBox towerHealthUpgradeValue = getUIFactory().setupSliderWithValue("TowerHealthUpgradeValueSlider", myTowerHealthUpgradeValueSlider, getErrorCheckedPrompt("TowerHealthUpgradeValue"));
	vb.getChildren().add(towerHealthUpgradeValue);
	myTowerHealthUpgradeValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myHealthUpgradeValue = newValue;
	    //    getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthUpgradeValue", newValue);
	});
    }
}