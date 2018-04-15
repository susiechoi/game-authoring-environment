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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustLauncherProjectileScreen extends AdjustNewOrExistingScreen {
	
	public static final String PROJECTILE_IMAGES = "images/ProjectileImageNames.properties"; 
	public static final String TOWER_FIELDS = "default_objects/TowerFields.properties";
	
	private AdjustTowerScreen myTowerScreen;
	private ComboBox<String> myProjectileImage;
	private ImageView myImageDisplay; 
	private Slider myProjectileDamageSlider;
//	private Slider myProjectileValueSlider;
//	private Slider myProjectileUpgradeCostSlider;
//	private Slider myProjectileUpgradeValueSlider;
	private Slider myProjectileSpeedSlider; 
//	private Slider myLauncherValueSlider;
//	private Slider myLauncherUpgradeCostSlider;
//	private Slider myLauncherUpgradeValueSlider;
	private Slider myLauncherRateSlider;
	private Slider myLauncherRangeSlider;

	private Slider myProjectileSizeSlider; 

	protected AdjustLauncherProjectileScreen(AuthoringView view, AdjustTowerScreen towerScreen, String selectedObjectName) {
		super(view, selectedObjectName);
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
			try {
				getView().makeTower(false, getMySelectedObjectName(), myTowerScreen.getSelectedImage(),  
						myTowerScreen.getTowerHealthValue(),  myTowerScreen.getTowerHealthUpgradeCost(),  myTowerScreen.getTowerHealthUpgradeValue(), 
						myProjectileImage.getValue(), myProjectileDamageSlider.getValue(), 0, 0, myProjectileSizeSlider.getValue(), myProjectileSpeedSlider.getValue(), 
						0, 0, 0, myLauncherRateSlider.getValue(), myLauncherRangeSlider.getValue(),
						myTowerScreen.getTowerValue(), myTowerScreen.getTowerUpgradeCost(), myTowerScreen.getTowerUpgradeValue());
			} catch (NoDuplicateNamesException e1) {
				// TODO DO NOTHING
			}
//			myTowerScreen.setLauncherProjectileValues(myProjectileImage, myProjectileDamageSlider.getValue(), 0, 0, 0, myProjectileSpeedSlider.getValue(), 0, 0, 0, myLauncherRateSlider.getValue(), myLauncherRangeSlider.getValue());
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
//
//		Slider projectileValueSlider = getUIFactory().setupSlider("ProjectileValueSlider", getMyMaxPrice());
//		myProjectileValueSlider = projectileValueSlider; 
//		HBox projectileValue = getUIFactory().setupSliderWithValue("ProjectileValueSlider", projectileValueSlider, getErrorCheckedPrompt("ProjectileValue"));
//		vb.getChildren().add(projectileValue);
//
//		Slider projectileUpgradeCostSlider = getUIFactory().setupSlider("ProjectileUpgradeCostSlider", getMyMaxPrice());
//		myProjectileUpgradeCostSlider = projectileUpgradeCostSlider; 
//		HBox projectileUpgradeCost = getUIFactory().setupSliderWithValue("ProjectileUpgradeCostSlider", projectileUpgradeCostSlider, getErrorCheckedPrompt("ProjectileUpgradeCost"));
//		vb.getChildren().add(projectileUpgradeCost);
//
//		Slider projectileUpgradeValueSlider = getUIFactory().setupSlider("ProjectileUpgradeValueSlider", getMyMaxUpgradeIncrement());
//		myProjectileUpgradeValueSlider = projectileUpgradeValueSlider; 
//		HBox projectileUpgradeValue = getUIFactory().setupSliderWithValue("ProjectileUpgradeValueSlider", projectileUpgradeValueSlider, getErrorCheckedPrompt("ProjectileUpgradeValue"));
//		vb.getChildren().add(projectileUpgradeValue);
		
		Slider projectileSizeSlider = getUIFactory().setupSlider("ProjectileSize", getMyMaxUpgradeIncrement());
		myProjectileSizeSlider = projectileSizeSlider; 
		Slider projectileSpeedSlider = getUIFactory().setupSlider("ProjectileSpeed", getMyMaxUpgradeIncrement());
		myProjectileSpeedSlider = projectileSpeedSlider; 
		HBox projectileSpeed = getUIFactory().setupSliderWithValue("ProjectileSpeed", myProjectileSpeedSlider, getErrorCheckedPrompt("ProjectileUpgradeValue"));
		vb.getChildren().add(projectileSpeed);
	}
	
	private void makeLauncherComponents(VBox vb) {
//		Slider launcherValueSlider = getUIFactory().setupSlider("LauncherValueSlider", getMyMaxPrice());
//		myLauncherValueSlider = launcherValueSlider; 
//		HBox launcherValue = getUIFactory().setupSliderWithValue("LauncherValueSlider", launcherValueSlider, getErrorCheckedPrompt("LauncherValue"));
//		vb.getChildren().add(launcherValue);
//
//		Slider launcherUpgradeCostSlider = getUIFactory().setupSlider("LauncherUpgradeCostSlider", getMyMaxPrice());
//		myLauncherUpgradeCostSlider = launcherUpgradeCostSlider; 
//		HBox launcherUpgradeCost = getUIFactory().setupSliderWithValue("LauncherUpgradeCostSlider", launcherUpgradeCostSlider, getErrorCheckedPrompt("LauncherUpgradeCost"));
//		vb.getChildren().add(launcherUpgradeCost);
//
//		Slider launcherUpgradeValueSlider = getUIFactory().setupSlider("LauncherUpgradeValueSlider", getMyMaxUpgradeIncrement());
//		myLauncherUpgradeValueSlider = launcherUpgradeValueSlider; 
//		HBox launcherUpgradeValue = getUIFactory().setupSliderWithValue("LauncherUpgradeValueSlider", launcherUpgradeValueSlider, getErrorCheckedPrompt("LauncherUpgradeValue"));
//		vb.getChildren().add(launcherUpgradeValue);

		Slider launcherRateSlider = getUIFactory().setupSlider("LauncherRateSlider", getMyMaxSpeed());
		myLauncherRateSlider = launcherRateSlider; 
		HBox launcherRate = getUIFactory().setupSliderWithValue("LauncherRateSlider", launcherRateSlider, getErrorCheckedPrompt("LauncherRate"));
		vb.getChildren().add(launcherRate);

		Slider launcherRangeSlider = getUIFactory().setupSlider("LauncherRangeSlider", getMyMaxRange());
		myLauncherRangeSlider = launcherRangeSlider; 
		HBox launcherRange = getUIFactory().setupSliderWithValue("LauncherRangeSlider", launcherRangeSlider, getErrorCheckedPrompt("LauncherRange"));
		vb.getChildren().add(launcherRange);
	}
	
	protected void populateFieldsWithData() {

		getUIFactory().setSliderToValue(myProjectileDamageSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myProjectileDamage"));

		getUIFactory().setSliderToValue(myProjectileSpeedSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myProjectileSpeed"));

		getUIFactory().setSliderToValue(myProjectileSizeSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myProjectileSize"));
		
//		getUIFactory().setSliderToValue(myProjectileValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myProjectileValue"));
//
//		getUIFactory().setSliderToValue(myProjectileUpgradeCostSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myProjectileUgradeCost"));
//
//		getUIFactory().setSliderToValue(myProjectileUpgradeValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myProjectileUpgradeValue"));
//
//		getUIFactory().setSliderToValue(myLauncherValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myLauncherValue"));
//
//		getUIFactory().setSliderToValue(myLauncherUpgradeCostSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myLauncherUpgradeCost"));
//
//		getUIFactory().setSliderToValue(myLauncherUpgradeValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myLauncherUgradeValue"));

		getUIFactory().setSliderToValue(myLauncherRateSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myLauncherRate"));

		getUIFactory().setSliderToValue(myLauncherRangeSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myLauncherRange"));
	}
	
	
	
}
