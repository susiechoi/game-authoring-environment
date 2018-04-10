/*
 * Author: Erik Riis
 */

package engine.builders;

import java.awt.geom.Point2D;
import java.util.List;

import engine.path.Path;

public class PathBuilder {
	public Path construct(int level, List<Point2D> coordinates) {
		Path newPath = new Path(coordinates);
		return newPath;
	}
}
