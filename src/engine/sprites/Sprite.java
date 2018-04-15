package engine.sprites;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Interface for an actor in the current Game. All game objects are sprites and have images
 * and IDs, as well as methods for moving them and checking their intersections with other 
 * objects.
 * 
 * @author Katherine Van Dyk 4/3/18
 * @author Ryan Pond
 * @author Miles Todzo
 * @author Ben Hodgson 4/8/18
 */
public class Sprite implements FrontEndSprite{

    private String myName;
    private String myImageString;
    private double myX;
    private double myY;
    private double myRotate;
    @XStreamOmitField
    private ImageView myImageView;
  


    /**
     * Constructor that takes in a sprite's image
     * Source for resizing image: https://stackoverflow.com/questions/27894945/how-do-i-resize-an-imageview-image-in-javafx
     * 
     * @param image: tower's initial image
     * @param size: size of tower's image
     */
    public Sprite(String name, String image, double size) {
	myName = name;
	myImageString = image;
	//myImageView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(image), 50, 50, true, true));
	//myImageView.setPreserveRatio(true);

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
    public ImageView getImageView() { 
	ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(myImageString), 50, 50, true, true));
	imageView.setX(myX);
	imageView.setY(myY);
	imageView.setRotate(myRotate);
	imageView.setPreserveRatio(true);
	return imageView;
    }
    
    @Deprecated
    public void setImage(Image image) {
	myImageView  = new ImageView(image);
    }
    
    public void place(double newX, double newY) {
	setX(newX);
	setY(newY);
    }
    
    // TODO Should this method go in the sprite object? Need to specify that it is projectiles we're dealing with in order to get their damage
//    public void checkForCollision(ShootingSprites shooter, ObservableList<Sprite> projectiles) {
//    		shooter.checkTowerEnemyCollision((ShootingSprites) this); 
//    		for (Sprite projectile: projectiles) {
//    			ImageView spriteImageView = projectile.getImageView();
//    			if(this.myImageView.intersects(spriteImageView.getX(), spriteImageView.getY(), spriteImageView.getFitWidth(), spriteImageView.getFitHeight())){
//    			//	this.handleCollision(projectile.getDamage());
//    				projectile.handleCollision();
//    			}
//    		}
//    }
    
    /**
     * @return Angle of the sprite
     */
    public double getRotate() {
    		return myRotate;
    }
    private void setX(double newX) {
    		myX = newX;
    }
    private void setY(double newY) {
		myY = newY;
    }
    /**
     * @return X-coordinate of the sprite
     */
    public double getX() {
    		return myX;
    }
    
    /**
     * @return Y-coordinate of the sprite
     */ 
    public double getY() {
    		return myY;
    }

    /**
     * Sets the shooting sprite's angle to @param rotateVal
     */
    public void setRotate(double rotateVal) {
    		myRotate = rotateVal;
    }
    /**
     * Returns the damage that this sprite inflicts on something (Can be enemy's damage, projectiles damage, etc)
     * @return
     */
    public Double getDamage() {
	return (double) 0;
    }
    
    public String getImageString() {
	return myImageString;
    }
    
    /**
     * @return : true if the sprite is alive, false if it is dead
     * Can be overridden in subclasses if a collision affects them
     */
    public boolean handleCollision(Sprite collider) {
	return false;
    }

}
