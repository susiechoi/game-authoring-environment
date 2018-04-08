package authoring.frontend;


import frontend.Screen;
import javafx.scene.control.Button;
public abstract class AuthoringScreen extends Screen {
    private AuthoringView myView;
    public AuthoringScreen(AuthoringView view) {
	super();
	myView = view;
    }
    
    @Override
    public AuthoringView getView() {
	return myView;
    }
    
    protected String getErrorCheckedPrompt(String prompt) {
	return myView.getErrorCheckedPrompt(prompt);
    }
    protected Button setupBackButton() {
	return getUIFactory().setupBackButton(e -> {getView().goBackFrom(this.getClass().getSimpleName());});
    }
}
