package engine.sprites.properties;

import engine.sprites.towers.projectiles.Projectile;

/**
 * Class will randomly shoot a projectile out from the tower
 * @author ryanpond
 *
 */
public class RandomShotProperty extends MovingProperty{

    public RandomShotProperty(double range) {
	super(range);
    }

    @Override
    public void move(Projectile projectile, double elapsedTime) {
	// TODO Auto-generated method stub
	
    }

}
