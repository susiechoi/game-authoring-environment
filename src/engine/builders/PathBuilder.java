/*
 * Author: Erik Riis
 */

package engine.builders;

import java.util.List;

import engine.path.Path;
import javafx.geometry.Point2D;

public class PathBuilder {
	public Path construct(int level, List<Point2D> coordinates) {
		Path newPath = new Path(coordinates);
		return newPath;
	}
}
