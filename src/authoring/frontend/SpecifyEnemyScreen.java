package authoring.frontend;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class SpecifyEnemyScreen extends SpecifyObjectScreen {
	
	@Override
	protected void makeScreenWithStyle(String stylesheet) {
		VBox v = new VBox();
	    v.setPadding(new Insets(50));
		v.setSpacing(20);
		ArrayList<String> testingList = new ArrayList<String>();
		v.getChildren().add(makeCreateNewObjectButton("button test"));
		testingList.add("option 1");
		testingList.add("option 2");
		v.getChildren().add(makeTextDropdown(testingList, 60, 20));
		Scene sc = new Scene(v, 1500, 900);
		sc.getStylesheets().add(stylesheet);
		myScreen = sc; 
	}

}