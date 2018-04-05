package engine.sprites.towers.projectiles;

import engine.physics.Physics;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 */
public interface ProjectileInterface extends Physics {
    
    /**
     * Specifies the effect of the projectile
     */
    public void move(double newX, double newY);
    
    /**
     * Checks to see if the Projectile hits an Enemy
     */
    public void hitsEnemy();
    
    /**
     * returns the damage that is inflicted upon the enemy
     * @return
     */
    public double inflictDamage();

}
