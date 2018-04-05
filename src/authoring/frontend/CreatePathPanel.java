package authoring.frontend;

import frontend.UIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CreatePathPanel implements Panel {
	
	public static final int PANEL_PATH_SIZE = 80;
	private VBox pathPanel;
	private ImageView pathImage;
	private ImageView startImage;
	private ImageView endImage;
	private Button trashButton;

	@Override
	public void makePanel() {
		pathPanel = new VBox();
		pathPanel.setMaxSize(300, 900);

		Image pathImg = new Image("file:images/stone.png");
		pathImage = new ImageView();
		pathImage.setImage(pathImg);
		pathImage.setFitWidth(PANEL_PATH_SIZE);
		pathImage.setFitHeight(PANEL_PATH_SIZE);
		new DraggableImage(pathImage);
		
		Image startImg = new Image("file:images/start.png");
		startImage = new ImageView();
		startImage.setImage(startImg);
		startImage.setFitWidth(PANEL_PATH_SIZE);
		startImage.setFitHeight(PANEL_PATH_SIZE);
		new DraggableImage(startImage);
		
		Image endImg = new Image("file:images/square.png");
		endImage = new ImageView();
		endImage.setImage(endImg);
		endImage.setFitWidth(PANEL_PATH_SIZE);
		endImage.setFitHeight(PANEL_PATH_SIZE);
		new DraggableImage(endImage);
		
		
//		Image trashImg = new Image("file:images/trash.png");	
		UIFactory factory = new UIFactory();
		trashButton = factory.makeTextButton("", "trash");
		trashButton.setMaxSize(PANEL_PATH_SIZE, PANEL_PATH_SIZE);
		
		trashButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        pathPanel.setCursor(Cursor.HAND); //Change cursor to hand
		    }
		});
	
		pathPanel.getChildren().addAll(startImage, endImage, pathImage, trashButton);
	}
	
	@Override
	public Node getPanel() {
		return pathPanel;
	}
}
