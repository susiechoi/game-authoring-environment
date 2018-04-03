package engine.enemies;

import engine.physics.Intersect;
import engine.physics.Movable;
import engine.towers.projectiles.Projectile;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 * Interface for enemy functionality
 */
public interface EnemyI extends Movable, Intersect {
    
    /**
     * Handles when the Enemy is hit by a tower
     * 
     * @param projectile: the projectile that hit the enemy
     */
    public boolean getHitBy(Projectile projectile);
    
    /**
     * Handles updating the enemy position to follow the path
     */
    public void followPath();
    
    /**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs
     */
    public Double damage();
    
    /**
     * Updates the properties of the enemy. For example, health, position, etc.
     */
    public void update();

}
