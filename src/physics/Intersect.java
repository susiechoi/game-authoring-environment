package physics;

import javafx.scene.image.Image;

/**
 * 
 * @author Ben Hodgson 3/29/18 
 *
 * Interface for determining image/object intersections 
 */
public interface Intersect {
    
    /**
     * Method for determining if two images overlap
     * 
     * @param otherImage: 
     * @return boolean: true if the images are overlapping, false otherwise
     */
    public boolean overlap(Image otherImage);

}
