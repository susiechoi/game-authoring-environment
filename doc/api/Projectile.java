package api;

//import engine.physics.Physics;

/**
 * 
 * @author Ben Hodgson 3/28/18
 *
 */
public interface Projectile extends Physics {
    
    /**
     * Specifies the effect of the projectile
     */
    public void launch();
    
    /**
     * Checks to see if the Projectile hits an Enemy
     */
    public void hitsEnemy();

}
