package authoring.frontend;

import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

abstract class AdjustLauncherProjectileScreen extends AdjustNewOrExistingScreen {
	
	public static final String PROJECTILE_IMAGES = "images/ProjectileImageNames.properties"; 
	
	private AdjustTowerScreen myTowerScreen;
	private ComboBox<String> myProjectileImage;
	private Slider myProjectileDamageSlider;
	private Slider myProjectileValueSlider;
	private Slider myProjectileUpgradeCostSlider;
	private Slider myProjectileUpgradeValueSlider;
	private Slider myLauncherValueSlider;
	private Slider myLauncherUpgradeCostSlider;
	private Slider myLauncherUpgradeValueSlider;
	private Slider myLauncherRateSlider;
	private Slider myLauncherRangeSlider; 

	protected AdjustLauncherProjectileScreen(AuthoringView view, AdjustTowerScreen towerScreen) {
		super(view);
		myTowerScreen = towerScreen; 
	}

	@Override
	protected Parent populateScreenWithFields() {
		VBox vb = new VBox(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeProjectileLauncher")));

		makeProjectileComponents(vb);
		makeLauncherComponents(vb);
		
		Button backButton = getUIFactory().setupBackButton();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			myTowerScreen.setLauncherProjectileValues(myProjectileImage.getValue(), myProjectileDamageSlider.getValue(), myProjectileValueSlider.getValue(), myProjectileUpgradeCostSlider.getValue(), myProjectileUpgradeValueSlider.getValue(), myLauncherValueSlider.getValue(), myLauncherUpgradeCostSlider.getValue(), myLauncherUpgradeValueSlider.getValue(), myLauncherRateSlider.getValue(), myLauncherRangeSlider.getValue());
		});
		HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);
				
		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		
		return sp;
	}
	
	private void makeProjectileComponents(VBox vb) {
		ComboBox<String> projectileImageDropdown;
		HBox projectileImageSelect = new HBox(); 
		try {
			projectileImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(PROJECTILE_IMAGES));
			myProjectileImage = projectileImageDropdown; 
			projectileImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Projectile") + " " , PROJECTILE_IMAGES, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),getErrorCheckedPrompt("NewImageName"), projectileImageDropdown);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(projectileImageSelect);

		Slider projectileDamageSlider = getUIFactory().setupSlider("ProjectileDamageSlider", myMaxRange);
		myProjectileDamageSlider = projectileDamageSlider; 
		HBox projectileDamage = getUIFactory().setupSliderWithValue("ProjectileDamageSlider", projectileDamageSlider, getErrorCheckedPrompt("ProjectileDamage"));
		vb.getChildren().add(projectileDamage);

		Slider projectileValueSlider = getUIFactory().setupSlider("ProjectileValueSlider", myMaxPrice);
		myProjectileValueSlider = projectileValueSlider; 
		HBox projectileValue = getUIFactory().setupSliderWithValue("ProjectileValueSlider", projectileValueSlider, getErrorCheckedPrompt("ProjectileValue"));
		vb.getChildren().add(projectileValue);

		Slider projectileUpgradeCostSlider = getUIFactory().setupSlider("ProjectileUpgradeCostSlider", myMaxPrice);
		myProjectileUpgradeCostSlider = projectileUpgradeCostSlider; 
		HBox projectileUpgradeCost = getUIFactory().setupSliderWithValue("ProjectileUpgradeCostSlider", projectileUpgradeCostSlider, getErrorCheckedPrompt("ProjectileUpgradeCost"));
		vb.getChildren().add(projectileUpgradeCost);

		Slider projectileUpgradeValueSlider = getUIFactory().setupSlider("ProjectileUpgradeValueSlider", myMaxUpgradeIncrement);
		myProjectileUpgradeValueSlider = projectileUpgradeValueSlider; 
		HBox projectileUpgradeValue = getUIFactory().setupSliderWithValue("ProjectileUpgradeValueSlider", projectileUpgradeValueSlider, getErrorCheckedPrompt("ProjectileUpgradeValue"));
		vb.getChildren().add(projectileUpgradeValue);
	}
	
	private void makeLauncherComponents(VBox vb) {
		Slider launcherValueSlider = getUIFactory().setupSlider("LauncherValueSlider", myMaxPrice);
		myLauncherValueSlider = launcherValueSlider; 
		HBox launcherValue = getUIFactory().setupSliderWithValue("LauncherValueSlider", launcherValueSlider, getErrorCheckedPrompt("LauncherValue"));
		vb.getChildren().add(launcherValue);

		Slider launcherUpgradeCostSlider = getUIFactory().setupSlider("LauncherUpgradeCostSlider", myMaxPrice);
		myLauncherUpgradeCostSlider = launcherUpgradeCostSlider; 
		HBox launcherUpgradeCost = getUIFactory().setupSliderWithValue("LauncherUpgradeCostSlider", launcherUpgradeCostSlider, getErrorCheckedPrompt("LauncherUpgradeCost"));
		vb.getChildren().add(launcherUpgradeCost);

		Slider launcherUpgradeValueSlider = getUIFactory().setupSlider("LauncherUpgradeValueSlider", myMaxUpgradeIncrement);
		myLauncherUpgradeValueSlider = launcherUpgradeValueSlider; 
		HBox launcherUpgradeValue = getUIFactory().setupSliderWithValue("LauncherUpgradeValueSlider", launcherUpgradeValueSlider, getErrorCheckedPrompt("LauncherUpgradeValue"));
		vb.getChildren().add(launcherUpgradeValue);

		Slider launcherRateSlider = getUIFactory().setupSlider("LauncherRateSlider", myMaxSpeed);
		myLauncherRateSlider = launcherRateSlider; 
		HBox launcherRate = getUIFactory().setupSliderWithValue("LauncherRateSlider", launcherRateSlider, getErrorCheckedPrompt("LauncherRate"));
		vb.getChildren().add(launcherRate);

		Slider launcherRangeSlider = getUIFactory().setupSlider("LauncherRangeSlider", myMaxRange);
		myLauncherRangeSlider = launcherRangeSlider; 
		HBox launcherRange = getUIFactory().setupSliderWithValue("LauncherRangeSlider", launcherRangeSlider, getErrorCheckedPrompt("LauncherRange"));
		vb.getChildren().add(launcherRange);
	}
	
	protected abstract void populateFieldsWithData(); 
	
	protected ComboBox<String> getMyProjectileImage() {
		return myProjectileImage; 
	}
	
	protected Slider getMyProjectileDamageSlider() {
		return myProjectileDamageSlider; 
	}
	
	protected Slider getMyProjectileValueSlider() {
		return myProjectileValueSlider; 
	}
	
	protected Slider getMyProjectileUpgradeCostSlider() {
		return myProjectileUpgradeCostSlider; 
	}
	
	protected Slider getMyProjectileUpgradeValueSlider() {
		return myProjectileUpgradeValueSlider; 

	}
	
	protected Slider getMyLauncherValueSlider() {
		return myLauncherValueSlider; 

	}
	
	protected Slider getMyLauncherUpgradeCostSlider() {
		return myLauncherUpgradeCostSlider; 

	}
	
	protected Slider getMyLauncherUpgradeValueSlider() {
		return myLauncherUpgradeValueSlider; 

	}
	
	protected Slider getMyLauncherRateSlider() {
		return myLauncherRateSlider; 

	}
	
	protected Slider getMyLauncherRangeSlider() {
		return myLauncherRangeSlider; 

	}
	
}
