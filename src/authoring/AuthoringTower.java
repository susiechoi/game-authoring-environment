/**
 * 
 * Authoring environment view of Tower objects, which are part of the "Model" of the authoring environment
 * @author Susie Choi 
 * 
 */

package authoring;
import javafx.scene.image.Image;

class AuthoringTower {
	
	protected AuthoringTower(String name, Image image, double size, double x, double y, 
							double health, double effect, double range, double speed, double cost) {
		
	}
	
	/**
	 * Changes an aspect of a tower
	 * @param field - the aspect of the tower to be changed
	 * @param changeToValue - the new value of the field 
	 */
	protected void changeTower(String field, String changeToValue) {
		
	}
	
	/**
	 * 
	 * Compares the name of the object to be changed (argument) to the name of this object
	 * Useful when client needs to search for the Tower object to which to apply a change 
	 * Removes need to explicitly give out name of this Tower object
	 * @return boolean representing whether the name of the object to be affected
	 * 			is the same as the name of this object
	 * 
	 */
	protected boolean shouldBeAffected(String name) {
		return false; 
	}

}
