/**
 * @author susiechoi
 * Creates Screen in which user can customize a new tower
 */

package authoring.frontend;

public class AdjustNewTowerScreen extends AdjustTowerScreen {

	protected AdjustNewTowerScreen(AuthoringView view) {
		super(view);
		setIsNewObject(true);
	}

	/**
	 * In the case that the user is making a new tower,
	 * the data fields on the AdjustTowerScreen need not be prepopulated. 
	 * 
	 * Null object design pattern: takes away the need for the instantiated AdjustTowerScreen to 
	 * hold logic such as 'if (editingExistingTower)'
	 */
	@Override
	protected void populateFieldsWithData() {
		// DO NOTHING
	}

}
