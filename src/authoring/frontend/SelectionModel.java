package authoring.frontend;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SelectionModel {

    Set<Node> selectedNodes = new HashSet<>();

    public void addNode(Node node) {
	ColorAdjust colorAdjust = new ColorAdjust();
	colorAdjust.setBrightness(0.5);
	node.setEffect(colorAdjust);
	selectedNodes.add(node);
    }

    public void removeNode(Node node) {
	node.setEffect(null);
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

    public Set<Node> getSelectedNodes() {
	return selectedNodes;
    }

    public void rotate() {
	for (Node node: selectedNodes) {
	    node.setRotate(node.getRotate() + 90);
	}
    }

    public void setTransparent() {
	for (Node node: selectedNodes) {
	    if (node instanceof ImageView) {
		((ImageView) node).setOpacity(0);;
	    }
	}
    }
    
    public void setCurved() {
	for (Node node: selectedNodes) {
	    if (node instanceof ImageView) {
		((ImageView) node).setImage(new Image("file:images/darkstone1.png"));
	    }
	}
    }   
}

