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
			
		getMyNameField().setText(getView().getObjectAttribute("Enemy", mySelectedObjectName, "myName"));
		
		setComboBoxToValue(getMyImageDropdown(),getView().getObjectAttribute("Enemy", mySelectedObjectName, "myImage")); 
		
		setSliderToValue(getMySpeedSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "mySpeed"));
		
		setSliderToValue(getMyHealthImpactSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "myHealthImpact"));
			
		setSliderToValue(getMyValueSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "myKillReward"));
		
		setSliderToValue(getMyUpgradeCostSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "myKillUgradeCost"));
		
		setSliderToValue(getMyUpgradeValueSlider(), getView().getObjectAttribute("Enemy", mySelectedObjectName, "myKillUpgradeValue"));

	}

}
