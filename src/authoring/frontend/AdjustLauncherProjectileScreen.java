/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing 
 * (new or existing, depending on whether corresponding tower is new or existing) launcher/projectile object
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

class AdjustLauncherProjectileScreen extends AdjustNewOrExistingScreen {
	
	public static final String OBJECT_DESCRIPTION = "Tower";
	public static final String PROJECTILE_IMAGES = "images/ProjectileImageNames.properties"; 
	public static final String PROJECTILE_FIELDS = "default_objects/ProjectileFields.properties";
	
	private AdjustTowerScreen myTowerScreen;
	private ComboBox<String> myProjectileImage;
	private ImageView myImageDisplay; 
	private Slider myProjectileDamageSlider;
	private Slider myProjectileSpeedSlider; 
	private Slider myLauncherRateSlider;
	private Slider myLauncherRangeSlider;

	private Slider myProjectileSizeSlider; 

	protected AdjustLauncherProjectileScreen(AuthoringView view, AdjustTowerScreen towerScreen, String selectedObjectName) {
		super(view, selectedObjectName, PROJECTILE_FIELDS, OBJECT_DESCRIPTION);
		myTowerScreen = towerScreen; 
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
			try {	setSaved();
				getView().makeTower(false, getMySelectedObjectName(), myTowerScreen.getSelectedImage(),  
						myTowerScreen.getTowerHealthValue(),  myTowerScreen.getTowerHealthUpgradeCost(),  myTowerScreen.getTowerHealthUpgradeValue(), 
						myProjectileImage.getValue(), myProjectileDamageSlider.getValue(), 0, 0, myProjectileSizeSlider.getValue(), myProjectileSpeedSlider.getValue(), 
						0, 0, 0, myLauncherRateSlider.getValue(), myLauncherRangeSlider.getValue(),
						myTowerScreen.getTowerValue(), myTowerScreen.getTowerUpgradeCost(), myTowerScreen.getTowerUpgradeValue());
			} catch (NoDuplicateNamesException e1) {
				// TODO DO NOTHING
			}
		    getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
		});
		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);
				
		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		
		return sp;
	}
	
	private void makeProjectileComponents(VBox vb) {
		ComboBox<String> projectileImageDropdown;
		HBox projectileImageSelect = new HBox(); 
		myImageDisplay = new ImageView(); 
		try {
			projectileImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(PROJECTILE_IMAGES));
			myProjectileImage = projectileImageDropdown; 
			projectileImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Projectile") + " " , PROJECTILE_IMAGES, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),getErrorCheckedPrompt("NewImageName"), projectileImageDropdown, myImageDisplay);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(projectileImageSelect);

		Slider projectileDamageSlider = getUIFactory().setupSlider("ProjectileDamageSlider", getMyMaxRange());
		myProjectileDamageSlider = projectileDamageSlider; 
		HBox projectileDamage = getUIFactory().setupSliderWithValue("ProjectileDamageSlider", projectileDamageSlider, getErrorCheckedPrompt("ProjectileDamage"));
		vb.getChildren().add(projectileDamage);
		
		Slider projectileSizeSlider = getUIFactory().setupSlider("ProjectileSize", getMyMaxUpgradeIncrement());
		myProjectileSizeSlider = projectileSizeSlider; 
		Slider projectileSpeedSlider = getUIFactory().setupSlider("ProjectileSpeed", getMyMaxUpgradeIncrement());
		myProjectileSpeedSlider = projectileSpeedSlider; 
		HBox projectileSpeed = getUIFactory().setupSliderWithValue("ProjectileSpeed", myProjectileSpeedSlider, getErrorCheckedPrompt("ProjectileUpgradeValue"));
		vb.getChildren().add(projectileSpeed);
	}
	
	private void makeLauncherComponents(VBox vb) {

		Slider launcherRateSlider = getUIFactory().setupSlider("LauncherRateSlider", getMyMaxSpeed());
		myLauncherRateSlider = launcherRateSlider; 
		HBox launcherRate = getUIFactory().setupSliderWithValue("LauncherRateSlider", launcherRateSlider, getErrorCheckedPrompt("LauncherRate"));
		vb.getChildren().add(launcherRate);

		Slider launcherRangeSlider = getUIFactory().setupSlider("LauncherRangeSlider", getMyMaxRange());
		myLauncherRangeSlider = launcherRangeSlider; 
		HBox launcherRange = getUIFactory().setupSliderWithValue("LauncherRangeSlider", launcherRangeSlider, getErrorCheckedPrompt("LauncherRange"));
		vb.getChildren().add(launcherRange);
	}

	@Override
	protected TextField getNameField() {
		return null;
	}
	
	
	
}
