package authoring.frontend;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Abstract class representing Screens that need to display the Path in some form (i.e. path
 * editing or Wave selecting). Dependent on CreatePathGrid to make the grid portion of the
 * path and on PathPanels/PathToolBars to correctly create the elemnts that make up the screen.
 * @author Sarahbland
 *
 */
public abstract class PathScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

	private StackPane pathRoot;
	private GridPane pathGrid;
	private CreatePathGrid grid;

	protected PathScreen(AuthoringView view) {	
		super(view);
		grid = new CreatePathGrid(view);
	}
	
	protected void setPathPanel(PathPanel panel, PathToolBar toolbar) {
		pathRoot.getChildren().clear();
		pathRoot.getChildren().add(panel.getPanel());
		pathRoot.getChildren().add(toolbar.getPanel());
		pathRoot.getChildren().add(pathGrid);

		StackPane.setAlignment(pathGrid, Pos.TOP_LEFT);
		StackPane.setAlignment(panel.getPanel(), Pos.CENTER_RIGHT);
		StackPane.setAlignment(toolbar.getPanel(), Pos.BOTTOM_LEFT);
	}
	

	@Override
	public Parent makeScreenWithoutStyling() {
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
		pathGrid = grid.makePathGrid();
		pathRoot = new StackPane();
		initializeGridSettings(grid);
		setSpecificUIComponents();
		return pathRoot; 	
	}

	protected abstract void initializeGridSettings(CreatePathGrid grid);
	protected abstract void setSpecificUIComponents();
	protected CreatePathGrid getGrid() {
	    return grid;
	}

	protected void setGridUIComponents(PathPanel panel, PathToolBar toolbar) {
		Button pathSizePlusButton = toolbar.getPlusButton();
		pathSizePlusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() < 100) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() + 10);
				}
			}
		});

		Button pathSizeMinusButton = toolbar.getMinusButton();
		pathSizeMinusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() > 30) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() - 10);

				}
			}
		});

		
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
