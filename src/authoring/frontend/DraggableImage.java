package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * Class of Images that can be drag/dropped for use in the Path.
 * @author Erik Riis
 *
 */
public class DraggableImage extends Parent {
	private ImageView pathImage;
	private String pathName;
	private EventHandler<MouseEvent> myCopyDragEvent;
	private EventHandler<MouseEvent> myDragEvent;
	private EventHandler<DragEvent> myDragDone;
	private EventHandler<DragEvent> myCopyDragDone;

	public DraggableImage(Image image) {
		setPathImage(image);
	}

	private void setPathImage(Image image) {
		pathImage = new ImageView();
		pathImage.setImage(image);
		pathImage.setFitHeight(CreatePathPanel.PANEL_PATH_SIZE);
		pathImage.setFitWidth(CreatePathPanel.PANEL_PATH_SIZE);
	}

	protected ImageView setCopyDraggable() {
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

	protected void setDraggable() {
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

	protected void disableDraggable() {
		pathImage.removeEventHandler(MouseEvent.DRAG_DETECTED, myDragEvent);
		pathImage.removeEventHandler(MouseEvent.DRAG_DETECTED, myCopyDragEvent);
		pathImage.removeEventHandler(DragEvent.DRAG_DONE, myDragDone);
		pathImage.removeEventHandler(DragEvent.DRAG_DONE, myCopyDragDone);
		pathImage.setOnDragDetected(e -> {});
		pathImage.setOnDragDone(e -> {});
	}
	
	protected void setPathName(int path_num) {
		pathName = "Path " +String.valueOf(path_num);
	}
	
	protected String getPathName() {
	    	if(pathName == null) {
	    	    return "Default";
	    	}
		return pathName;
	}


	protected void setNewImage(Image image) {
		pathImage.setImage(image);
	}

	protected ImageView getPathImage() {
		return pathImage;
	}
}