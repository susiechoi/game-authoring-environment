package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class EnemyScreen implements Screen {

	@Override
	public Parent getScreen() {
		VBox v = new VBox();
		v.getChildren().add(new Text("well hello there!"));
		return v; 
	}

	@Override
	public void makeScreen() {
		System.out.println("hi");
		Stage s = new Stage(); 
		EnemyScreen e = new EnemyScreen();
		Scene sc = new Scene(e.getScreen(), 50, 50);
		s.setScene(sc);
		s.show();
	}

}
