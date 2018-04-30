/**
 * @author susiechoi
 * @author Katherine Van Dyk
 * @author benauriemma
 * Abstract class for developing the fields for customizing 
 * (new or existing, depending on whether corresponding tower is new or existing) launcher/projectile object
 */

package authoring.frontend;

import com.sun.javafx.tools.packager.Log;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustLauncherProjectileScreen extends AdjustNewOrExistingScreen {	
    public static final String PROJECTILE_OBJECT_TYPE = "Projectile";
    public static final String LAUNCHER_OBJECT_TYPE = "Launcher";
    public static final String PROJECTILE_IMAGE_PREFIX = "images/ThemeSpecificImages/ProjectileImages/";
    public static final String PROJECTILE_IMAGE_SUFFIX = "ProjectileImageNames.properties";
    public static final String PROJECTILE_FIELDS = "default_objects/ProjectileFields.properties";
    public static final String DEFAULT_APPLYBUTTON_SCREENFLOW = "Apply";
    public static final String DEFAULT_BACKBUTTON_SCREENFLOW = "Back";

    private String myObjectName; 


    //    private Slider myProjectileDamageSlider;
    //    private Slider myProjectileSpeedSlider; 
    //    private Slider myLauncherRateSlider;
    //    private Slider myLauncherRangeSlider;
    //    private Slider myProjectileSizeSlider;
    // TODO: add something for the sound -bma
    private Double myProjectileDamage;
    private Double myProjectileSpeed;
    private Double myLauncherRate;
    private Double myLauncherRange;



    protected AdjustLauncherProjectileScreen(AuthoringView view, String selectedObjectName) {
	super(view, selectedObjectName, PROJECTILE_FIELDS, PROJECTILE_OBJECT_TYPE);
	myObjectName = selectedObjectName; 
    }

    @Override
    protected Parent populateScreenWithFields() {
	VBox vb = new VBox(); 
	vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeProjectileLauncher")));

	makeProjectileComponents(vb);
	makeLauncherComponents(vb);

	//	Button backButton = setupBackButtonCustom(e-> {
	//	    getView().goForwardFrom(this.getClass().getSimpleName()+DEFAULT_BACKBUTTON_SCREENFLOW, getMySelectedObjectName());
	//	}); 
	Button applyButton = getUIFactory().setupApplyButton();
	applyButton.setOnAction(e -> {
	    try {
		setProperty(PROJECTILE_OBJECT_TYPE, myObjectName, "DamageProperty", 0.0, 0.0, myProjectileDamage);
		setProperty(PROJECTILE_OBJECT_TYPE, myObjectName, "ConstantSpeedProperty", myProjectileSpeed);
		setProperty(PROJECTILE_OBJECT_TYPE, myObjectName, "RangeProperty", myLauncherRange);
		setProperty(PROJECTILE_OBJECT_TYPE, myObjectName, "FireRateProperty", 0.0, 0.0, myLauncherRate);
		setSaved();
		getView().goForwardFrom(this.getClass().getSimpleName()+DEFAULT_APPLYBUTTON_SCREENFLOW);
	    }
	    catch(NullPointerException e1) {
		getView().loadErrorAlert("NoSelection");
	    }
	});
	vb.getChildren().add(makePropertySelector());
	//	HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
	vb.getChildren().add(applyButton);
	return vb;
    }

    private void makeProjectileComponents(VBox vb) {
	HBox selectors = new HBox();

	HBox projectileImageSelect = makeImageSelector(PROJECTILE_OBJECT_TYPE, "", PROJECTILE_IMAGE_PREFIX+getView().getTheme()+PROJECTILE_IMAGE_SUFFIX);
	selectors.getChildren().add(projectileImageSelect);



	String soundPropertiesFilePath = "src/sound/resources/soundFiles.properties";
	ComboBox<String> soundDropdown;
	try {
	    soundDropdown = this.getUIFactory().makeTextDropdown(this.getPropertiesReader().allKeys(soundPropertiesFilePath)); // TODO: this has no prompt, and the action for no choice is null

	    soundDropdown.addEventHandler(ActionEvent.ACTION,e -> {
		    getView().setObjectAttribute("Projectile", myObjectName, "mySound", soundDropdown.getSelectionModel().getSelectedItem()); 
	    });
	    
	    VBox projectileSoundSelect = this.getUIFactory().setupSelector(this.getPropertiesReader(), "", soundPropertiesFilePath, "Load New Sound", "New Sound Name:", ".wav", soundDropdown);
	    selectors.getChildren().add(projectileSoundSelect);
	} catch (MissingPropertiesException e) {
	    e.printStackTrace(); //TODO
	}

	vb.getChildren().add(selectors);

	Slider myProjectileDamageSlider = getUIFactory().setupSlider(getMyMaxRange());
	HBox projectileDamage = getUIFactory().setupSliderWithValue(myProjectileDamageSlider, getErrorCheckedPrompt("ProjectileDamage"));
	vb.getChildren().add(projectileDamage);
	myProjectileDamageSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myProjectileDamage = (Double) newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myProjectileDamage", newValue);
	});

	//	Slider myProjectileSizeSlider = getUIFactory().setupSlider(getMyMaxUpgradeIncrement());
	Slider myProjectileSpeedSlider = getUIFactory().setupSlider(getMyMaxUpgradeIncrement());
	HBox projectileSpeed = getUIFactory().setupSliderWithValue(myProjectileSpeedSlider, getErrorCheckedPrompt("ProjectileUpgradeValue"));
	vb.getChildren().add(projectileSpeed);
	myProjectileSpeedSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myProjectileSpeed = (Double) newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myProjectileSpeed", newValue);
	});
    }

    private void makeLauncherComponents(VBox vb) {

	Slider myLauncherRateSlider = getUIFactory().setupSlider(getMyMaxSpeed());
	HBox launcherRate = getUIFactory().setupSliderWithValue(myLauncherRateSlider, getErrorCheckedPrompt("LauncherRate"));
	vb.getChildren().add(launcherRate);
	myLauncherRateSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myLauncherRate = (Double) newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myLauncherRate", newValue);
	});

	Slider myLauncherRangeSlider = getUIFactory().setupSlider(getMyMaxRange());
	HBox launcherRange = getUIFactory().setupSliderWithValue(myLauncherRangeSlider, getErrorCheckedPrompt("LauncherRange"));
	vb.getChildren().add(launcherRange);
	myLauncherRangeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myLauncherRange = (Double) newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myLauncherRange", newValue);
	});
    }
}
