package authoring.frontend;

import java.util.ArrayList;

import javafx.scene.layout.VBox;

public class SpecifyEnemyScreen extends SpecifyObjectScreen {

	@Override
	public void makeScreen() {
		VBox v = new VBox();
		ArrayList<String> testingList = new ArrayList<String>();
		testingList.add("hi");
		testingList.add("bye");
		v.getChildren().add(makeTextDropdown(testingList, 60, 20));
		myScreen = v; 
	}

}
