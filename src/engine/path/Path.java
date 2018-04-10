package engine.path;

import java.util.List;

import javafx.geometry.Point2D;

import engine.physics.SnapMover;

/**
 * Class for constructing path and determining next coordinates
 * 
 * @author Katherine Van Dyk 4/8/18
 */
public class Path {
    private final double THRESHOLD = 5;
    private List<Point2D> myCoordinates;
    private Point2D currentPosition;
    private double myAngle;
    private int pathIndex;
   
    public Path(List<Point2D> coordinates) {
    		myCoordinates = coordinates;
    }

    /**
     * Adds a start to the path. A path start point represents where enemies spawn.
     */
    public void addCoords(double speed){
    	
	pathIndex = 0;

	currentPosition = myCoordinates.get(pathIndex);
	myAngle = getAngle(myCoordinates.get(pathIndex), myCoordinates.get(pathIndex+1));
    }

    /**
     * Returns the next position of the object according to its speed
     * 
     * @param mySpeed
     */
    public Point2D nextPosition(double elapsedTime, double speed) {
	if(checkBounds()) {
	    currentPosition = myCoordinates.get(pathIndex++);
	    myAngle = getAngle(currentPosition, myCoordinates.get(pathIndex + 1));
	    return currentPosition;
	}
	else {
	    double newX = currentPosition.getX() + Math.cos(myAngle) * speed * elapsedTime;
	    double newY = currentPosition.getY() + Math.sin(myAngle) * speed * elapsedTime;
	    return new Point2D(newX, newY); 
	}
    }

    /**
     * Checks if current position is within the bounds of the next path turn
     * 
     * @return boolean: True if within range of next path coordinate, false otherwise
     */
    private boolean checkBounds() {
	double xDistance = Math.pow(myCoordinates.get(pathIndex+1).getX() - currentPosition.getX(), 2);
	double yDistance = Math.pow(myCoordinates.get(pathIndex+1).getY() - currentPosition.getY(), 2); 
	return Math.sqrt(xDistance + yDistance) < THRESHOLD;
    }

    /**
     * Returns a new angle for the image
     * 
     * @param point1: initial point
     * @param point2: next point
     * @return double representing angle
     */
    private double getAngle(Point2D point1, Point2D point2) {
	double deltaY = point2.getY() - point1.getY();
	double deltaX = point2.getX() - point1.getX();
	return Math.atan(deltaY/deltaX);
    }
    
}
