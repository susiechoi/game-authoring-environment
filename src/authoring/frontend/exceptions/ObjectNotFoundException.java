package authoring.frontend.exceptions;

public class ObjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String object) {
		super(object+" could not be located"); 
	}
	
}
