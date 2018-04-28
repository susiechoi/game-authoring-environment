package engine.sprites.properties;

import java.awt.Point;

import engine.sprites.towers.projectiles.Projectile;

/**
 * This class handles projectile movement for boomerang type projectiles.
 * @author milestodzo
 *
 * @param <Projectile>
 */

public class Boomerang extends MovingProperty{
    private double tCircle = 0.0;
    private double range = 200;
    
    public Boomerang(double speed) {
	super(speed);
    }
    
    @Override
    public void move(Projectile projectile, double elapsedTime) {
	tCircle+=this.getProperty()*elapsedTime;
	double x = projectile.getX();
	double y = projectile.getY();
	projectile.getImageView().setX(range* Math.cos(tCircle) + x);
	projectile.getImageView().setY(range* Math.sin(tCircle) + y);

//	Projectile movingProjectile = (Projectile) args[0];
//	Point origin = (Point) args[1];
    }
	
}
