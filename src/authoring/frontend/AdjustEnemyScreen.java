package authoring.frontend;

import javafx.scene.Scene;

class AdjustEnemyScreen extends AdjustScreen {
	
	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	
	protected AdjustEnemyScreen() {
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}
	
	protected Scene makeScreenWithoutStyling() {
		return null; 
	}
	
	
}
