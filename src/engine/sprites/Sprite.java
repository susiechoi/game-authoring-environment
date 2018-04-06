package engine.sprites;

import java.util.List;

import javafx.scene.image.ImageView;

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
     * 
     * @param image
     */
    public Sprite(ImageView image) {
	myImageView = image;
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
    
    public void checkForCollision(ShootingSprites shooter, List<Sprite> projectiles) {
    		shooter.checkTowerEnemyCollision((ShootingSprites) this); 
    		for (Sprite projectile: projectiles) {
    			ImageView spriteImageView = projectile.getImage();
    			if(this.myImageView.intersects(spriteImageView.getX(), spriteImageView.getY(), spriteImageView.getFitWidth(), spriteImageView.getFitHeight())){
    				this.handleCollision();
    			}
    		}
    }

	public void handleCollision() {
		// TODO Auto-generated method stub
		
	}
}
