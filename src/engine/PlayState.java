package engine;

import engine.managers.EnemyManager;
import engine.managers.TowerManager;
import engine.sprites.towers.Tower;
import engine.sprites.towers.TowerI;

/**
 * This class will hold all of the current Play information, such as the list of objects on the screen (enemies,
 * towers, projectiles, etc). Update method will be called every game loop to update the position of every object. 
 * @author ryanpond
 * @author Ben Hodgson 4/3/18
 * @author benauriemma 4/5
 *
 */
public class PlayState implements PlayStateI{
    
    private final TowerManager TOWERS = new TowerManager();
    private final EnemyManager ENEMIES = new EnemyManager();
    
	public PlayState() {
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TowerI placeTower(String type, int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sellTower(Tower towerToBeSold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upgradeTower(Tower towerToBeUpgraded) {
		// TODO Auto-generated method stub
		
	}

}
