package authoring.frontend;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class AdjustEnemyScreen extends AdjustScreen {

	@Override
	protected Scene makeScreen() {
		VBox vb = new VBox(); 

		HBox towerName = new HBox(); 
		Text towerNameText = new Text("Tower Name: "); 
		TextField towerNameInput = new TextField(); 
		towerName.getChildren().add(towerNameText);
		towerName.getChildren().add(towerNameInput); 
		
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
