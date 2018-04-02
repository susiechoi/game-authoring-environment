package authoring.frontend;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CreatePathPanel implements Panel {
	
	private VBox pathPanel;
	private ImageView pathImage;
	private ImageView startImage;
	private ImageView endImage;
	private int pathSize = 80;

	@Override
	public void makePanel() {
		pathPanel = new VBox();
		pathPanel.setMaxSize(300, 900);
		
		Image pathImg = new Image("file:images/stone.png");
		pathImage = new ImageView();
		pathImage.setImage(pathImg);
		pathImage.setFitWidth(pathSize);
		pathImage.setFitHeight(pathSize);
		
		Image startImg = new Image("file:images/start.png");
		startImage = new ImageView();
		startImage.setImage(startImg);
		startImage.setFitWidth(pathSize);
		startImage.setFitHeight(pathSize);
		
		Image endImg = new Image("file:images/square.png");
		endImage = new ImageView();
		endImage.setImage(endImg);
		endImage.setFitWidth(pathSize);
		endImage.setFitHeight(pathSize);
		
		pathPanel.getChildren().add(startImage);
		pathPanel.getChildren().add(endImage);
		pathPanel.getChildren().add(pathImage);
	}
	
	@Override
	public Node getPanel() {
		return pathPanel;
	}
}
