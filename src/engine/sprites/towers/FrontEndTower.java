package engine.sprites.towers;

import java.util.Map;
import javafx.scene.image.ImageView;

/**
 * Interface Gameplayer will use to populate tower-specific panels, and to trigger upgrades of tower
 * @author andrewarnold
 *
 *NOTE: let me know if you want us to pass any other information on our method calls
 */
public interface FrontEndTower {
    
    /**
     * Needed to add the ImageView to the Panel
     * @return The towers ImageView
     */
    public ImageView getImageView();
    
    /**
     * Method to get information about the tower, was thinking the key would be the 
     * type of statistic (health, damage, rate-of-fire, etc) and then the map value would be
     * the value of the statistic. NOTE: should include sell price of tower
     * @return
     */
    public Map<String,Double> getTowerStats();
    
	/**
	 * 
	 * @return The name (type) of the tower
	 */
    public String getName();
    
    /**
     * Sell tower
     * @return updated resources value
     */
    public int sell();

    /**
     * 
     * @param myResources
     * @return Updated resources value
     * @throws CannotAffordException 
     */
	public int purchase(int myResources) throws CannotAffordException;
    
    /**
     * Method to get list of available upgrades, map key would be upgrade type so we can assign
     * a particular image to the icon (health, damage, rate-of-fire, etc) [would be nice to have these
     * standardized between this and getTowerStats but not required] and the map value would be the cost
     * @return Map of the available upgrades
     */
//    public Map<String,Double> getUpgrades();
//    
//    /**
//     * Method to request information/statistics on a specific upgrade for population of UpgradePanel
//     *  $$Return doesn't need to be a string, I'm happy to parse out whatever you give me if the info is there$$
//     * @param upgradeName the type of upgrade, will be the value given as a key from getUpgrades()
//     * @return upgrade specifics
//     */
//    public String getSpecificUpgradeInfo(String upgradeName);
//    
//    /**
//     * Triggers the actual update
//     * @param upgradeName	which upgrade to trigger, will be the value given as a key 
//     * 				from getUpgrades()
//     * @return if upgrade was successful (did user have enough money)
//     */
//    public boolean upgrade(String upgradeName);
    
}
