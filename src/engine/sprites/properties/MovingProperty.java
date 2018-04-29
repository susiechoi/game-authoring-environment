package engine.sprites.properties;

import engine.sprites.towers.projectiles.Projectile;

/**
 * Abstract superclass whose subclasses will specify the move methods for different patterns of projectile movement.
 * @author milestodzo
 *
 */

public abstract class MovingProperty<T> extends Property<Object>{
    
    public MovingProperty(double range) {
	super(range);
      }
}
