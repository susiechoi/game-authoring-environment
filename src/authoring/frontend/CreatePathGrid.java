package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

//figure out grid sizing
//add start/end
//separate panel containing blocks
//move drag and drop functionality to separate class (or set up grid in one class, and one for path drag-and-drop)

public class CreatePathGrid {

	private int pathSize = 50;
	private int colIndex;
	private int rowIndex;
	private ImageView pathImage;
	private GridPane grid;


	protected GridPane makePathGrid() {
		grid = new GridPane();
		grid.setMaxSize(1000, 1200);
		populateGrid();

		Image img = new Image("file:images/stone.png");
		pathImage = new ImageView();
		pathImage.setImage(img);

		pathImage.setFitWidth(pathSize);
		pathImage.setFitHeight(pathSize);


		pathImage.setOnDragDetected(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event){
				Dragboard db = pathImage.startDragAndDrop(TransferMode.ANY);
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

		grid.getChildren().addAll(pathImage);
		return grid;
	}

	private void populateGrid() {
		for (int x = 0 ; x < 30; x++) {
			for (int y = 0 ; y < 15; y++) {
				Pane cell = new Pane();
				cell.setBackground((new Background(new BackgroundFill(Color.LIGHTCYAN, null, null))));
				cell.setPrefSize(pathSize, pathSize);

				final int col = x;
				final int row = y;

				cell.setOnDragOver(new EventHandler <DragEvent>() {
					public void handle(DragEvent event) {
						if (event.getDragboard().hasImage()) {
							event.acceptTransferModes(TransferMode.ANY);
						}
						colIndex = col;
						rowIndex = row;
						event.consume();
					}
				});

				cell.setOnDragDropped(new EventHandler <DragEvent>() {
					public void handle(DragEvent event) {
						event.acceptTransferModes(TransferMode.ANY);
						Dragboard db = event.getDragboard();
						boolean success = false;
						if (db.hasImage()){
							ImageView piece = new ImageView(db.getImage());
							piece.setFitWidth(pathSize);
							piece.setFitHeight(pathSize);
							grid.add(piece, colIndex, rowIndex);
							success = true;
						}
						event.setDropCompleted(success);
						event.consume();
					}
				});
				
				grid.add(cell, x, y);
			}
		}
	}
}
