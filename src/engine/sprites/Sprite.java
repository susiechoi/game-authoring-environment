package engine.sprites;

import javafx.scene.image.ImageView;

/**
 * Interface for an actor in the current Game. All game objects are sprites and have images
 * and IDs.
 * 
 * @author katherinevandyk
 *
 */
public abstract class Sprite {
    
    protected ImageView myImage;
    
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
   
}
