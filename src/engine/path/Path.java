package engine.path;

import java.util.List;
import java.util.Map;
import java.awt.Point;

/**
 * Class for constructing path and determining next coordinates
 * 
 * @author Katherine Van Dyk 4/8/18
 */
public class Path {
	private final double THRESHOLD = 5;
	private List<Point> myCoordinates;
	private double myAngle;
	private int pathIndex;
	private Map<String, List<Point>> myPathMap;
	public Path(List<Point> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage) {
		myCoordinates = coordinates;
		pathIndex = 0;
		myAngle = getAngle(myCoordinates.get(pathIndex), myCoordinates.get(pathIndex+1));
		myPathMap = imageCoordinates;
	}



	/**
	 * Returns the next position of the object according to its speed
	 * 
	 * @param mySpeed
	 */
	public Point nextPosition(Point currentPos) {
		if(checkBounds(currentPos)) {
			currentPos = myCoordinates.get(pathIndex++);
			myAngle = getAngle(currentPos, myCoordinates.get(pathIndex + 1));
			return currentPos;
		}
		else {
		    	System.out.println("CURRENT XPOS: " + currentPos.getX());
		    	System.out.println("CURRENT YPOS: " + currentPos.getY());
			double newX = currentPos.getX() + Math.cos(myAngle) * 5;
			double newY = currentPos.getY() + Math.sin(myAngle) * 5;
			currentPos.setLocation(newX, newY);
			return currentPos; 
		}
	}

	/**
	 * Checks if current position is within the bounds of the next path turn
	 * 
	 * @return boolean: True if within range of next path coordinate, false otherwise
	 */
	private boolean checkBounds(Point currentPos) {
		double xDistance = Math.pow(myCoordinates.get(pathIndex+1).getX() - currentPos.getX(), 2);
		double yDistance = Math.pow(myCoordinates.get(pathIndex+1).getY() - currentPos.getY(), 2); 
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
    
    public Point initialPoint() {
	return myCoordinates.get(0);
    }

}
