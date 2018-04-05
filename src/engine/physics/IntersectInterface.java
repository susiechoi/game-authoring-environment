package engine.physics;

import javafx.scene.image.ImageView;

/**
 * 
 * @author Ben Hodgson 3/29/18 
 *
 * Interface for determining image/object intersections 
 */
public interface IntersectInterface {
    
    /**
     * Method for determining if two images overlap
     * 
     * @param otherImage: 
     * @return boolean: true if the images are overlapping, false otherwise
     */
    public boolean overlap(ImageView otherImage);

}
