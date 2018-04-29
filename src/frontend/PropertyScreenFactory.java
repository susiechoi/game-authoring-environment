package frontend;

import java.util.ArrayList;
import java.util.List;

import authoring.factory.AttributeFinder;
import authoring.factory.PropertyFactory;
import authoring.frontend.AuthoringView;
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import jdk.internal.jimage.ImageReader.Node;

public class PropertyScreenFactory {
    PropertiesReader myPropertiesReader;
    UIFactory myUIFactory;
    AttributeFinder myAttributeFinder;
    PropertyFactory myPropertyFactory;
    
    AuthoringView myView;
    public PropertyScreenFactory(AuthoringView view, PropertyFactory propFactory) {
	//myPropertiesReader = new PropertiesReader();
	myUIFactory = new UIFactory();
	myAttributeFinder = new AttributeFinder();
	myPropertyFactory = propFactory;
	myView = view;
    }
    public Parent makePropertyScreen(String propertyName, String UIComponentFilepath, String UIConstantFilepath, String objectName) throws MissingPropertiesException{
	VBox vb = new VBox();
	vb.getChildren().add(myUIFactory.makeScreenTitleText(propertyName));
	List<String> keys = myView.getPropertiesReader().allKeys(UIComponentFilepath);
	ArrayList<String> keyList = new ArrayList<>();
	keyList.addAll(keys);
	List<Object> currentAttributes = myPropertyFactory.retrieveProperty(objectName, propertyName);
	for (int k = 0; k<keyList.size(); k+=1) {
	    String key = keyList.get(k);
	    String UIComponent = myPropertiesReader.findVal(UIComponentFilepath, key);
	    if(UIComponent.equals("Slider")) {
		Slider nextSlider = myUIFactory.setupSlider("", Integer.parseInt(myPropertiesReader.findVal(UIConstantFilepath, key)));
		myUIFactory.setSliderToValue(nextSlider, (String) currentAttributes.get(k));
		vb.getChildren().add(nextSlider);
	    }
	}
	return vb;
    }
    
    private void applyPropertyScreen(Node parent) {
	for(Node node : parent.getChildren()) {
	    // add values to a list
	    //send that list along with property info to the propertyfactory  
	}
    }
    
}
