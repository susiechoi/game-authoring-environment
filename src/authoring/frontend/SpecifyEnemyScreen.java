package authoring.frontend;

import java.util.List;

class SpecifyEnemyScreen extends SpecifyObjectScreen {
	
	public static final String DEFAULT_DESCRIPTOR = "Enemy";

	protected SpecifyEnemyScreen(List<String> enemyOptions) {
		super(enemyOptions);
		myObjectDescription = DEFAULT_DESCRIPTOR;
	}

}