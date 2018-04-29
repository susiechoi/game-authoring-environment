package engine.sprites.properties;

import java.util.Random;

import engine.sprites.towers.projectiles.Projectile;

/**
 * Class will randomly shoot a random projectile
 * @author ryanpond
 *
 */
public class RandomShotProperty extends MovingProperty{

    private boolean hasSetPath;
    
    public RandomShotProperty(double range) {
	super(range);
	hasSetPath = false;
    }
    
    public RandomShotProperty(Property p) {
	super(p.getProperty());
	hasSetPath = false;
    }

    @Override
    public void move(Projectile projectile, double elapsedTime) {
	System.out.println("random shooting");
	if(!hasSetPath) {
	    rotate(projectile);
	}
	double totalDistanceToMove = projectile.getSpeed()*elapsedTime;
	double xMove = Math.sin(Math.toRadians(projectile.getRotate()))*totalDistanceToMove;
	double yMove = Math.cos(Math.toRadians(projectile.getRotate()))*totalDistanceToMove;
	projectile.getImageView().setX(projectile.getX()+xMove);
	projectile.getImageView().setY(projectile.getY()+yMove);
    }
    
    private void rotate(Projectile projectile) {
	hasSetPath = true;
	Random rand = new Random();
	double angleToRotateRads = rand.nextInt(360);
	projectile.setRotate(Math.toDegrees(angleToRotateRads));
    }

}
