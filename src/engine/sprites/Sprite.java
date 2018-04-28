package engine.sprites;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import xml.serialization.ImageWrapper;

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
    @XStreamOmitField
    private transient ImageView myImageView;
    private String myImageString;
    private ImageWrapper myWrapper;


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
	myImageView = new ImageView(new Image("file:"+image, 50, 50, true, true)); //TODO Replace w non-magic values or getresourcestream
	myImageView.setPreserveRatio(true);
	System.out.println("Created Sprite");
	myWrapper = new ImageWrapper(image);
    }

	/**
	 * Return the String name of the sprite.
	 * 
	 * @return String: the name of the Sprite
	 */
	@Override
	public String getName() {
		return myName;
	}

	/**
	 * Returns each Sprite's image
	 * 
	 * @return ImageView representing game object's image
	 */
	@Override
	public ImageView getImageView() { 
		return myImageView;
	}

	public void setImage(Image image) {
		myImageView  = new ImageView(image);
	}
	
	/**
	 * Method that allows the sprite to be updated based on its wrapper. To be used when deserializing.
	 */
	public void updateImage() {
	    myImageView = myWrapper.toImageView();
	}

	public void place(double newX, double newY) {
		myImageView.setX(newX);
		myImageView.setY(newY);
	}

	/**
	 * @return Angle of the sprite
	 */
	public double getRotate() {
		return this.myImageView.getRotate();
	}

	/**
	 * @return X-coordinate of the sprite
	 */
	public double getX() {
		return this.myImageView.getX();
	}

	/**
	 * @return Y-coordinate of the sprite
	 */ 
	public double getY() {
		return this.myImageView.getY();
	}

	/**
	 * Sets the shooting sprite's angle to @param rotateVal
	 */
	public void setRotate(double rotateVal) {
		this.myImageView.setRotate(rotateVal);
	}
	/**
	 * Returns the damage that this sprite inflicts on something (Can be enemy's damage, projectiles damage, etc)
	 * @return
	 */
	public double getDamage() {
		return 0.0;
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

	/**
	 * Returns how many points the user gets for killing this Sprite. Default
	 * set to 0
	 * @return
	 */
	public int getPointValue() {
		return 0;
	}

	protected void updateImage(String imagePath) {
		myImageString = imagePath; 
		Image newImage = new Image("file:"+imagePath, 50, 50, true, true); 
		myImageView = myWrapper.toImageView();
		myImageView.setImage(newImage);
		myImageView.setPreserveRatio(true);

	}

}
