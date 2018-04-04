package engine.physics;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Mover implements Movable {

    @Override
    public void move(int newX, int newY) {
	Path path = new Path();
	path.getElements().addAll(new MoveTo(x1 + X_OFFSET, y1 + Y_OFFSET), new LineTo(x2 + X_OFFSET, y2 + Y_OFFSET));
	PathTransition pt = new PathTransition(Duration.millis(moveSpeed), path, agent);
	return new SequentialTransition(agent, pt);
	
    }

    @Override
    public void rotate(double angle) {
	// TODO Auto-generated method stub
	
    }

}
