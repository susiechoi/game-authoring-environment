package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class SpecifyEnemyScreen extends SpecifyObjectScreen {

	private String DEFAULT_DESCRIPTOR = "Enemy";
	
	@Override
	protected void makeScreenWithStyles(List<String> stylesheets) {
		VBox v = new VBox();
		ArrayList<String> testingList = new ArrayList<String>();
		v.getChildren().add(makeCreateNewObjectButton(DEFAULT_DESCRIPTOR));
		testingList.add("Select Existing "+DEFAULT_DESCRIPTOR);
		testingList.add("Enemy 1");
		testingList.add("Enemy 2");
		v.getChildren().add(new Text("or"));

		HBox h = new HBox(); 
		h.getChildren().add(makeTextDropdown(testingList)); 
		h.getChildren().add(makeGoButton());
		v.getChildren().add(h);
		
		Scene sc = new Scene(v, 1500, 900);
		for (String s : stylesheets) {
			sc.getStylesheets().add(s);
		}
		myScreen = sc; 
	}

}