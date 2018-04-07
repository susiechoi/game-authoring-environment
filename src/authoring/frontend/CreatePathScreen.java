package authoring.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class CreatePathScreen extends Screen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

	private StackPane pathRoot;
	private GridPane pathGrid;
	private Node pathPanel;
	private CreatePathPanel panel;
	private CreatePathGrid grid;

	protected CreatePathScreen(AuthoringView view) {
		super(view);
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	}

	protected Scene makeScreenWithoutStyling() {
		pathRoot = new StackPane();
		Scene myScene = new Scene(pathRoot, 1500, 900);

		grid = new CreatePathGrid();
		pathGrid = grid.makePathGrid();

		panel = new CreatePathPanel();
		panel.makePanel();
		pathPanel = panel.getPanel();

		pathRoot.getChildren().add(pathGrid);
		pathRoot.getChildren().add(pathPanel);
		StackPane.setAlignment(pathGrid, Pos.CENTER_LEFT);
		StackPane.setAlignment(pathPanel, Pos.CENTER_RIGHT);

		setGridSizing();
		setGridApplied();

		return myScene; 	
	}

	private void setGridSizing() {
		Button pathSizePlusButton = (Button) panel.getSizeButtons().getChildrenUnmodifiable().get(0);
		pathSizePlusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() < 100) {
					grid.setGridConstraints(grid.getPathSize() + 10);
				}
			}
		});

		Button pathSizeMinusButton = (Button) panel.getSizeButtons().getChildren().get(1);
		pathSizeMinusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() > 30) {
					grid.setGridConstraints(grid.getPathSize() - 10);
				}
			}
		});
	}


	private void setGridApplied() {
		Button applyButton = panel.getApplyButton();
		applyButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				grid.checkPathConnected(0, 0);
			}
		});
	}
}
