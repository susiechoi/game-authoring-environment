package engine.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.sprites.towers.Tower;


/**
 * Tower manager uses composite design pattern to handle updating all 
 * active Tower objects in the game loop.
 * 
 * @author Katherine Van Dyk
 * @author Ryan Pond
 * @author Ben Hodgson 4/3/18
 *
 */
public class TowerManager extends Manager<Tower> {

	private final List<Tower> AVAILABLE = new ArrayList<Tower>();
	private final List<Tower> ACTIVE = new ArrayList<Tower>();

	/**
	 * Constructor for super class
	 */
	public TowerManager(List<Tower> towers) {
		super(towers);
	}

	/**
	 * 
	 * @return List<Tower>: an unmodifiableList of AVAILABLE towers
	 */
	public List<Tower> unmodifiableAvailableList() {
		return Collections.unmodifiableList(AVAILABLE);
	}

	/**
	 * 
	 * @return List<Tower>: an unmodifiableList of ACTIVE towers
	 */
	public List<Tower> unmodifiableActiveList() {
		return Collections.unmodifiableList(ACTIVE);
	}

	/**
	 * Sets the AVAILABLE field in the TowerManager to @param towers. Performs a deep copy of 
	 * each individual tower and places them in AVAILABLE.
	 * 
	 * @param towers: towers taken from the AuthoringModel that are available in the game
	 */
	public void setTowers(List<Tower> towers) {
		for (Tower gameTower : towers) {
			// TODO perform the deep copy
			Tower copyTower = new Tower(null, null, 0, 0);
			AVAILABLE.add(copyTower);
		}
	}

	/**
	 * Adds a tower to the list of ACTIVE Tower objects.
	 * 
	 * @param tower: A tower that is Activated in the game
	 */
	public void setActive(Tower tower) {
		ACTIVE.add(tower);
	}

}
