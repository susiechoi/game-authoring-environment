package authoring.frontend;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class SelectionModel {

	Set<Node> selectedNodes = new HashSet<>();

	public void addNode(Node node) {
		node.setStyle("-fx-effect: dropshadow(three-pass-box, lightblue, 2, 2, 0, 0);");
		selectedNodes.add(node);
	}

	public void removeNode(Node node) {
		node.setStyle("-fx-effect: null");
		selectedNodes.remove(node);
	}

	public void clear() {
		while(!selectedNodes.isEmpty()) {
			removeNode(selectedNodes.iterator().next());
		}
	}

	public boolean contains(Node node) {
		return selectedNodes.contains(node);
	}

	public 	Set<Node> getSelectedNodes() {
		return selectedNodes;
	}

	public void rotate() {
		for (Node node: selectedNodes) {
			node.setRotate(node.getRotate() + 90);
		}
	}


	public void copyAndPaste() {
		for (Node node: selectedNodes) {
			if (node instanceof ImageView) {
				DraggableImage image = new DraggableImage(((ImageView) node).getImage());
//				image.setCopyDraggable();
			}
		}
	}
}

