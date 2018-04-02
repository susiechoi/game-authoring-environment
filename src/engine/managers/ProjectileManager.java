package engine.managers;

import engine.towers.projectiles.Projectile;

/**
 * Projectile manager uses composite design pattern to handle updating all 
 * active Projectile objects in the game loop.
 * 
 * @author Katherine Van Dyk
 *
 */
public class ProjectileManager extends Manager<Projectile> {
    
    /**
     * Constructor from super class
     */
    public ProjectileManager() {
	super();
    }

}
