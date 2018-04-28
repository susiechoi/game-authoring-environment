package doc.api;

/**
 * Interface used for controlling Game Loop during play of a game. Engine contains the Game Loop 
 * and is top-level manager of the backend Game-State.
 * 
 * @author Katherine Van Dyk 3/28/18
 *
 */
public interface Engine {
   
    /**
     * Pauses Game Loop animation so Game State stays constant
     */
    public void pause();
    
    /**
     * Starts Game Loop animation, so Game State continuously loops
     */
    public void start();
    
    /**
     * Sets Game Loop speed, to determine how fast level steps through.
     * 
     * @param speed: speed at which animation should iterate
     */
    public void setSpeed(Integer speed);
   
    /**
     * Saves current Game State to File
     */
    public void savePlay();
    
    /**
     * Updates Game State to new Level as specified in XML File
     * 
     * @param l: integer denoting level to jump to
     */
    public void jumpLevel(int l);


}
