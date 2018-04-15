package authoring.frontend;

import java.util.ArrayList;

import javafx.scene.Parent;

/**
 * Class responsible for specifying toolbar at the bottom of WaveScreen.
 * @author Sarahbland
 *
 */
public class WaveToolBar extends PathToolBar {
    public WaveToolBar(AuthoringView view) {
 	super(view);
     }
    protected void makePanel() {
	// null method for now //TODO!!
    }
    
    /**
     * Do-nothing method for now - to refactor
     * @see frontend.Screen#makeScreenWithoutStyling()
     */
    @Override
    
    public Parent makeScreenWithoutStyling() {
	// TODO Auto-generated method stub
	return null;
    }

}
