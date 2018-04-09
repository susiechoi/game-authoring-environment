package authoring.frontend;

public class SpecifyWaveScreen extends SpecifyObjectScreen{
    public static final String DEFAULT_DESCRIPTION = "Wave";

    protected SpecifyWaveScreen(AuthoringView view) {
	super(view);
	setDescription(DEFAULT_DESCRIPTION); 
    }
}
