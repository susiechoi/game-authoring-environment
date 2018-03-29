package towers.projectiles;

import enemies.Enemy;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 */
public interface Projectile {
    
    /**
     * Specifies the effect of the projectile
     */
    public void effect(Enemy enemy);

}
