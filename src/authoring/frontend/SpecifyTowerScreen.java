package authoring.frontend;

import java.util.List;

public class SpecifyTowerScreen extends SpecifyObjectScreen {
	
	public static final String DEFAULT_DESCRIPTION = "Tower";

	protected SpecifyTowerScreen(List<String> towerOptions) {
		super(towerOptions);
		myObjectDescription = DEFAULT_DESCRIPTION; 
	}

}
