/*
 * Author: Erik Riis
 */

package engine.builders;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import engine.path.Path;

public class PathBuilder {
	public Path construct(List<List<Point>> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage, int pathSize, int col, int row) {
		Path newPath = new Path(coordinates, imageCoordinates, backgroundImage, pathSize, col, row);
		return newPath;
	}
}
