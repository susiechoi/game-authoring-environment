package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;


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
		//remove former label from checkgrid
		//if image is already in grid...
		pathImage.setOnDragDetected(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event){
				
				//remove based on row and column its in
				Dragboard db = pathImage.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putImage(pathImage.getImage());
				db.setContent(content);
				event.consume();    
			}
		});
		
		pathImage.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent e){
				if (e.getTransferMode() == TransferMode.MOVE){
					((ImageView) e.getSource()).setImage(null);
				}
				e.consume();
			}
		});
	}
	
	public void removeNode(GridPane grid, int row, int col) {
		for(Node node : grid.getChildren()) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				grid.getChildren().remove(node);
				break;
			}
		} 
	}

	public void disableDraggable() {
		pathImage.removeEventHandler(MouseEvent.DRAG_DETECTED, myDragEvent);
		pathImage.removeEventHandler(MouseEvent.DRAG_DETECTED, myCopyDragEvent);
		pathImage.removeEventHandler(DragEvent.DRAG_DONE, myDragDone);
		pathImage.removeEventHandler(DragEvent.DRAG_DONE, myCopyDragDone);
		pathImage.setOnDragDetected(e -> {});
		pathImage.setOnDragDone(e -> {});
	}
	
	public void setPathName(int path_num) {
		pathName = "Path " +String.valueOf(path_num);
	}
	
	public String getPathName() {
	    	if(pathName == null) {
	    	    return "Default";
	    	}
		return pathName;
	}


	public void setNewImage(Image image) {
		pathImage.setImage(image);
	}

	public ImageView getPathImage() {
		return pathImage;
	}
}