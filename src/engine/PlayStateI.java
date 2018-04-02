package engine;

import engine.towers.Tower;
import engine.towers.TowerI;

/**
 * Interface for interacting with current state of the Game, including collection/placement of Towers, as well
 * as the shooters they release and all spawned enemies. 
 * 
 * @author Katherine Van Dyk 3/28/18
 *
 */
public interface PlayStateI {

    /**
     * Checks for intersections within all moving Towers, Enemies, and Projectiles, and spawns
     * new enemies/projectiles. Progression is controlled by Game Loop.
     */
    public void update();

    /**
     * Adds back-end tower to collection based on user drag and drop. Given type of tower (value tag in XML),
     * as well as initial tower x/y positions (top-left of image).
     * 
     * @param type: type of tower, as specified in the tower tag of XML game data file.
     * @param x: x-position of top-left coordinate of image
     * @param y: y-position of top-left coordinate of image
     */
    public TowerI placeTower(String type, int x, int y);
    
    /**
     * Prompts user to release tower from collection and add value to current points within GameState.
     * 
     * @param Tower: The Tower to be sold
     */
    public void sellTower(Tower towerToBeSold);
    
    /**
     * Prompts user to upgrade tower
     * 
     * @param Tower: The tower that is going to be upgraded
     */
    public void upgradeTower(Tower towerToBeUpgraded );

}
