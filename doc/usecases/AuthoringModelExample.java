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
	
	protected void applyChange(String fieldToChange, String changeToValue) {
		myTowers.add(new AuthoringTowerExample());
		myTowers.get(0).changeTower(fieldToChange, changeToValue);
	}
	
}
