//package authoring.frontend;
//
//import java.util.ArrayList;
//
//import javafx.event.EventHandler;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.DragEvent;
//import javafx.scene.input.Dragboard;
//import javafx.scene.input.TransferMode;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
//
//public class DragAndDropTarget {
//
//	private int colIndex;
//	private int rowIndex;
//	private ImageView startImage = new ImageView(new Image("file:images/start.png"));
//	private ImageView endImage = new ImageView(new Image("file:images/end.png"));
//	private ImageView pathImage = new ImageView(new Image("file:images/cobblestone.png"));
//	private Label startLabel;
//	private Label endLabel;
//	private Label pathLabel;
//	private ArrayList<Integer> startCols;
//	private ArrayList<Integer> startRows;
//	
//	public Pane createDragAndDropTarget(GridPane grid, GridPane checkGrid, Pane target, int col, int row) {
//		
//		startCols = new ArrayList<Integer>();
//		startRows = new ArrayList<Integer>();
//
//		StackPane cell = new StackPane();
//		cell.setOnDragOver(new EventHandler <DragEvent>() {
//			public void handle(DragEvent event) {
//				if (event.getDragboard().hasImage()) {
//					event.acceptTransferModes(TransferMode.ANY);
//				}
//				colIndex = col;
//				rowIndex = row;
//				event.consume();
//			}
//		});
//
//		cell.setOnDragDropped(new EventHandler <DragEvent>() {
//			public void handle(DragEvent event) {
//				event.acceptTransferModes(TransferMode.ANY);
//				Dragboard db = event.getDragboard();
//				boolean success = false;
//				if (db.hasImage()) {
//					DraggableImage path = new DraggableImage(db.getImage());
//					path.setDraggable();
//					path.getPathImage().fitWidthProperty().bind(cell.widthProperty()); 
//					path.getPathImage().fitHeightProperty().bind(cell.heightProperty()); 
//					grid.add(path.getPathImage(), colIndex, rowIndex);
//
//					if (imageCompare(path.getPathImage().getImage(), startImage.getImage()) == true) {
//						startLabel = new Label("start");
//						checkGrid.add(startLabel, colIndex, rowIndex);
//						startCols.add(colIndex);
//						startRows.add(rowIndex);
//					} else if (imageCompare(path.getPathImage().getImage(), endImage.getImage()) == true) {
//						endLabel = new Label("end");
//						checkGrid.add(endLabel, colIndex, rowIndex);
//					} else if (imageCompare(path.getPathImage().getImage(), pathImage.getImage()) == true) {
//						pathLabel = new Label("path");
//						checkGrid.add(pathLabel, colIndex, rowIndex);
//					}
//
//					success = true;
//				}
//				event.setDropCompleted(success);
//				event.consume();
//			}
//		});
//		
//		return cell;
//	}
//	
//	private boolean imageCompare(Image image1, Image image2) {
//		for (int i = 0; i < image1.getWidth(); i++){
//			for (int j = 0; j < image1.getHeight(); j++){
//				if (image1.getPixelReader().getArgb(i, j) != image2.getPixelReader().getArgb(i, j)) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//}
