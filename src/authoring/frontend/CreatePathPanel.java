package authoring.frontend;

import frontend.UIFactory;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreatePathPanel implements Panel {
	
	public static final int PANEL_PATH_SIZE = 90;
	private VBox pathPanel;
	private DraggableImage pathImage;
	private DraggableImage startImage;
	private DraggableImage endImage;
	private Button pathSizePlusButton;
	private Button pathSizeMinusButton;
	private HBox pathSizeButtons;
	private Button startImageChooser;
	private Button endImageChooser;
	private Button pathImageChooser;

	@Override
	public void makePanel() { //separate into smaller methods
		pathPanel = new VBox();
		pathPanel.setMaxSize(280, 900);
		pathPanel.getStylesheets();

		Image pathImg = new Image("file:images/cobblestone.png");
		pathImage = new DraggableImage(pathImg); //get defaults
		pathImage.setCopyDraggable();
		pathImage.getPathImage().getStyleClass().add("img-view");
		
		Image startImg = new Image("file:images/start.png");
		startImage = new DraggableImage(startImg);
		startImage.setCopyDraggable();
		startImage.getPathImage().getStyleClass().add("img-view");
		
		Image endImg = new Image("file:images/end.png");
		endImage = new DraggableImage(endImg);
		endImage.setCopyDraggable();
		endImage.getPathImage().getStyleClass().add("img-view");
		
		UIFactory factory = new UIFactory();

		ImageView trashImage = new ImageView(new Image("file:images/trash.png", 120, 120, true, false));
		trashImage.getStyleClass().add("img-view");
		trashImage.setOnDragOver(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.ANY);
				}
			}
		});
		
		trashImage.setOnDragDropped(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasImage()) {
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
		
		Image plusImg = new Image("file:images/plus.png", 60, 40, true, false);
		pathSizePlusButton = factory.makeImageButton("", plusImg);
		
		Image minusImg = new Image("file:images/minus.png", 60, 40, true, false);
		pathSizeMinusButton = factory.makeImageButton("", minusImg);
		
		pathSizeButtons = new HBox();
		pathSizeButtons.getChildren().addAll(pathSizePlusButton, pathSizeMinusButton);
		
		startImageChooser = factory.makeTextButton("", "Choose Start Image");
		endImageChooser = factory.makeTextButton("", "Choose End Image");
		pathImageChooser = factory.makeTextButton("", "Choose Path Image");
		
		
		pathPanel.getChildren().addAll(startImage.getPathImage(), pathImage.getPathImage(), endImage.getPathImage(), trashImage, pathSizeButtons, startImageChooser, pathImageChooser, endImageChooser);
	}
	
	public HBox getSizeButtons() {
		return pathSizeButtons;
	}
	
	@Override
	public Node getPanel() {
		return pathPanel;
	}
}
