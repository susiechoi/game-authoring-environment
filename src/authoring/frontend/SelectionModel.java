package authoring.frontend;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.Node;

public class SelectionModel {

	Set<Node> selectedNodes = new HashSet<>();

	public void addNode(Node node) {
		node.setStyle("-fx-effect: dropshadow(three-pass-box, lightblue, 2, 2, 0, 0);");
		selectedNodes.add(node);
	}

	public void removeNode(Node node) {
		node.setStyle("-fx-effect: null");
		selectedNodes.remove( node);
	}

	public void clear() {
		while(!selectedNodes.isEmpty()) {
			removeNode(selectedNodes.iterator().next());
		}
	}

	public boolean contains(Node node) {
		return selectedNodes.contains(node);
	}

	public Set<Node> getSelectedNodes() {
		return selectedNodes;
	}
	
	public void log() {
		System.out.println( "Items in model: " + Arrays.asList(selectedNodes.toArray()));
	}
}

