package engine;

import authoring.AuthoringModel;
/**
 * 
 * @author milestodzo
 *
 */
public class EngineController {
    private AuthoringModel myGameModel;
    private PlayState myPlayState;

    public EngineController(AuthoringModel gameModel, PlayState playState) {
	myGameModel = gameModel;
	myPlayState = playState;
    }

    /**
     * Pauses Game Loop animation so Game State stays constant
     */
    public void pause() {
	
    }

    /**
     * Starts Game Loop animation, so Game State continuously loops
     */
    public void start() {
	
    }

    /**
     * Sets Game Loop speed, to determine how fast level steps through.
     * 
     * @param speed: speed at which animation should iterate
     */
    public void setSpeed(Integer speed) {
	
    }

    /**
     * Saves current Game State to File
     */
    public void savePlay() {
	
    }

    /**
     * Updates Game State to new Level as specified in XML File
     * 
     * @param l: integer denoting level to jump to
     */
    public void jumpLevel(int l) {
	
    }

    public void setLevel(PlayState newLevel) {
	// TODO Auto-generated method stub

    }

    public void restartLevel() {
	// TODO Auto-generated method stub

    }

}
