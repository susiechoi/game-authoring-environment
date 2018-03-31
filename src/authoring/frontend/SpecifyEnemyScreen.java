package authoring.frontend;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

public class SpecifyEnemyScreen extends SpecifyObjectScreen {

	@Override
	public void makeScreen() {
		VBox v = new VBox();
	    v.setPadding(new Insets(50));
		v.setSpacing(20);
		ArrayList<String> testingList = new ArrayList<String>();
		v.getChildren().add(makeCreateNewObjectButton("button test"));
		testingList.add("option 1");
		testingList.add("option 2");
		v.getChildren().add(makeTextDropdown(testingList, 60, 20));
		myScreen = v; 
	}

}
