/**
 * @author susiechoi
 * Encapsulates information about the starting resources (health & money) in gameplay
 */

package authoring;

class AuthoringResources {
	
	private double myHealth;
	private double my$; 

	protected AuthoringResources(double startingHealth, double starting$) {
		myHealth = startingHealth;
		my$ = starting$; 
	}
	
}
