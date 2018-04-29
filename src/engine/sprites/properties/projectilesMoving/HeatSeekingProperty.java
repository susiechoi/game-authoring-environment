package engine.sprites.properties.projectilesMoving;

import engine.sprites.ShootingSprites;
import engine.sprites.properties.Property;
import engine.sprites.towers.projectiles.Projectile;

/**
 * This class will handle the movement of a projectile that is heat seeking 
 * (Follows the enemy it is shot at)
 * @author ryanpond
 *
 */
public class HeatSeekingProperty extends MovingProperty{

    public HeatSeekingProperty(double range) {
	super(range);
    }
    
    public HeatSeekingProperty(Property p) {
	super(p.getProperty());
    }

    @Override
    public void move(Projectile projectile, double elapsedTime) {
	System.out.println("heat seeking");
	ShootingSprites myTarget = projectile.getTarget();
	if (myTarget.isAlive()) {
	    rotateImage(projectile, myTarget);
	}
	double totalDistanceToMove = projectile.getSpeed()*elapsedTime;
	double xMove = Math.sin(Math.toRadians(projectile.getRotate()))*totalDistanceToMove;
	double yMove = Math.cos(Math.toRadians(projectile.getRotate()))*totalDistanceToMove;
	projectile.getImageView().setX(projectile.getX()+xMove);
	projectile.getImageView().setY(projectile.getY()+yMove);
    }

    /**
     * Rotates the image to face the target
     */
    private void rotateImage(Projectile projectile, ShootingSprites myTarget) {
	double xDifference = myTarget.getX() - projectile.getX();
	double yDifference = myTarget.getY() - projectile.getY();
	double angleToRotateRads = Math.atan2(xDifference,yDifference);
	projectile.setRotate(Math.toDegrees(angleToRotateRads));
    }

    
    
    
    

}
