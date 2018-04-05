package engine.sprites;

import javafx.scene.image.ImageView;

/**
 * Interface for an actor in the current Game. All game objects are sprites and have images
 * and IDs, as well as methods for moving them and checking their intersections with other 
 * objects.
 * 
 * @author Katherine Van Dyk
 * @date 4/3/18
 *
 */
public abstract class Sprite  {
    private ImageView myImage; // this is repeated in a bunch of subclasses -bma

    /**
     * Constructor that takes in a sprite's image
     * 
     * @param image
     */
    public Sprite(ImageView image) {
	myImage = image;
    }
    
    /**
     * Returns each Sprite's image
     * 
     * @return ImageView representing game object's image
     */
    public ImageView getImage() { 
	return myImage;
    }
    
    public void setImage(ImageView image) {
	myImage  = image;
    }
    
    public void place(double newX, double newY) {
	myImage.setX(newX);
	myImage.setY(newY);
    }


}
