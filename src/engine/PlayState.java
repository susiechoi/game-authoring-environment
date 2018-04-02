package engine;

import engine.towers.Tower;
import engine.towers.TowerI;

/**
 * This class will hold all of the current Play information, such as the list of objects on the screen (enemies,
 * towers, projectiles, etc). Update method will be called every game loop to update the position of every object. 
 * @author ryanpond
 *
 */
public class PlayState implements PlayStateI{

	public PlayState() {
		// TODO Auto-generated constructor stub
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
