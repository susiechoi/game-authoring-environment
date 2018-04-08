package authoring.frontend.exceptions;

public class NoDuplicateNamesException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDuplicateNamesException(String objectName) {
		super(objectName+" already in use. Choose another name.");
	}

}
