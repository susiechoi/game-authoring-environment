package authoring.frontend;

import java.util.HashSet;

import javafx.scene.Node;

public class SelectionModel {

	HashSet<Node> selectedNodes = new HashSet<>();

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

	public HashSet<Node> getSelectedNodes() {
		return selectedNodes;
	}

}

