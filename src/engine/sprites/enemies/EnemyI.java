package engine.sprites.enemies;

import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 * Interface for enemy functionality
 */
public interface EnemyI {
    
    /**
     * Handles when the Enemy is hit by a tower
     * 
     * @param projectile: the projectile that hit the enemy
     * @return : returns true if the enemy is still alive, false if it is dead
     */
    public boolean getHitBy(Projectile projectile);
    
    /**
     * Handles updating the enemy position to follow the path
     */
    public void followPath();
    
    /**
     * Handles returning an enemy's damage after hitting a tower
     * 
     * @return Double: damage that Enemy incurs on the tower
     */
    public Double damage();

    boolean overlap(ImageView otherImage);

}
