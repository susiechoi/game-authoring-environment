/**
 * @author susiechoi
 */

package authoring.frontend;

public class SpecifyTowerScreen extends SpecifyObjectScreen {
	
	public static final String DEFAULT_DESCRIPTION = "Tower";

	protected SpecifyTowerScreen(AuthoringView view) {
		super(view);
		setDescription(DEFAULT_DESCRIPTION); 
	}

}
