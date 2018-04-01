package authoring.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustScreen {
	
	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	
	protected AdjustEnemyScreen() {
		myStylesheet = DEFAULT_OWN_STYLESHEET; 
	}
	
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 

		HBox towerName = setupPromptAndTextField("Tower Name: "); 
		
		HBox towerImage = new HBox(); 
		
		HBox projectileImage = new HBox(); 
		HBox towerAbility = new HBox(); 
		HBox towerRange = new HBox(); 
		HBox towerDamage = new HBox(); 
		HBox towerPrice = new HBox(); 
		HBox backAndApply = setupBackAndApply(); 

		vb.getChildren().add(towerName);
		
		return new Scene(vb, 1500, 900); 
	}
	
	
}
