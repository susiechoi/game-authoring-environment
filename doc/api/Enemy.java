package api;

import engine.physics.IntersectInterface;
import engine.physics.Movable;
import engine.sprites.towers.projectiles.ProjectileInterface;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 * Interface for enemy functionality
 */
public interface Enemy extends Movable, IntersectInterface {
    
    /**
     * Handles when the Enemy is hit by a tower
     * 
     * @param projectile: the projectile that hit the enemy
     */
    public void getHitBy(ProjectileInterface projectile);
    
    /**
     * Handles updating the enemy position to follow the path
     */
    public void followPath();
    
    /**
     * Updates the properties of the enemy. For example, health, position, etc.
     */
    public void update();

}
