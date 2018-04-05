package authoring.frontend;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class CreatePathScreen extends Screen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	
	private StackPane pathRoot;
	private GridPane pathGrid;
	private Node pathPanel;

	protected CreatePathScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}

	protected Scene makeScreenWithoutStyling() {
		pathRoot = new StackPane();
		Scene myScene = new Scene(pathRoot, 1500, 900);
		
		CreatePathGrid grid = new CreatePathGrid();
		pathGrid = grid.makePathGrid();
		
		CreatePathPanel panel = new CreatePathPanel();
		panel.makePanel();
		pathPanel = panel.getPanel();
		
		pathRoot.getChildren().add(pathGrid);
		pathRoot.getChildren().add(pathPanel);
		pathRoot.setAlignment(pathGrid, Pos.CENTER_LEFT);
		pathRoot.setAlignment(pathPanel, Pos.CENTER_RIGHT);
		
		return myScene; 	
	}
}
