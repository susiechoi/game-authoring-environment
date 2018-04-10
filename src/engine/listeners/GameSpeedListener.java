//package engine.listeners;
//
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//
//import engine.GameEngine;
//
///**
// * ChangeListener class that will be added to the ObservableValue<Integer> Speed in Mediator
// * stateChanged will be called when the Integer is changed
// * @author ryanpond
// *
// */
//public class GameSpeedListener implements ChangeListener{
//
//    private GameEngine myGameEngine;
//    public GameSpeedListener(GameEngine engine) {
//	myGameEngine = engine;
//    }
//    
//    @Override
//    public void stateChanged(ChangeEvent e) {
//	myGameEngine.setSpeed((Integer) e.getSource());
//    }
//
//}
