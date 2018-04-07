package engine.sprites.towers;

import java.util.Map;
import javafx.scene.image.ImageView;

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
    public Map<String,String> getTowerStats();
    
    /** (OPTIONAL DEPENDING ON WHERE YOU WANT THIS CALL TO BE MADE)
     * Method to sell tower(optional could be a Mediator call if needed, however you will need a
     * way to identify the tower, maybe position or a towerID[don't love towerID idea])
     * @return if sell was successful(don't really know why it wouldn't be)
     */
    public boolean sell();
    
    /**
     * Method to get list of available upgrades, map key would be upgrade type so we can assign
     * a particular image to the icon (health, damage, rate-of-fire, etc) [would be nice to have these
     * standardized between this and getTowerStats but not required] and the map value would be the cost
     * @return Map of the available upgrades
     */
    public Map<String,Double> getUpgrades();
    
    /**
     * Method to request information/statistics on a specific upgrade for population of UpgradePanel
     *  $$Return doesn't need to be a string, I'm happy to parse out whatever you give me if the info is there$$
     * @param upgradeName the type of upgrade, will be the value given as a key from getUpgrades()
     * @return upgrade specifics
     */
    public String getSpecificUpgradeInfo(String upgradeName);
    
    /**
     * Triggers the actual update
     * @param upgradeName	which upgrade to trigger, will be the value given as a key 
     * 				from getUpgrades()
     * @return if upgrade was successful (did user have enough money)
     */
    public boolean upgrade(String upgradeName);
    
}
