/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) tower object
 */

package authoring.frontend;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> 233e5c6d3272fea39341cec37b2b0c259c9abc12
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


class AdjustTowerScreen extends AdjustNewOrExistingScreen {

<<<<<<< HEAD
    public static final String OBJECT_TYPE = "Tower";
    public static final String TOWER_IMAGE_PREFIX = "images/ThemeSpecificImages/TowerImages/";
    public static final String TOWER_IMAGE_SUFFIX = "TowerImageNames.properties";
    public static final String TOWER_FIELDS = "default_objects/TowerFields.properties";
    public static final String DEFAULT_PROJECTILE_IMAGE = "Bullet";
    Slider myTowerHealthUpgradeValueSlider;
    Slider myTowerHealthUpgradeCostSlider;
    Slider myTowerHealthValueSlider;
    Slider myTowerValueSlider;
   
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
	    setProperty("HealthProperty", myHealthUpgradeCost, myHealthUpgradeValue, myHealthValue);
	    setProperty("ValueProperty", myTowerValue);
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

    private void setProperty(String propertyName, Object ...args) {
	List<Object> attributes = makeList(args);
	getView().setObjectAttributes(OBJECT_TYPE, myObjectName, propertyName, attributes);
    }

    private List<Object> makeList(Object ...attributes) {
	List<Object> list = new ArrayList<Object>();
	for(Object attribute : attributes) {
	    list.add(attribute);
=======
	public static final String OBJECT_TYPE = "Tower";
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String TOWER_IMAGE_PREFIX = "images/ThemeSpecificImages/TowerImages/";
	public static final String TOWER_IMAGE_SUFFIX = "TowerImageNames.properties";
	static final String TOWER_FIELDS = "default_objects/TowerFields.properties";
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

		HBox towerImageSelect = makeImageSelector("Tower","", TOWER_IMAGE_PREFIX + getView().getTheme() + TOWER_IMAGE_SUFFIX);
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
>>>>>>> 233e5c6d3272fea39341cec37b2b0c259c9abc12
	}
	return list;
    }
}