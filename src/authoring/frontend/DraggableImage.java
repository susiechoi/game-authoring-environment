package authoring.frontend;


import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DraggableImage {
	private ImageView pathImage;

	DraggableImage(Image image) {
		setPathImage(image);
	}

	private void setPathImage(Image image) {
		pathImage = new ImageView();
		pathImage.setImage(image);
		pathImage.setFitHeight(CreatePathPanel.PANEL_PATH_SIZE);
		pathImage.setFitWidth(CreatePathPanel.PANEL_PATH_SIZE);
	}

	public ImageView setCopyDraggable() {
		pathImage.setOnDragDetected(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event){
				Dragboard db = pathImage.startDragAndDrop(TransferMode.COPY);
				ClipboardContent content = new ClipboardContent();
				content.putImage(pathImage.getImage());
				db.setContent(content);
				event.consume();    
			}
		});

		pathImage.setOnDragDone(new EventHandler <DragEvent>() {
			public void handle(DragEvent event){
				if (event.getTransferMode() == TransferMode.MOVE){
					pathImage.setImage(null);
				}
				event.consume();
			}
		});

		return pathImage;
	}

	public void setDraggable() {
		pathImage.setOnDragDetected(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event){
				Dragboard db = pathImage.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putImage(pathImage.getImage());
				db.setContent(content);
				event.consume();    
			}
		});

		pathImage.setOnDragDone(e -> {
			if (e.getTransferMode() == TransferMode.MOVE){
				((ImageView) e.getSource()).setImage(null);
			}
			e.consume();
		});
	}

	public ImageView getPathImage() {
		return pathImage;
	}
	
	public void setNewImage(Image image) {
		pathImage.setImage(image);
	}
}