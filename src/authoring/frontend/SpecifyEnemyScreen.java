package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class SpecifyEnemyScreen extends SpecifyObjectScreen {
	
	@Override
	protected void makeScreenWithStyles(List<String> stylesheets) {
		VBox v = new VBox();
		ArrayList<String> testingList = new ArrayList<String>();
		v.getChildren().add(makeCreateNewObjectButton("Enemy"));
		testingList.add("Enemy 1");
		testingList.add("Enemy 2");
		v.getChildren().add(makeTextDropdown(testingList));
		Scene sc = new Scene(v, 1500, 900);
		for (String s : stylesheets) {
			sc.getStylesheets().add(s);
		}
		myScreen = sc; 
	}

}