package engine.sprites;

import java.util.ArrayList;
import java.util.List;

import engine.builders.PropertyBuilder;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;
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
    private ImageView myImageView;
    private String myImageString;
    private PropertyBuilder myPropertyBuilder;
    private List<Property> myProperties;


    /**
     * Constructor that takes in a sprite's image
     * Source for the image path fix: https://stackoverflow.com/questions/16099427/cannot-load-image-in-javafx @author susiechoi
     * 
     * @param image: tower's initial image
     * @param size: size of tower's image
     */
    public Sprite(String name, String image, double size, List<Property> properties) {
	myName = name;
	setImageString(image);
	myImageView.setPreserveRatio(true);
	myProperties = new ArrayList<>();
	myPropertyBuilder = new PropertyBuilder();
	for(Property p : properties) {
	    myProperties.add(this.makeProperty(p));
	}
    }

    /**
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
	return myImageView;
    }

    public void setImageString(String image) {
	myImageString = image;
	myImageView  = new ImageView(new Image("file:"+image, 50, 50, true, true));
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
	myImageView.setImage(newImage);
	myImageView.setPreserveRatio(true);
    }

    protected Property makeProperty(Property p) {
	return myPropertyBuilder.getProperty(p);
    }
    
    public Property getProperty(String ID) {
	for(Property property : myProperties) {
	    if(property != null && property.getName().equals(ID)) {
		return property;
	    }
	}
	return null;
    }
    
    /**
     * Handles upgrading the health of a tower
     */
    public double upgradeProperty(String name, double balance) {
	for(Property property : myProperties) {
	    if(property.getName() == name) {
		return ((UpgradeProperty) property).upgrade(balance);
	    }
	}
	return balance;
    }
    
    public List<Property> getProperties(){
	return myProperties;
    }
    
    public void addProperty(Property property) {
	Property toRemove = null;
	try {
	    for(Property p : myProperties) {
		    if(property.getName().equals(p.getName())) {
			toRemove = p;
		    }
		}
		myProperties.remove(toRemove);
		myProperties.add(property);
	}catch(NullPointerException e){
	    return;
	}
	
    }
    
    public double getValue(String ID) {
	for(Property property : myProperties) {
	    if(property.getName().equals(ID)) {
		return property.getProperty();
	    }
	}
	return 0;
    }
    
    /**
     * Returns the superclass of name 'type' (i.e MovingProperty, CollisionProperty, etc)
     * @param type
     * @return
     */
    public Property getPropertySuperclassType(String type) {
	for(Property p : this.getProperties()) {
		if(p.getClass().getSuperclass().getSimpleName().equals(type)) {
		    return p;
		}
	}
	return null;
    }

}
