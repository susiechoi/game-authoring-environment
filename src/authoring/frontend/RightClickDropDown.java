package authoring.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class RightClickDropDown {
	private Node myNode;
	private SelectionModel myModel;
	
	public RightClickDropDown(Node node, SelectionModel model) {
		myNode = node;
		myModel = model;
		setupDropDown();
		setRightClickDropDown();
	}

	public void setRightClickDropDown() {
		ContextMenu dropDown = setupDropDown();
		myNode.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.SECONDARY || event.isControlDown()) {
					dropDown.show(myNode, event.getScreenX(), event.getScreenY());
				}
			}
		});
	}

	public ContextMenu setupDropDown() {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem item1 = new MenuItem("Rotate");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				myModel.rotate();
			}
		});
		
		MenuItem item2 = new MenuItem("Copy/Paste");
		item2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				myModel.copyAndPaste();
			}
		});
		
		contextMenu.getItems().addAll(item1, item2);
		return contextMenu;
	}
}
