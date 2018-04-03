package engine.sprites.enemies;

import engine.physics.Intersector;
import engine.sprites.Sprite;
import engine.sprites.towers.projectiles.ProjectileInterface;
import javafx.scene.image.ImageView;

/**
 * This is used for the Enemy object in the game. It will use composition to implement moveable
 * and intersectable methods.
 * 
 * @author ryanpond
 *
 */
public class Enemy extends Sprite implements EnemyI{

	private int myHealth;
	private int myDamageInflicted;
	private int rewardForKillingMe;
	private Intersector myIntersector;
	
	public Enemy(ImageView myImage, int maxHealth, int damageInflicted, int rewardForKilling ) {
	    	super(myImage);
		myHealth = maxHealth;
		myDamageInflicted = damageInflicted;
		rewardForKillingMe = rewardForKilling;
		myIntersector = new Intersector(myImage);
		
	}

	@Override
	public void move(int newX, int newY) {
		myImage.setX(newX);
		myImage.setY(newY);
	}

	@Override
	public void rotate(double angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean overlap(ImageView otherImage) {
		return myIntersector.overlap(otherImage);
	}

	@Override
	public boolean getHitBy(ProjectileInterface projectile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void followPath() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Double damage() {
	    // TODO Auto-generated method stub
	    return null;
	}

}
