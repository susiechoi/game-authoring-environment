/**
 * @author susiechoi
 * Provides necessary information (object description) to populate screen that allows user to choose whether to
 * create a new enemy or edit an existing one
 */

package authoring.frontend;

import javafx.scene.Parent;

class SpecifyEnemyScreen extends SpecifyObjectScreen {
	
	public static final String DEFAULT_DESCRIPTION = "Enemy";

	protected SpecifyEnemyScreen(AuthoringView view) {
		super(view);
		setDescription(DEFAULT_DESCRIPTION); 
	}

	@Override
	protected Parent populateScreenWithFields() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	protected void populateFieldsWithData() {
	    // TODO Auto-generated method stub
	    
	}

}