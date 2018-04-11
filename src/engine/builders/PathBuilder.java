/*
 * Author: Erik Riis
 */

package engine.builders;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;

import engine.path.Path;

public class PathBuilder {
	public Path construct(int level, List<Point> coordinates, HashMap<String, List<Point>> imageCoordinates, String backgroundImage) {
		Path newPath = new Path(coordinates, imageCoordinates, backgroundImage);
		return newPath;
	}
}
