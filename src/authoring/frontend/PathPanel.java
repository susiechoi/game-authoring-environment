package authoring.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * Abstract class representing Panel classes for use on PathScreens (rightmost Panel of
 * PathScreens). 
 * @author Sarahbland
 *
 */
public abstract class PathPanel extends AuthoringScreen {
	
    	/**
    	 * Constructor of Path Panel abstract class
    	 * @param view
    	 */
	public PathPanel(AuthoringView view) {
		super(view);
	}
	
	/**
	 * Sets the path panel components
	 */
	protected abstract void makePanel();
	
	/**
	 * Gets the apply button that must be located in a path panel
	 * @return apply button
	 */
	protected abstract Button getApplyButton();
	
	/**
	 * Gets the path Panel
	 * @return panel
	 */
	protected abstract Node getPanel();
	
	/**
	 * Sets the apply button
	 * @param e
	 */
	protected abstract void setApplyButtonAction(EventHandler<ActionEvent> e);
}
