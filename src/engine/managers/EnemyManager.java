package engine.managers;

import java.util.List;

import engine.sprites.enemies.Enemy;

/**
 * Enemy manager uses composite design pattern to handle updating all 
 * active Enemy objects in the game loop.
 * 
 * @author Katherine Van Dyk
 *
 */
public class EnemyManager extends Manager<Enemy> {
    
    /**
     * Constructor for Enemy manager
     */
    public EnemyManager(List<Enemy> enemies) {
    		super(enemies);
    }
    
    
    
    

}
