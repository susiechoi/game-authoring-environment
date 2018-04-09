package authoring.frontend.exceptions;

public class MissingPropertiesError extends Error {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MissingPropertiesError(String missingPropertiesName) {
	super(missingPropertiesName);
}

}
