/**
 * @author susiechoi
 * Abstract class of Screens where values that need to be communicated to AuthoringModel
 * are changed (i.e. Screens that have some kind of "Apply" button)
 *
 */

package authoring.frontend;

abstract class AdjustScreen extends AuthoringScreen {
		
	public static final String DEFAULT_BACK_IMAGE = "images/back.gif"; 
	
	protected AdjustScreen(AuthoringView view) {
		super(view);
	}
	
	

}
