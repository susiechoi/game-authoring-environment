package authoring.frontend;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public abstract class PathScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

	private StackPane pathRoot;
	private GridPane pathGrid;
	//private Node pathPanel;
	private PathPanel panel;
	private CreatePathGrid grid;

	protected PathScreen(AuthoringView view) {	
	    super(view);
	}
	protected void setPathPanel(PathPanel panelnew) {
	    panel = panelnew;
	    pathRoot.getChildren().clear();
	    pathRoot.getChildren().add(panel.getPanel());
	    pathRoot.getChildren().add(pathGrid);
	}
	protected PathPanel getPathPanel() {
	    return panel;
	}
	@Override
	public Parent makeScreenWithoutStyling() {
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
		grid = new CreatePathGrid();
		pathGrid = grid.makePathGrid();
		pathRoot = new StackPane();
		//		Scene myScene = new Scene(pathRoot, 1500, 900);

		grid = new CreatePathGrid();
		initializeGridSettings(grid);
		pathGrid = grid.makePathGrid();

		pathRoot.getChildren().add(pathGrid);
		pathRoot.getChildren().add(panel.getPanel());
		
		StackPane.setAlignment(pathGrid, Pos.CENTER_LEFT);
		StackPane.setAlignment(panel.getPanel(), Pos.CENTER_RIGHT);

		setGridSizing();
		

		return pathRoot; 	
	}
	
	public abstract void initializeGridSettings(CreatePathGrid grid);

	private void setGridSizing() {
		Button pathSizePlusButton = panel.getPlusButton();
		pathSizePlusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() < 100) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() + 10);
				}
			}
		});

		Button pathSizeMinusButton = panel.getMinusButton();
		pathSizeMinusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() > 30) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() - 10);

				}
			}
		});

//		Button backgroundButton = (Button) panel.getBackgroundButton();
//		backgroundButton.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent e) {
//				FileChooser fileChooser = new FileChooser();
//				fileChooser.setTitle("View Pictures");
//				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
//				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
//				File file = fileChooser.showOpenDialog(new Stage());
//				grid.setBackgroundmage(file);
//			}
//		});
	}

	@Override
	protected Parent populateScreenWithFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void populateFieldsWithData() {
		// TODO Auto-generated method stub

	}
}
