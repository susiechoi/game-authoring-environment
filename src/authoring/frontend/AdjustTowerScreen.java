package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class AdjustTowerScreen implements Screen {

	private Parent myScreen; 

	@Override
	public void makeScreen() {
		VBox v = new VBox();
		v.getChildren().add(new Text("testing enemy screen"));
		myScreen = v; 
	}

	@Override
	public Parent getScreen() {
		return myScreen; 
	}

}
