package authoring.frontend;

import javafx.scene.Scene;

class AdjustEnemyScreen extends AdjustScreen {
	
	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	
	protected AdjustEnemyScreen() {
		myStylesheet = DEFAULT_OWN_STYLESHEET; 
	}
	
	protected Scene makeScreenWithoutStyling() {
		return null; 
	}
	
	
}
