package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


/**
 * Class of Images that can be drag/dropped for use in the Path.
 * @author Erik Riis
 *
 */
public class DraggableImage extends Parent {
    private ImageView pathImage;

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
	pathImage.setOnMouseEntered(new EventHandler <MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event){
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(0.3);
		pathImage.setEffect(colorAdjust);
		event.consume();    
	    }
	});
	pathImage.setOnMouseExited(new EventHandler <MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event){
		pathImage.setEffect(null);
		event.consume();    
	    }
	});
	pathImage.setOnDragDetected(new EventHandler <MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event){
		Dragboard db = pathImage.startDragAndDrop(TransferMode.COPY);
		ClipboardContent content = new ClipboardContent();
		content.putImage(pathImage.getImage());
		db.setContent(content);
		event.consume();    
	    }
	});

	pathImage.setOnDragDone(new EventHandler <DragEvent>() {
	    @Override
	    public void handle(DragEvent event){
		if (event.getTransferMode() == TransferMode.MOVE){
		    pathImage.setImage(null);
		}
		event.consume();
	    }
	});
	return pathImage;
    }

    public void setDraggable(GridPane grid, int row, int col) {
	//		pathImage.setOnMouseEntered(new EventHandler <MouseEvent>() {
	//			@Override
	//			public void handle(MouseEvent event){
	//				ColorAdjust colorAdjust = new ColorAdjust();
	//				colorAdjust.setBrightness(0.3);
	//				pathImage.setEffect(colorAdjust);
	//				event.consume();    
	//			}
	//		});
	//		pathImage.setOnMouseExited(new EventHandler <MouseEvent>() {
	//			@Override
	//			public void handle(MouseEvent event){
	//				pathImage.setEffect(null);
	//				event.consume();    
	//			}
	//		});

	pathImage.setOnDragDetected(new EventHandler <MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event){
		removeNode(grid, row, col);
		Dragboard db = pathImage.startDragAndDrop(TransferMode.MOVE);
		ClipboardContent content = new ClipboardContent();
		content.putImage(pathImage.getImage());
		db.setContent(content);
		event.consume();
	    }
	});

	pathImage.setOnDragDone(new EventHandler<DragEvent>() {
	    @Override
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

    protected void setNewImage(Image image) {
	pathImage.setImage(image);
    }

    protected ImageView getPathImage() {
	return pathImage;
    }

}