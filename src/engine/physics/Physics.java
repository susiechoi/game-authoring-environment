package engine.physics;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface for handling the projectile physics
 */
public interface Physics {
    
    /**
     * Calculates a curved path for the projectile to follow as it moves
     * 
     * @param speed: the projectiles speed
     * @param xStart: the projectiles starting x position
     * @param yStart: the projectiles starting y position
     */
    public void getCurve(int speed, int xStart, int yStart);

}
