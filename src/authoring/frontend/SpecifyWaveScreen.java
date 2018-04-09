package authoring.frontend;

import javafx.scene.Parent;

public class SpecifyWaveScreen extends SpecifyObjectScreen{
    public static final String DEFAULT_DESCRIPTION = "Wave";

    protected SpecifyWaveScreen(AuthoringView view) {
	super(view);
	setDescription(DEFAULT_DESCRIPTION); 
    }
}
