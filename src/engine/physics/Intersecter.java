package engine.physics;

import javafx.scene.Node;

/**
 * Handles determining whether or not two images have intersected
 * 
 * @author Katherine Van Dyk
 * @date 4/5/18
 *
 */
public class Intersecter implements IIntersecter {

    /**
     * Determines if two nodes on the screen overlap
     * 
     * @return True if two nodes intersect, false otherwise
     */
    @Override
    public boolean overlaps(Node thisNode, Node thatNode) {
	return thisNode.getBoundsInLocal().intersects(thatNode.getBoundsInLocal());
    }

}
