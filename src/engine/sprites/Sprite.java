package engine.sprites;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

/**
 * Interface for an actor in the current Game. All game objects are sprites and have images
 * and IDs, as well as methods for moving them and checking their intersections with other 
 * objects.
 * 
 * @author Katherine Van Dyk
 * @date 4/3/18
 * @author Miles Todzo
 */

public class Sprite  {
	private ImageView myImageView;


	/**
	 * Constructor that takes in a sprite's image
	 * Source for resizing image: https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
	 * 
	 * @param image: tower's initial image
	 * @param size: size of tower's image
	 */
	public Sprite(Image image) {
		myImageView = new ImageView(image);
		myImageView.setPreserveRatio(true);
	}

	/**
	 * Returns each Sprite's image
	 * 
	 * @return ImageView representing game object's image
	 */
	public ImageView getImageView() { 
		return myImageView;
	}

	public void setImageView(ImageView image) {
		myImageView  = image;
	}

	public void place(double newX, double newY) {
		myImageView.setX(newX);
		myImageView.setY(newY);
	}

	public void handleCollision() {
		// TODO Auto-generated method stub

	}
}
