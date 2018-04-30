package authoring.frontend;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
		if (event.isShiftDown() && event.getTarget() instanceof Pane) {
		    selectionModel.addCell((Pane) event.getTarget());
		    new RightClickDropDown((Pane) event.getTarget(), selectionModel);
		} else if (!event.isShiftDown() && event.getButton() != MouseButton.SECONDARY && event.getTarget() instanceof Pane) {
		    selectionModel.removeCell((Pane) event.getTarget());
		} else if (event.isShiftDown() && event.getTarget() instanceof ImageView) {
		    selectionModel.addNode((Node) event.getTarget());
		    new RightClickDropDown((Node) event.getTarget(), selectionModel);
		} else if (!event.isShiftDown() && event.getButton() != MouseButton.SECONDARY && event.getTarget() instanceof ImageView) {
		    selectionModel.removeNode((Node) event.getTarget());
		}
	    }
	});
    }
}
