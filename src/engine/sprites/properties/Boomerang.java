package engine.sprites.properties;

import java.awt.Point;

import engine.sprites.towers.projectiles.Projectile;

/**
 * This class handles projectile movement for boomerang type projectiles.
 * @author milestodzo
 *
 * @param <Projectile>
 */

public class Boomerang<Projectile> extends MovingProperty<Object>{
    
    public Boomerang(double range) {
	super(range);
    }
    
    
    @Override
    public Projectile execute(Object...args) {
	Projectile movingProjectile = (Projectile) args[0];
	Point origin = (Point) args[1];
	return null;
    }
	
}
