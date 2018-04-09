package authoring.frontend;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class AdjustWaveScreen extends AdjustScreen {
    public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

    private StackPane pathRoot;
    private GridPane pathGrid;
    private Node pathPanel;
    private Panel panel;
    private CreatePathGrid grid;
    private String myWaveNumber;

    protected AdjustWaveScreen(AuthoringView view, String waveNumber) {
	super(view);
	setStyleSheet(DEFAULT_OWN_STYLESHEET); 
	myWaveNumber = waveNumber;
    }

    @Override
    public Parent makeScreenWithoutStyling() {
	pathRoot = new StackPane();
	//		Scene myScene = new Scene(pathRoot, 1500, 900);

	grid = new CreatePathGrid();
	pathGrid = grid.makePathGrid();
	grid.setUpForWaves(new EventHandler<MouseEvent>() {
	    public void handle(MouseEvent e){
		getView().loadErrorScreen("NoFile");
	    }
	}
		);

	panel = new WavePanel();
	panel.makePanel();
	pathPanel = panel.getPanel();

	pathRoot.getChildren().add(pathGrid);
	pathRoot.getChildren().add(pathPanel);
	StackPane.setAlignment(pathGrid, Pos.CENTER_LEFT);
	StackPane.setAlignment(pathPanel, Pos.CENTER_RIGHT);

	setGridSizing();
	setGridApplied();

	return pathRoot; 	
    }

    private void setGridSizing() {
	Button pathSizePlusButton = (Button) panel.getSizeButtons().getChildrenUnmodifiable().get(0);
	pathSizePlusButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		if (grid.getPathSize() < 100) {
		    grid.setGridConstraints(grid.getGrid(), grid.getPathSize() + 10);
		}
	    }
	});

	Button pathSizeMinusButton = (Button) panel.getSizeButtons().getChildren().get(1);
	pathSizeMinusButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		if (grid.getPathSize() > 30) {
		    grid.setGridConstraints(grid.getGrid(), grid.getPathSize() - 10);

		}
	    }
	});

	//	Button backgroundButton = (Button) panel.getBackgroundButton();
	//	backgroundButton.setOnAction(new EventHandler<ActionEvent>() {
	//		@Override
	//		public void handle(ActionEvent e) {
	//			FileChooser fileChooser = new FileChooser();
	//			fileChooser.setTitle("View Pictures");
	//			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
	//			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
	//			File file = fileChooser.showOpenDialog(new Stage());
	//			grid.setBackgroundmage(file);
	//		}
	//	});
    }
    private void setGridApplied() {
	Button applyButton = panel.getApplyButton();
	applyButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		HashMap<Integer, ArrayList<Integer>> coordMap = grid.getStartingPosition();
		for (int key: coordMap.keySet()) {
		    if (grid.checkPathConnected(coordMap.get(key).get(0), coordMap.get(key).get(1)) == true) {
			System.out.println("TRUE");
		    } else {
			System.out.println("FALSE");
		    }
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