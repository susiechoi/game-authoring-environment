package engine.listeners;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import engine.GameEngine;

/**
 * ChangeListener class that will be added to the ObservableValue<Integer> Level in Mediator
 * stateChanged will be called when the Integer is changed
 * @author ryanpond
 *
 */
public class LevelChangeListener implements ChangeListener{

    private GameEngine myEngine;
    
    public LevelChangeListener(GameEngine engine) {
	myEngine = engine;
    }
    
    @Override
    public void stateChanged(ChangeEvent e) {
	int newLevel = (int) e.getSource();
	
    }
    
    

}
