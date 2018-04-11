package authoring.frontend;


import java.awt.Point;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreatePathScreen extends PathScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";
	
	//private CreatePathPanel panel;
	private CreatePathGrid grid;
	private String backgroundImageFileName;

	protected CreatePathScreen(AuthoringView view) {
		super(view);
		setGridBackground();
	}

	private void setGridBackground() {
		
		//TODO: panel.getBackgroundButton() fix
//		Button backgroundButton = (Button) panel.getBackgroundButton();
//		backgroundButton.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent e) {
//				FileChooser fileChooser = new FileChooser();
//				fileChooser.setTitle("View Pictures");
//				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
//				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
//				File file = fileChooser.showOpenDialog(new Stage());
//				backgroundImageFileName = file.toURI().toString();
//				grid.setBackgroundImage(backgroundImageFileName);
//			}
//		});
	}

	//TODO: fix checkPathConnected - doesn't work for multiple starts, after moving path, or after set sizing
	private void setGridApplied() {
		getPathPanel().setApplyButtonAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				List<Point> startCoords = grid.getStartingPosition();
				if (grid.getStartingPosition().size() == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Path Cutomization Error");
					alert.setContentText("Your path has no starting blocks");
					alert.showAndWait();
				}
				for (Point point: startCoords) {
					System.out.println(point);
					if (grid.checkPathConnected(grid.getCheckGrid(), (int) point.getY(), (int) point.getX())) {
						System.out.println("TRUE");
						getView().makePath(grid.getGrid(), grid.getAbsoluteCoordinates(), grid.getGridImageCoordinates(), backgroundImageFileName); //when apply is clicked and there is a complete path, the info gets passed to view
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
