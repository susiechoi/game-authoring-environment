/**
 * 
 * @author Susie Choi 
 * Represents the Model component of the Authoring environment's MVC. 
 * Receives input from Controller through applyChange method regarding what/how to change an object
 * 
 */

package usecases;

import java.util.ArrayList;

class AuthoringModelExample {

	private ArrayList<AuthoringTowerExample> myTowers; 
	
	protected AuthoringModelExample() {
		myTowers = new ArrayList<AuthoringTowerExample>(); 
	}
	
	/**
	 * Causes change to appropriate field of appropriate component of Model 
	 * @param affectedObject - the name of the affected object
	 * @param fieldToChange - field of the object that is to be changed
	 * @param changeToValue - value to which the fieldToChange should be changed
	 */
	protected void applyChange(String affectedObject, String fieldToChange, String changeToValue) {
		for (AuthoringTowerExample tower : myTowers) {
			if (tower.shouldBeAffected(affectedObject)) {
				tower.changeTower(fieldToChange, changeToValue);
			}
		}
	}
	
}
