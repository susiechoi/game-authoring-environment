/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing 
 * (new or existing, depending on whether corresponding tower is new or existing) launcher/projectile object
 */

package authoring.frontend;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustLauncherProjectileScreen extends AdjustNewOrExistingScreen {

    public static final String OBJECT_TYPE = "Tower";
    public static final String PROJECTILE_TYPE = "Projectile";
    public static final String LAUNCHER_TYPE = "Launcher";
    public static final String PROJECTILE_IMAGE_PREFIX = "images/ThemeSpecificImages/ProjectileImages/";
    public static final String PROJECTILE_IMAGE_SUFFIX = "ProjectileImageNames.properties";
    public static final String PROJECTILE_FIELDS = "default_objects/ProjectileFields.properties";

    private String myObjectName; 
    private Slider myProjectileDamageSlider;
    private Slider myProjectileSpeedSlider; 
    private Slider myLauncherRateSlider;
    private Slider myLauncherRangeSlider;
    private Slider myProjectileSizeSlider; 
    private Object myProjectileDamage;
    private Object myProjectileSpeed;
    private Object myLauncherRate;
    private Object myLauncherRange;

    protected AdjustLauncherProjectileScreen(AuthoringView view, String selectedObjectName) {
	super(view, selectedObjectName, PROJECTILE_FIELDS, OBJECT_TYPE);
	myObjectName = selectedObjectName; 
    }

    @Override
    protected Parent populateScreenWithFields() {
	VBox vb = new VBox(); 
	vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeProjectileLauncher")));

	makeProjectileComponents(vb);
	makeLauncherComponents(vb);

	Button backButton = setupBackButtonCustom(e-> {
	    getView().goForwardFrom(this.getClass().getSimpleName()+"Back", getMySelectedObjectName());
	}); 
	Button applyButton = getUIFactory().setupApplyButton();
	applyButton.setOnAction(e -> {
	    setProperty(PROJECTILE_TYPE, "ConstantDamageProperty", myProjectileDamage);
	    setProperty(PROJECTILE_TYPE, "ConstantSpeedProperty", myProjectileSpeed);
	    setProperty(LAUNCHER_TYPE, "RangeProperty", myLauncherRange);
	    setProperty(LAUNCHER_TYPE, "FireRateProperty", myLauncherRate);
	    getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
	});
	HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
	vb.getChildren().add(backAndApplyButton);
	return vb;
    }

    private void makeProjectileComponents(VBox vb) {
	HBox projectileImageSelect = makeImageSelector("Tower", "Projectile", PROJECTILE_IMAGE_PREFIX+getView().getTheme()+PROJECTILE_IMAGE_SUFFIX);
	vb.getChildren().add(projectileImageSelect);
	
	Slider myProjectileDamageSlider = getUIFactory().setupSlider("ProjectileDamageSlider", getMyMaxRange());
	HBox projectileDamage = getUIFactory().setupSliderWithValue("ProjectileDamageSlider", myProjectileDamageSlider, getErrorCheckedPrompt("ProjectileDamage"));
	vb.getChildren().add(projectileDamage);
	myProjectileDamageSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myProjectileDamage = newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myProjectileDamage", newValue);
	});

	Slider myProjectileSizeSlider = getUIFactory().setupSlider("ProjectileSize", getMyMaxUpgradeIncrement());
	Slider myProjectileSpeedSlider = getUIFactory().setupSlider("ProjectileSpeed", getMyMaxUpgradeIncrement());
	HBox projectileSpeed = getUIFactory().setupSliderWithValue("ProjectileSpeed", myProjectileSpeedSlider, getErrorCheckedPrompt("ProjectileUpgradeValue"));
	vb.getChildren().add(projectileSpeed);
	myProjectileSpeedSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myProjectileSpeed = newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myProjectileSpeed", newValue);
	});
    }

    private void makeLauncherComponents(VBox vb) {

	Slider myLauncherRateSlider = getUIFactory().setupSlider("LauncherRateSlider", getMyMaxSpeed());
	HBox launcherRate = getUIFactory().setupSliderWithValue("LauncherRateSlider", myLauncherRateSlider, getErrorCheckedPrompt("LauncherRate"));
	vb.getChildren().add(launcherRate);
	myLauncherRateSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myLauncherRate = newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myLauncherRate", newValue);
	});

	Slider myLauncherRangeSlider = getUIFactory().setupSlider("LauncherRangeSlider", getMyMaxRange());
	HBox launcherRange = getUIFactory().setupSliderWithValue("LauncherRangeSlider", myLauncherRangeSlider, getErrorCheckedPrompt("LauncherRange"));
	vb.getChildren().add(launcherRange);
	myLauncherRangeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myLauncherRange = newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myLauncherRange", newValue);
	});
    }

    private void setProperty(String objectType, String propertyName, Object ...args) {
	List<Object> attributes = makeList(args);
	getView().setObjectAttributes(objectType, myObjectName, propertyName, attributes);
    }

    private List<Object> makeList(Object ...attributes) {
	List<Object> list = new ArrayList<>();
	for(Object attribute : attributes) {
	    list.add(attribute);
	}
	return list;
    }

}
