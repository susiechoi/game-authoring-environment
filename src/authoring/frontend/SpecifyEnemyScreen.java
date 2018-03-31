package authoring.frontend;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class SpecifyEnemyScreen extends SpecifyObjectScreen {

	private String DEFAULT_DESCRIPTOR = "Enemy";

	@Override
	public Scene makeScreenWithoutStyling() {
		VBox v = new VBox();
		ArrayList<String> testingList = new ArrayList<String>();
		//		Text intro = new Text("Customize "+DEFAULT_DESCRIPTOR); 
		//		intro.setId("intro");		
		//		v.getChildren().add(intro);

		v.getChildren().add(makeCreateNewObjectButton(DEFAULT_DESCRIPTOR));
		testingList.add("Edit Existing "+DEFAULT_DESCRIPTOR);
		testingList.add("Enemy 1");
		testingList.add("Enemy 2");

		Text orText = new Text("or"); 
		orText.setId("or");
		v.getChildren().add(orText);

		HBox h = new HBox(); 
		h.getChildren().add(makeTextDropdown(testingList)); 
		h.getChildren().add(makeGoButton());
		v.getChildren().add(h);
		
		return new Scene(v, 1500, 900); 
		
	}

}