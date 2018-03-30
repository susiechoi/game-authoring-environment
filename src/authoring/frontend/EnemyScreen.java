package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class EnemyScreen implements Screen {

	@Override
	public Parent getScreen() {
		VBox v = new VBox();
		v.getChildren().add(new Text("well hello there!"));
		return v; 
	}

	@Override
	public void makeScreen() {
	}

}
