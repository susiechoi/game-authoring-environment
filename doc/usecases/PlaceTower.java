package usecases;

import api.GameState;
import data.TowerData;

/**
 * This class simulates the design flow of when a user places a tower. It will call GameState.placeTower,
 * where a new Tower is created and then added to the Tower Set. 
 *
 */
public class PlaceTower {

	public PlaceTower() {
		UserDropsTower();
	}
	
	/**
	 * This method is used when the user drops a tower on a spot. It will call myGameState.placeTower, 
	 * and it will pass the towerType (as a string) as well as the x and y coordinates of the spot that
	 * it drops it at.
	 */
	@SuppressWarnings("null")
	private void UserDropsTower() {
		String towerType = "type";
		int xcoord = 1;
		int ycoord = 1;
		GameState myGameState = null;
		myGameState.placeTower(towerType,xcoord,ycoord);
		createTowerObject(towerType);
	}
	
	/**
	 * This method is going to be called in GameState, which has an instance of TowerData
	 * It shows the progression of calls, and how TowerData will create the tower and 
	 * then send it back to the GameState() where it will be added to the collection. 
	 * @param type
	 */
	private void createTowerObject(String type) {
		TowerData myTowerData = new TowerData();
		myTowerData.create(type);
	}
	
	

}
