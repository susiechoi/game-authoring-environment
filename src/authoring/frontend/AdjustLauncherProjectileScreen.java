/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing 
 * (new or existing, depending on whether corresponding tower is new or existing) launcher/projectile object
 */

package authoring.frontend;


import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustLauncherProjectileScreen extends AdjustNewOrExistingScreen {
	
	public static final String OBJECT_TYPE = "Tower";
	public static final String PROJECTILE_IMAGE_PREFIX = "images/ThemeSpecificImages/ProjectileImages/";
	public static final String PROJECTILE_IMAGE_SUFFIX = "ProjectileImageNames.properties";
	public static final String PROJECTILE_FIELDS = "default_objects/ProjectileFields.properties";
	
	private String myObjectName; 
	private Slider myProjectileDamageSlider;
	private Slider myProjectileSpeedSlider; 
	private Slider myLauncherRateSlider;
	private Slider myLauncherRangeSlider;
	private Slider myProjectileSizeSlider; 

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
		    getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
		});
		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);
		return vb;
	}
	
	private void makeProjectileComponents(VBox vb) {
		HBox projectileImageSelect = makeImageSelector("Tower", "Projectile", PROJECTILE_IMAGE_PREFIX+getView().getTheme()+PROJECTILE_IMAGE_SUFFIX);
		vb.getChildren().add(projectileImageSelect);

		Slider projectileDamageSlider = getUIFactory().setupSlider("ProjectileDamageSlider", getMyMaxRange());
		myProjectileDamageSlider = projectileDamageSlider; 
		HBox projectileDamage = getUIFactory().setupSliderWithValue("ProjectileDamageSlider", projectileDamageSlider, getErrorCheckedPrompt("ProjectileDamage"));
		vb.getChildren().add(projectileDamage);
		myProjectileDamageSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myProjectileDamage", newValue);
		});
		
		Slider projectileSizeSlider = getUIFactory().setupSlider("ProjectileSize", getMyMaxUpgradeIncrement());
		myProjectileSizeSlider = projectileSizeSlider; 
		Slider projectileSpeedSlider = getUIFactory().setupSlider("ProjectileSpeed", getMyMaxUpgradeIncrement());
		myProjectileSpeedSlider = projectileSpeedSlider; 
		HBox projectileSpeed = getUIFactory().setupSliderWithValue("ProjectileSpeed", myProjectileSpeedSlider, getErrorCheckedPrompt("ProjectileUpgradeValue"));
		vb.getChildren().add(projectileSpeed);
		myProjectileSpeedSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myProjectileSpeed", newValue);
		});
	}
	
	private void makeLauncherComponents(VBox vb) {

		Slider launcherRateSlider = getUIFactory().setupSlider("LauncherRateSlider", getMyMaxSpeed());
		myLauncherRateSlider = launcherRateSlider; 
		HBox launcherRate = getUIFactory().setupSliderWithValue("LauncherRateSlider", launcherRateSlider, getErrorCheckedPrompt("LauncherRate"));
		vb.getChildren().add(launcherRate);
		myLauncherRateSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myLauncherRate", newValue);
		});

		Slider launcherRangeSlider = getUIFactory().setupSlider("LauncherRangeSlider", getMyMaxRange());
		myLauncherRangeSlider = launcherRangeSlider; 
		HBox launcherRange = getUIFactory().setupSliderWithValue("LauncherRangeSlider", launcherRangeSlider, getErrorCheckedPrompt("LauncherRange"));
		vb.getChildren().add(launcherRange);
		myLauncherRangeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
			getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myLauncherRange", newValue);
		});
	}	
	
}
