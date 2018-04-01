package authoring.frontend;

import java.util.List;

import javafx.scene.Scene;

public class SpecifyTowerScreen extends SpecifyObjectScreen {
	
	public static final String DEFAULT_DESCRIPTION = "Tower";

	protected SpecifyTowerScreen(List<String> objectOptions) {
		super(objectOptions);
		myObjectDescription = DEFAULT_DESCRIPTION; 
	}

	@Override
	public Scene makeScreenWithoutStyling() {
		return null; 
	}

}
