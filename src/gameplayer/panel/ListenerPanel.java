package gameplayer.panel;


public abstract class ListenerPanel extends Panel {
    
	/**
	 * Checks if label has been created, if it hasn't set up initial value for construction
	 * @param display	display object to be tested for creation
	 * @param initialValue	initial value label should display
	 * @param setNonInitalLabelTo	what label will be set to on creation
	 * @return	if label has been created
	 */
	protected int setInitalProperty(Object display, int initialValue ) {
	    if(display == null) {
		return initialValue;
	    }
	    else {
		return -1;
	    }
	    
	}

}
