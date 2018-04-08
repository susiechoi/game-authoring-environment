package engine.path;

import java.awt.geom.Point2D;

/**
 * Class for constructing path and determining next coordinates
 * 
 * @author Katherine Van Dyk 4/8/18
 */
public class Path {
    
    //TODO do trig to figure out how to calculate next position off based off of list of coordinates
    //The idea is that you move incrementally in one direction until you get past the next coord in the
    // myCoordinates array, and then you set your position to the next coordinate in that array and reset
    //your angle and continue moving
    private Point2D[] myCoordinates;
    private Point2D currentPosition;
    private Point2D angle;
    
    
    /**
     * Adds a start to the path. A path start point represents where enemies spawn.
     */
    public void addCoords(Point2D[] coordinates){
	myCoordinates = coordinates;
    }
    
    /**
     * Returns the next position of the object according to its speed
     * 
     * @param mySpeed
     */
    public Point2D nextPosition(double speed, double elapsedTime) {
	checkBounds(speed);
	double newX = currentPosition.getX() + speed * elapsedTime;
	double newY = currentPosition.getY() + speed*elapsedTime;
	return new Point2D.Double(newX, newY);
    }
    
    private void checkBounds(double speed) {
	
    }
}
