package engine.sprites.towers.projectiles;

import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import javafx.scene.image.ImageView;

public class Projectile extends Sprite {

	private DamageProperty myDamage;
	private ImageView myImage; // this is repeated in the superclass sprite -ben
	
	public Projectile(DamageProperty damage, ImageView image) {
	    	super(image);
	    	myImage = super.getImage();
		myDamage = damage;
	}


	public void move(double newX, double newY) {
		// TODO fill this out with delegation to a mover
	}
	
	public double upgradeDamage(double balance) {
	    return myDamage.upgrade(balance);
	}

}
