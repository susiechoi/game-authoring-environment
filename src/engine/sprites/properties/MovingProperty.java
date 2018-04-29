package engine.sprites.properties;

import engine.sprites.towers.projectiles.Projectile;

/**
 * Abstract superclass whose subclasses will specify the move methods for different patterns of projectile movement.
 * @author milestodzo
 *
 */

public abstract class MovingProperty extends Property{
    
    public MovingProperty(double range) {
	super(range);
      }
    
    public abstract void move(Projectile projectile, double elapsedTime);
}
