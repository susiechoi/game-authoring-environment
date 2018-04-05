package engine.sprites.enemies;

import engine.physics.IIntersecter;
import engine.sprites.Sprite;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.ImageView;

/**
 * This is used for the Enemy object in the game. It will use composition to implement
 * intersectable methods.
 * 
 * @author ryanpond
 *
 */
public class Enemy extends Sprite implements EnemyI{

	private HealthProperty myHealth;
	private DamageProperty myDamage;
	private ValueProperty myValue;
	private IIntersecter myIntersecter;

	public Enemy(ImageView myImage, HealthProperty health, DamageProperty damage, ValueProperty value) {
		super(myImage);
		//myIntersector = new Intersector(myImage); //there is no implementation for intersecter yet -bma
		myHealth = health;
		myDamage = damage;
		myValue = value;
	}

	@Override
	public boolean overlap(ImageView otherImage) {
		//return myIntersector.overlap(otherImage); //there is no implementation for intersecter yet -bma
		
		return false; // TODO return the right thing
	}

	@Override
	public boolean getHitBy(Projectile projectile) { // I don't think this is supposed to return a boolean -bma
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void followPath() {
		// TODO Auto-generated method stub

	}

	public Double damage() {
		// TODO Auto-generated method stub
		return null;
	}



}
