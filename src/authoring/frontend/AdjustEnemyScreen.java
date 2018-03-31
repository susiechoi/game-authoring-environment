package authoring.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class AdjustEnemyScreen extends Screen {
	
	
	@Override
	public void makeScreen() {
		VBox v = new VBox();
		v.getChildren().add(new Text("testing enemy screen"));
		Scene sc = new Scene(v, 1500, 900);
		setScreen(sc); 
	}
}
