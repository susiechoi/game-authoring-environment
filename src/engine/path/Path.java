package engine.path;

import java.awt.geom.Point2D;

/**
 * Class for constructing path and determining next coordinates
 * 
 * @author Katherine Van Dyk 4/8/18
 */
public class Path {
    private final double THRESHOLD = 5;
    private Point2D[] myCoordinates;
    private Point2D currentPosition;
    private double myAngle;
    private int pathIndex;

    /**
     * Adds a start to the path. A path start point represents where enemies spawn.
     */
    public void addCoords(Point2D[] coordinates, double speed){
	myCoordinates = coordinates;
	pathIndex = 0;
	currentPosition = coordinates[pathIndex];
	myAngle = getAngle(coordinates[pathIndex], coordinates[pathIndex+1]);
    }

    /**
     * Returns the next position of the object according to its speed
     * 
     * @param mySpeed
     */
    public Point2D nextPosition(double elapsedTime, double speed) {
	if(checkBounds()) {
	    currentPosition = myCoordinates[pathIndex++];
	    myAngle = getAngle(currentPosition, myCoordinates[pathIndex + 1]);
	    return currentPosition;
	}
	else {
	    double newX = currentPosition.getX() + Math.cos(myAngle) * speed * elapsedTime;
	    double newY = currentPosition.getY() + Math.sin(myAngle) * speed * elapsedTime;
	    return new Point2D.Double(newX, newY); 
	}
    }

    /**
     * Checks if current position is within the bounds of the next path turn
     * 
     * @return boolean: True if within range of next path coordinate, false otherwise
     */
    private boolean checkBounds() {
	double xDistance = Math.pow(myCoordinates[pathIndex+1].getX() - currentPosition.getX(), 2);
	double yDistance = Math.pow(myCoordinates[pathIndex+1].getY() - currentPosition.getY(), 2); 
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
