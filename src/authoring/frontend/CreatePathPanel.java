package authoring.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;


public class CreatePathPanel extends PathPanel {

	public static final int PANEL_PATH_SIZE = 90;
	public static final String DEFAULT_PATH_IMAGE = "file:images/cobblestone.png";
	public static final String DEFAULT_START_IMAGE = "file:images/brick.png";
	public static final String DEFAULT_END_IMAGE = "file:images/darkstone.png";
	public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";
	private VBox pathPanel;
	private DraggableImage pathImage;
	private DraggableImage startImage;
	private DraggableImage endImage;
	private Button applyButton;
	private Button backButton;
	private ImageView trashImage;

	public CreatePathPanel(AuthoringView view) {
		super(view);
		makePanel();
	}

	@Override
	public Button getApplyButton() {
		return applyButton;
	}

	public void makePanel() {

		pathPanel = new VBox();

		pathPanel.setMaxSize(260, 900);
		pathPanel.getStylesheets();

		Label panelTitle = new Label("Drag and Drop Paths");
		
		Label startLabel = new Label("Start:");
		startLabel.getStyleClass().add("label-path");
		Label pathLabel = new Label("Path:");
		pathLabel.getStyleClass().add("label-path");
		Label endLabel = new Label("End:");
		endLabel.getStyleClass().add("label-path");
		
		Image pathImg = new Image(DEFAULT_PATH_IMAGE); 
		pathImage = new DraggableImage(pathImg);
		pathImage.setCopyDraggable();
		pathImage.getPathImage().getStyleClass().add("img-view");

		Image startImg = new Image(DEFAULT_START_IMAGE);
		startImage = new DraggableImage(startImg);
		startImage.setCopyDraggable();
		startImage.getPathImage().getStyleClass().add("img-view");

		Image endImg = new Image(DEFAULT_END_IMAGE);
		endImage = new DraggableImage(endImg);
		endImage.setCopyDraggable();
		endImage.getPathImage().getStyleClass().add("img-view");

		applyButton = getUIFactory().makeTextButton("", "Apply");

		backButton = setupBackButton();
		
		trashImage = new ImageView(new Image("file:images/trash.png", 120, 120, true, false));

		pathPanel.getChildren().addAll(panelTitle, startLabel, startImage.getPathImage(), pathLabel, pathImage.getPathImage(), endLabel, endImage.getPathImage(), trashImage, applyButton, backButton);
	}
	
	protected ImageView makeTrashImage() {
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
		return trashImage;
	}


	public Node getPanel() {
		return pathPanel;
	}
	
	public DraggableImage getPanelPathImage() {
		return pathImage;
	}
	
	public DraggableImage getPanelStartImage() {
		return startImage;
	}
	
	public DraggableImage getPanelEndImage() {
		return endImage;
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		//TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setApplyButtonAction(EventHandler<ActionEvent> e) {
		applyButton.setOnAction(event -> e.handle(event));
	}

}

