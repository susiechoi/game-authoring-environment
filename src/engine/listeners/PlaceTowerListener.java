//package engine.listeners;
//
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//
//import engine.PlayState;
//import engine.sprites.towers.Tower;
//
///**
// * ChangeListener class that will be added to the ObservableValue<Tower> placeTower in Mediator
// * stateChanged will be called when the Integer is changed
// * @author ryanpond
// *
// */
//public class PlaceTowerListener implements ChangeListener{
//
//    private PlayState myPlayState;
//    
//    public PlaceTowerListener(PlayState playState) {
//	myPlayState = playState;
//    }
//    
//    @Override
//    public void stateChanged(ChangeEvent e) {
//	myPlayState.placeTower( (Tower) e.getSource());
//    }
//
//}
