package engine.path;

import java.util.List;
import java.util.Map;
import java.awt.Point;
import java.awt.geom.Point2D;


/**
 * Class for constructing path and determining next coordinates
 * 
 * @author Katherine Van Dyk 4/8/18
 */
public class Path {
	private final double THRESHOLD = 5;
	private List<Point> myCoordinates;
	private Point currentPosition;
	private double myAngle;
	private int pathIndex;
	private int myPathSize;
	private Map<String, List<Point>> myPathMap;
	private String myBackgroundImage;

	public Path(List<Point> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage, int pathSize) {
		myCoordinates = coordinates;
		myBackgroundImage = backgroundImage;
		myPathSize = pathSize;
		pathIndex = 0;
		currentPosition = myCoordinates.get(pathIndex);
		myAngle = getAngle(myCoordinates.get(pathIndex), myCoordinates.get(pathIndex+1));
		myPathMap = imageCoordinates;
	}


	/**
	 * Returns the next position of the object according to its speed
	 * 
	 * @param mySpeed
	 */
	public Point2D nextPosition(double speed) {
		if(checkBounds()) {
			currentPosition = myCoordinates.get(pathIndex++);
			myAngle = getAngle(currentPosition, myCoordinates.get(pathIndex + 1));
			return currentPosition;
		}
		else {
			double newX = currentPosition.getX() + Math.cos(myAngle) * speed;
			double newY = currentPosition.getY() + Math.sin(myAngle) * speed;
			return new Point2D.Double(newX, newY); 
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
    private double getAngle(Point point1, Point point2) {
	double deltaY = point2.getY() - point1.getY();
	double deltaX = point2.getX() - point1.getX();
	return Math.atan(deltaY/deltaX);
    }
    
    public Map<String, List<Point>> getPathMap() {
    		return myPathMap;
    }
    
    public String getBackgroundImage() {
    		return myBackgroundImage;
    }
    
    public int getPathSize() {
    		return myPathSize;
    }

}
