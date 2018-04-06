package authoring.frontend;

import frontend.UIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreatePathPanel implements Panel {
	
	public static final int PANEL_PATH_SIZE = 90;
	private VBox pathPanel;
	private PathImage pathImage;
	private PathImage startImage;
	private PathImage endImage;
	private Button trashButton;
	private Button pathSizePlusButton;
	private Button pathSizeMinusButton;
	private HBox pathSizeButtons;

	@Override
	public void makePanel() {
		pathPanel = new VBox();
		pathPanel.setMaxSize(300, 900);

		pathImage = new PathImage("file:images/cobblestone.png");
		new DraggableImage(pathImage.getPathImage());
		
		startImage = new PathImage("file:images/start.png");
		new DraggableImage(startImage.getPathImage());
		
		endImage = new PathImage("file:images/end.png");
		new DraggableImage(endImage.getPathImage());
		
		Image trashImg = new Image("file:images/trash.png", 80, 80, true, false);	
		UIFactory factory = new UIFactory();
		trashButton = factory.makeImageButton("", trashImg);
		trashButton.setMaxSize(PANEL_PATH_SIZE, PANEL_PATH_SIZE);
		
		trashButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        pathPanel.setCursor(Cursor.HAND); //Change cursor to hand
		    }
		});
		
		Image plusImg = new Image("file:images/plus.png", 60, 40, true, false);
		pathSizePlusButton = factory.makeImageButton("", plusImg);
		
		Image minusImg = new Image("file:images/minus.png", 60, 40, true, false);
		pathSizeMinusButton = factory.makeImageButton("", minusImg);
		
		pathSizeButtons = new HBox();
		pathSizeButtons.getChildren().addAll(pathSizePlusButton, pathSizeMinusButton);
		
		pathPanel.getChildren().addAll(startImage.getPathImage(), pathImage.getPathImage(), endImage.getPathImage(), pathSizeButtons, trashButton);
	}
	
	public HBox getSizeButtons() {
		return pathSizeButtons;
	}
	
	@Override
	public Node getPanel() {
		return pathPanel;
	}
}
