package authoring.frontend;

import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;

public class SpecifyWaveScreen extends SpecifyObjectScreen{
	public static final String DEFAULT_DESCRIPTION = "Wave";

	protected SpecifyWaveScreen(AuthoringView view) {
		super(view, DEFAULT_DESCRIPTION);
		
	}
	protected Node setupAdditionalElements() {
	    TextField selectTime = new TextField();
	    
	}
}
