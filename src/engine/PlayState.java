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
public class PlayState implements PlayStateI {

    private final TowerManager TOWERS = new TowerManager();
    private final EnemyManager ENEMIES = new EnemyManager();

    public PlayState() {

    }

    @Override
    public void update() {
	// TODO Auto-generated method stub
    }

    @Override
    public void setLevel(Level newLevel) {
	// TODO Auto-generated method stub

    }

}
