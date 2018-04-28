package frontend;

import java.util.List;

import authoring.factory.AttributeFinder;
import authoring.frontend.AuthoringView;
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class PropertyScreenFactory {
    PropertiesReader myPropertiesReader;
    UIFactory myUIFactory;
    AttributeFinder myAttributeFinder;
    AuthoringView myView;
    public PropertyScreenFactory(AuthoringView view) {
	//myPropertiesReader = new PropertiesReader();
	myUIFactory = new UIFactory();
	myAttributeFinder = new AttributeFinder();
	myView = view;
    }
    public Parent makePropertyScreen(String filepath, String objectName) throws MissingPropertiesException{
	VBox vb = new VBox();
	List<String> keys = myView.getPropertiesReader().allKeys(filepath);
	String className = filepath.substring(filepath.lastIndexOf(".") + 1);
	for(String specificKey : keys) {
	    if(specificKey.contains("Slider")) {
		Slider nextSlider = myUIFactory.setupSlider("", Integer.parseInt(myPropertiesReader.findVal(filepath, key)));
		myAttributeFinder.setFieldValue(key.substring(0, key.lastIndexOf("S")-1), objectName, newValue);
		nextSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
		    	myView.getPro
		});

	    }
	    elseif(key.contains(s))
	}
    }
    
}
