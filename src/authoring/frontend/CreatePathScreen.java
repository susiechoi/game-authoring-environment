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

public class CreatePathScreen extends PathScreen {

    public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

    //private StackPane pathRoot;
    private GridPane pathGrid;
    private Node pathPanel;
    //private CreatePathPanel panel;
    private CreatePathGrid grid;

    protected CreatePathScreen(AuthoringView view) {
	super(view);
    }

    //    protected 
    //    		Button backgroundButton = (Button) panel.getBackgroundButton();
    //    		backgroundButton.setOnAction(new EventHandler<ActionEvent>() {
    //    			@Override
    //    			public void handle(ActionEvent e) {
    //    				FileChooser fileChooser = new FileChooser();
    //    				fileChooser.setTitle("View Pictures");
    //    				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
    //    				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
    //    				File file = fileChooser.showOpenDialog(new Stage());
    //    				grid.setBackgroundmage(file);
    //    			}
    //    		});

    //fix error checking!!!!!
    //fix checkPathConnected 
    private void setGridApplied() {
	Button applyButton = getPathPanel().getApplyButton();
	//	getPathPanel().setApplyButtonAction(new EventHandler<ActionEvent>() {
	//		@Override
	//		public void handle(ActionEvent e) {
	//		    System.out.println("sad face turns happy?");
	//		}
	//	});
	getPathPanel().setApplyButtonAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {

		HashMap<Integer, ArrayList<Integer>> coordMap = grid.getStartingPosition();
		if (grid.getStartingPosition().size() == 0) {
		    Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Path Cutomization Error");
		    alert.setContentText("Your path has no starting blocks");
		    alert.showAndWait();
		}
		for (int key: coordMap.keySet()) {
		    //System.out.println(coordMap.get(key).get(0));
		    if (grid.checkPathConnected(coordMap.get(key).get(0), coordMap.get(key).get(1))) {
			System.out.println("TRUE");
			getView().makePath(key, grid.getAbsoluteCoordinates(), grid.getGrid()); //when apply is clicked and there is a complete path, the info gets passed to view
		    } else {
			System.out.println("FALSE");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Path Cutomization Error");
			alert.setContentText("Your path is incomplete - Please make sure that any start and end positions are connected");
			alert.showAndWait();
		    }
		}
	    }
	});
	//Button trybutton = getPathPanel().getApplyButton();

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

	@Override
	public void initializeGridSettings(CreatePathGrid gridIn) {
	    setPathPanel(new CreatePathPanel(getView()));
	    grid = gridIn; 
	    setGridApplied();


	}

    }
