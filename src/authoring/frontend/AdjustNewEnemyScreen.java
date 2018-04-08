/**
 * @author susiechoi
 */

package authoring.frontend;

class AdjustNewEnemyScreen extends AdjustEnemyScreen {

	protected AdjustNewEnemyScreen(AuthoringView view) {
		super(view);
		setIsNewObject(true);
	}

	/**
	 * In the case that the user is making a new enemy,
	 * the data fields on the AdjustEnemyScreen need not be prepopulated. 
	 * 
	 * Null object design pattern: takes away the need for the instantiated AdjustEnemyScreen to 
	 * hold logic such as 'if (editingExistingEnemy)'
	 *
	 */
	protected void populateFieldsWithData() {
		// DO NOTHING
	}

}
