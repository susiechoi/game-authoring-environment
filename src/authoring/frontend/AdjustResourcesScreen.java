package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdjustResourcesScreen extends AdjustScreen {

	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
	}

	@Override
	protected Scene makeScreenWithoutStyling() throws MissingPropertiesException {
		VBox vb = new VBox(); 

		Button newObjectButton = makeCreateNewObjectButton(myObjectDescription);
		
		HBox objectsDropdown = getUIFactory().setupPromptAndDropdown("objectOptions", "Edit Existing "+myObjectDescription, myObjectOptions); 
		HBox backAndApplyButton = setupBackAndApplyButton();
		
		vb.getChildren().add(getUIFactory().makeScreenTitleText(myObjectDescription));
		vb.getChildren().add(newObjectButton);
		vb.getChildren().add(orText);
		vb.getChildren().add(objectsDropdown);
		vb.getChildren().add(backAndApplyButton);

		return new Scene(vb, 1500, 900); // TODO move to properties file
	}

}
