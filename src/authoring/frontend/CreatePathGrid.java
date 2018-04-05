package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
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
//delete button to get rid of path blocks (trash, select all and delete)

public class CreatePathGrid {

	protected double pathSize = 50;
	private int colIndex;
	private int rowIndex;
	private GridPane grid;

	protected GridPane makePathGrid() {
		grid = new GridPane();
		grid.setMaxSize(1000, 1200);
		populateGrid();
		return grid;
	}

	private void populateGrid() {
		for (int x = 0 ; x < 1000/pathSize; x++) {
			for (int y = 0 ; y < 1200/pathSize; y++) {
				Pane cell = new Pane();
				cell.setBackground((new Background(new BackgroundFill(Color.LIGHTCYAN, null, null))));
				cell.setPrefSize(pathSize, pathSize); //background

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
