package engine.sprites.towers.projectiles;

import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import javafx.scene.image.ImageView;

public class Projectile extends Sprite implements ProjectileInterface{

	private DamageProperty myDamage;
	
	public Projectile(int damage, ImageView image) {
	    	super(image);
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
	public void move(double newX, double newY) {
	    // TODO Auto-generated method stub
	    
	}
	
	public double upgradeDamage(double balance) {
	    return myDamage.upgrade(balance);
	}

}
