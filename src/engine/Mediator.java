package engine;

import gameplayer.ScreenManager;
import xml.XMLFactory;

/**
 * This class serves as a bridge between the front end, back end, and file I/O of our game player
 * Each of these areas holds an instance of this 
 * @author benauriemma 4/5
 *
 */
public class Mediator {
    
    private XMLFactory myXMLFactory;
    private ScreenManager myScreenManager;
    private GameEngine myGameEngine;
    
    public Mediator() {
	myXMLFactory = null;
	myScreenManager = null;
	myGameEngine = null;
    }

    void savePlay() {
	//TODO ask Engine for a PlayState and then ask XMLFactory for
	//	a writer and then write it to a file
    }
    
    // a whole slew of other methods
    // but fr there should be a method for every event that can occur
    
}
