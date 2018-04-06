package engine;

import engine.level.Level;

/**
 * Interface for interacting with current state of the Game, including collection/placement of Towers, as well
 * as the shooters they release and all spawned enemies. 
 * 
 * @author Katherine Van Dyk 3/28/18
 *@author Miles Todzo 4/3/18
 */
public interface PlayStateI {

    /**
     * Checks for intersections within all moving Towers, Enemies, and Projectiles, and spawns
     * new enemies/projectiles. Progression is controlled by Game Loop.
     */
    public void update();
    
    /**
     * Sets the current level for the game. From the level, data regarding available towers, enemy waves, etc.
     */
    public void setLevel(Level newLevel);

}
