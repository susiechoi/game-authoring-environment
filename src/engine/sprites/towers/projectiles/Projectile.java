package engine.sprites.towers.projectiles;

import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import javafx.scene.image.ImageView;

public class Projectile extends Sprite implements ProjectileInterface{

	private DamageProperty myDamage;
	private ImageView myImage;
	
	public Projectile(int damage, ImageView image) {
	    	super(image);
	    	myImage = super.getImage();
		myDamage = new DamageProperty(damage);
	}

	@Override
	public void getCurve(int speed, int xStart, int yStart) {
		
	}

	@Override
	public void hitsEnemy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double inflictDamage() {
		return myDamage.getProperty();
	}

	@Override
	public void move(double newX, double newY) {
	    myImage.setX(newX);
	    myImage.setY(newY);
	}
	
	public double upgradeDamage(double balance) {
	    return myDamage.upgrade(balance);
	}

}
