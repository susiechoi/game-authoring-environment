package authoring.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

public abstract class PathPanel extends AuthoringScreen {
	
	public PathPanel(AuthoringView view) {
		super(view);
	}

	protected abstract void makePanel();
	
	protected abstract Button getApplyButton();
	
	protected abstract Node getPanel();
	
	protected abstract void setApplyButtonAction(EventHandler<ActionEvent> e);
}
