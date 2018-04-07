/**
 * @author susiechoi
 */

package authoring.frontend;

class AdjustExistingEnemyScreen extends AdjustEnemyScreen {

	private String mySelectedObjectName; 
	
	protected AdjustExistingEnemyScreen(AuthoringView view, String selectedObjectName) {
		super(view);
		setIsNewObject(false);
		mySelectedObjectName = selectedObjectName; 
	}

	@Override
	protected void populateFieldsWithData() {
		
		getMyNameField();
		
		setComboBoxToValue(getMyImageDropdown(),getView().getObjectAttribute("Enemy", mySelectedObjectName, "image")); 
		
		setSliderToValue(getMySpeedSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "speed"));
		
		setSliderToValue(getMyHealthImpactSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "healthimpact"));
		
		setSliderToValue(getMy$ImpactSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "$impact"));
		
		setSliderToValue(getMyValueSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "value"));
		
		setSliderToValue(getMyUpgradeCostSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "upgradecost"));
		
		setSliderToValue(getMyUpgradeValueSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "upgradevalue"));

	}

}
