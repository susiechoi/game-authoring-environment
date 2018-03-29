package towers.projectiles;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 */
public interface Projectile {
    
    /**
     * Specifies the effect of the projectile
     */
    public void launch();
    
    /**
     * Checks to see if the Projectile hits an Enemy
     */
    public void hitsEnemy();

}
