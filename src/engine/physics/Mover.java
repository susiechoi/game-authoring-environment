package engine.physics;

import javafx.scene.Node;

/**
 * Handles moving objects along a path
 * 
 * @author Katherine Van Dyk
 * @date 4/5/18
 *
 */
public class Mover implements IMover {
    
    private double speed;
    
    public Mover(double s) {
	speed = s;
    }

    @Override
    public void move(Node thisNode, Integer targetX, Integer targetY) {
	thisNode.setLayoutX(thisNode.getLayoutX() + speed * targetX);
	thisNode.setLayoutY(thisNode.getLayoutY() + speed * targetY);
    }
    

    @Override
    public void rotate(Node thisNode, Integer targetAngle) {
	thisNode.setRotate(targetAngle);
    }
    
    public void setSpeed(double s) {
	speed = s;
    }


}
