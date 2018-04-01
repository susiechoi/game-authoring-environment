package api;

import engine.towers.Tower;

/**
 * Interface for interacting with current state of the Game, including collection/placement of Towers, as well
 * as the shooters they release and all spawned enemies. 
 * 
 * @author Katherine Van Dyk 3/28/18
 *
 */
public interface GameState {

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
    public Tower placeTower(String type, int x, int y);
    
    /**
     * Prompts user to release tower from collection and add value to current points within GameState.
     * 
     * @param type: Type of tower being sold
     */
    public void sellTower();
    
    /**
     * Prompts user to upgrade tower
     * 
     * @param hash
     */
    public void upgradeTower();
    
    /**
     * Allows a new tower to be selected and placed by the user
     * @param tower 
     */
    public void unlockTower(Tower tower);

}
