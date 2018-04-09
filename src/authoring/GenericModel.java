package authoring;

/**
 * 
 * @author Ben Hodgson 4/8/18
 *
 * Class to generate a generic AuthoringModel.java class and save the object
 * as an XML using XStream.
 */
public class GenericModel {
    // TODO put in properties files
    private final String DEFAULT = "default";
    private final AuthoringModel myModel;
    
    public GenericModel() {
	myModel = new AuthoringModel();
	generateGenericModel();
    }
    
    private void generateGenericModel() {
	
    }
    
}
