package authoring.frontend;


import java.awt.Point;
import java.util.List;

import authoring.frontend.exceptions.ObjectNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreatePathScreen extends PathScreen {

	private CreatePathGrid grid;
	private String backgroundImageFileName;

	protected CreatePathScreen(AuthoringView view) {
		super(view);
	}
	
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
						try {
							getView().makePath(grid.getGrid(), grid.getAbsoluteCoordinates(), grid.getGridImageCoordinates(), backgroundImageFileName);
						} catch (ObjectNotFoundException e1) {
							// TODO Auto-generated catch block
						}
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
		setPathPanel(new CreatePathPanel(getView()), new CreatePathToolBar(getView()));
		grid = gridIn; 
		setGridApplied();
	}
}
