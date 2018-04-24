package authoring.frontend;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ShiftSelection {

	private SelectionModel selectionModel;
	private Pane myGroup;

	public ShiftSelection(Pane group, SelectionModel model) {
		this.selectionModel = model;
		this.myGroup = group;

		myGroup.setOnMouseClicked(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Node node = (Node) event.getTarget();
				if (event.isShiftDown()) {
					selectionModel.addNode(node);
					new RightClickDropDown(node, selectionModel);
				} 
			}
		});
	}
}
