package engine;

import engine.level.Level;
import engine.managers.EnemyManager;
import engine.managers.TowerManager;

/**
 * This class will hold all of the current Play information, such as the list of objects on the screen (enemies,
 * towers, projectiles, etc). Update method will be called every game loop to update the position of every object. 
 * @author ryanpond
 * @author Ben Hodgson 4/3/18
 * @author benauriemma 4/5
 *
 */
public class PlayState {

    private final TowerManager TOWERS = new TowerManager();
    private final EnemyManager ENEMIES = new EnemyManager();

    public PlayState() {

    }

    /**
     * Checks for intersections within all moving Towers, Enemies, and Projectiles, and spawns
     * new enemies/projectiles. Progression is controlled by Game Loop.
     */
    public void update() {
	// TODO Auto-generated method stub
    }

    /**
     * Sets the current level for the game. From the level, data regarding available towers, enemy waves, etc.
     */
    public void setLevel(Level newLevel) {
	// TODO Auto-generated method stub

    }

}
