package authoring.frontend;


import frontend.Screen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public abstract class AuthoringScreen extends Screen {
    private AuthoringView myView;
    
    public AuthoringScreen(AuthoringView view) {
	super();
	setStyleSheet(view.getCurrentCSS());
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
    protected Button setupBackButtonSuperclass() {
	return getUIFactory().setupBackButton(e -> {getView().goBackFrom(this.getClass().getSuperclass().getSimpleName());});
    }
    protected Button setupBackButtonCustom(EventHandler<ActionEvent> e) {
	return getUIFactory().setupBackButton(e);
    }
}
