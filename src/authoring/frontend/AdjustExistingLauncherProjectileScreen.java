package authoring.frontend;

public class AdjustExistingLauncherProjectileScreen extends AdjustLauncherProjectileScreen {

	private String mySelectedObjectName; 
	
	protected AdjustExistingLauncherProjectileScreen(AuthoringView view, AdjustTowerScreen towerScreen, String selectedObjectName) {
		super(view, towerScreen);
		setIsNewObject(false);
		mySelectedObjectName = selectedObjectName; 
	}

	@Override
	protected void populateFieldsWithData() {

		setComboBoxToValue(getMyProjectileImage(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myImage")); 

		setSliderToValue(getMyProjectileDamageSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myProjectileDamage"));

		setSliderToValue(getMyProjectileValueSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myProjectileValue"));

		setSliderToValue(getMyProjectileUpgradeCostSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myProjectileUgradeCost"));

		setSliderToValue(getMyProjectileUpgradeValueSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myProjectileUpgradeValue"));

		setSliderToValue(getMyLauncherValueSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myLauncherValue"));

		setSliderToValue(getMyLauncherUpgradeCostSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myLauncherUpgradeCost"));

		setSliderToValue(getMyLauncherUpgradeValueSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myLauncherUgradeValue"));

		setSliderToValue(getMyLauncherRateSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myLauncherRate"));

		setSliderToValue(getMyLauncherRangeSlider(), getView().getObjectAttribute("Tower", mySelectedObjectName, "myLauncherRange"));
		
	}

}
