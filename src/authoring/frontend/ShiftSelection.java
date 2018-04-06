package authoring.frontend;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ShiftSelection {

	private SelectionModel selectionModel;
	private Pane myGroup;

	public ShiftSelection(Pane group, SelectionModel model) {
		this.selectionModel = model;
		this.myGroup = group;

		myGroup.setOnMouseClicked(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event) {
				Node node = (Node) event.getTarget();
				if (node instanceof ImageView && event.isShiftDown()) {
					selectionModel.addNode(node);
				} else if (node instanceof ImageView && !event.isShiftDown()) {
					selectionModel.removeNode(node);
				}
			}
		});

		selectionModel.log();
	}
}
