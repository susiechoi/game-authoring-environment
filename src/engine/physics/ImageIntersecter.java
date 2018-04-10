package engine.physics;

import javafx.scene.Node;

/**
 * Handles determining whether or not two images have intersected
 * 
 * @author Katherine Van Dyk
 * @date 4/5/18
 *
 */
public class ImageIntersecter implements Intersecter {

    private Node currentNode;
    
    public ImageIntersecter(Node input) {
	currentNode = input;
    }
    /**
     * Determines if two nodes on the screen overlap
     * 
     * @return True if two nodes intersect, false otherwise
     */
    @Override
    public boolean overlaps(Node thatNode) {
	return currentNode.getBoundsInParent().intersects(thatNode.getBoundsInParent());
    }

}
