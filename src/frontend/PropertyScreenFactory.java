package frontend;

import java.util.ArrayList;
import java.util.List;

import authoring.AttributeFinder;
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
    public Parent makePropertyScreen(String propertyName, String UIComponentFilepath, String UIConstantFilepath, String objectName) throws MissingPropertiesException{
	VBox vb = new VBox();
	vb.getChildren().add(myUIFactory.makeScreenTitleText(propertyName));
	List<String> keys = myView.getPropertiesReader().allKeys(UIComponentFilepath);
	ArrayList<String> keyList = new ArrayList<>();
	keyList.addAll(keys);
	List<Object> currentAttributes = 
	for (int k = 0; k<keyList.size(); k+=1) {
	    String key = keyList.get(k);
	    String UIComponent = myPropertiesReader.findVal(UIComponentFilepath, key);
	    if(UIComponent.equals("Slider")) {
		Slider nextSlider = myUIFactory.setupSlider("", Integer.parseInt(myPropertiesReader.findVal(UIConstantFilepath, key)));
		myUIFactory.setSliderToValue(nextSlider,);
		nextSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
		    	myVie
		});
		vb.getChildren().add(nextSlider);
		

	   }
//	    elseif(key.contains(s));
	}
	return vb;
    }
    
}
