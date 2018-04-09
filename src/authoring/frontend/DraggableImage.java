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
	private EventHandler<MouseEvent> myCopyDragEvent;
	private EventHandler<MouseEvent> myDragEvent;
	private EventHandler<DragEvent> myDragDone;
	private EventHandler<DragEvent> myCopyDragDone;

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
		myCopyDragEvent = new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event){
				Dragboard db = pathImage.startDragAndDrop(TransferMode.COPY);
				ClipboardContent content = new ClipboardContent();
				content.putImage(pathImage.getImage());
				db.setContent(content);
				event.consume();    
			}
		};
		pathImage.setOnDragDetected(myCopyDragEvent);

		myCopyDragDone = new EventHandler <DragEvent>() {
			public void handle(DragEvent event){
				if (event.getTransferMode() == TransferMode.MOVE){
					pathImage.setImage(null);
				}
				event.consume();
			}
		};
		pathImage.setOnDragDone(myCopyDragDone);

		return pathImage;
	}

	public void setDraggable() {
		myDragEvent = new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event){
				Dragboard db = pathImage.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putImage(pathImage.getImage());
				db.setContent(content);
				event.consume();    
			}
		};
		pathImage.setOnDragDetected(myDragEvent);
		myDragDone = new EventHandler<DragEvent>() {
			public void handle(DragEvent e){
				if (e.getTransferMode() == TransferMode.MOVE){
					((ImageView) e.getSource()).setImage(null);
				}
				e.consume();
			}

		};
		pathImage.setOnDragDone(myDragDone);
	}

	public void disableDraggable() {
		pathImage.removeEventHandler(MouseEvent.DRAG_DETECTED, myDragEvent);
		pathImage.removeEventHandler(MouseEvent.DRAG_DETECTED, myCopyDragEvent);
		pathImage.removeEventHandler(DragEvent.DRAG_DONE, myDragDone);
		pathImage.removeEventHandler(DragEvent.DRAG_DONE, myCopyDragDone);
		pathImage.setOnDragDetected(e -> {});
		pathImage.setOnDragDone(e -> {});
	}


	public void setNewImage(Image image) {
		pathImage.setImage(image);
	}

	public ImageView getPathImage() {
		return pathImage;
	}
}