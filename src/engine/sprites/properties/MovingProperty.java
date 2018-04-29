package engine.sprites.properties;

import engine.sprites.towers.projectiles.Projectile;

/**
 * Abstract superclass whose subclasses will specify the move methods for different patterns of projectile movement.
 * @author milestodzo
 *
 */

public abstract class MovingProperty extends UpgradeProperty{
    
    public MovingProperty(double range) {
	super(0,0,range);
      }
    
    public abstract void move(Projectile projectile, double elapsedTime);
}
