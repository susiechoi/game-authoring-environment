package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DraggableImage {
	private ImageView myImage;
	
	DraggableImage(ImageView image) {
		myImage = image;
		setDraggable();
	}

	private ImageView setDraggable() {
		myImage.setOnDragDetected(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event){
				Dragboard db = myImage.startDragAndDrop(TransferMode.COPY);
				ClipboardContent content = new ClipboardContent();
				content.putImage(myImage.getImage());
				db.setContent(content);
				event.consume();    
			}
		});

		myImage.setOnDragDone(new EventHandler <DragEvent>() {
			public void handle(DragEvent event){
				if (event.getTransferMode() == TransferMode.MOVE){
					myImage.setImage(null);
				}
				event.consume();
			}
		});
		
		return myImage;
	}
}