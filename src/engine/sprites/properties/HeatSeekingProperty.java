package engine.sprites.properties;

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
	// TODO Auto-generated method stub
	
    }
    
    
    
    

}
