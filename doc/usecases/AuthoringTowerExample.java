/**
 * 
 * @author Susie Choi
 * Authoring environment view of Tower objects, which are part of the "Model" of the authoring environment
 * 
 */

package doc.usecases;
import javafx.scene.image.Image;

class AuthoringTowerExample {
	
	private String myName; 
	
	protected AuthoringTowerExample() {
		
	}
	
	protected AuthoringTowerExample(String name, Image image, double size, double x, double y, 
							double health, double effect, double range, double speed, double cost) {
		myName = name; 
	}
	
	/**
	 * Changes an aspect of a tower
	 * @param field - the aspect of the tower to be changed
	 * @param changeToValue - the new value of the field 
	 */
	protected void changeTower(String field, String changeToValue) {
		
	}

	protected boolean shouldBeAffected(String name) {
		return myName.equals(name); 
	}
	
}
