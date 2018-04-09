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
 * @author Ben Hodgson 4/8/18
 */

public class Sprite  {
<<<<<<< HEAD
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
=======
    private String myName;
    private ImageView myImageView;


    /**
     * Constructor that takes in a sprite's image
     * Source for resizing image: https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
     * 
     * @param image: tower's initial image
     * @param size: size of tower's image
     */
    public Sprite(String name, Image image) {
	myName = name;
	myImageView = new ImageView(image);
	myImageView.setPreserveRatio(true);
    }
    
    /**
     * Return the String name of the sprite.
     * 
     * @return String: the name of the Sprite
     */
    public String getName() {
	return myName;
    }
    
    /**
     * Returns each Sprite's image
     * 
     * @return ImageView representing game object's image
     */
    public ImageView getImage() { 
	return myImageView;
    }
    
    public void setImage(ImageView image) {
	myImageView  = image;
    }
    
    public void place(double newX, double newY) {
	myImageView.setX(newX);
	myImageView.setY(newY);
    }
    
    // TODO Should this method go in the sprite object? Need to specify that it is projectiles we're dealing with in order to get their damage
    public void checkForCollision(ShootingSprites shooter, ObservableList<Sprite> projectiles) {
    		shooter.checkTowerEnemyCollision((ShootingSprites) this); 
    		for (Sprite projectile: projectiles) {
    			ImageView spriteImageView = projectile.getImage();
    			if(this.myImageView.intersects(spriteImageView.getX(), spriteImageView.getY(), spriteImageView.getFitWidth(), spriteImageView.getFitHeight())){
    			//	this.handleCollision(projectile.getDamage());
    				projectile.handleCollision();
    			}
    		}
    }
>>>>>>> d835ca910fa7448a9ca0a2acb5bcfe890a7c41e2

	public void handleCollision() {
		// TODO Auto-generated method stub

	}
}
