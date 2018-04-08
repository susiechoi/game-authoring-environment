/**
 * @author susiechoi
 */

package authoring.frontend;

class SpecifyEnemyScreen extends SpecifyObjectScreen {
	
	public static final String DEFAULT_DESCRIPTION = "Enemy";

	protected SpecifyEnemyScreen(AuthoringView view) {
		super(view);
		setDescription(DEFAULT_DESCRIPTION); 
	}

}