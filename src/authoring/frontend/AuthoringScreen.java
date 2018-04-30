package authoring.frontend;

import frontend.Screen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
public abstract class AuthoringScreen extends Screen {
	public static final String DEFAULT_CANCEL_KEY = "Cancel";
	public static final String DEFAULT_NOTSAVED_KEY = "NotSaved";
	public static final String DEFAULT_AUTHORING_SPECIFIC = "styling/GameAuthoringSpecific.css";
	
    private AuthoringView myView;
    private boolean myIsSaved;
    
    public AuthoringScreen(AuthoringView view) {
	super();
	setStyleSheet(DEFAULT_AUTHORING_SPECIFIC);
	myView = view;
	myIsSaved = false;
	setupCSSListener(); 
    }
    
    private void setupCSSListener() {
    	myView.cssChangedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				setStyleSheet(myView.getCurrentCSS());
			}
		});
	}

	@Override
    public AuthoringView getView() {
	return myView;
    }
    protected boolean getIsSaved() {
	return myIsSaved;
    }
    protected void setSaved() {
	myIsSaved = true;
    }
    protected String getErrorCheckedPrompt(String prompt) {
	return myView.getErrorCheckedPrompt(prompt);
    }
    
    protected Button setupBackButton() {
		return getUIFactory().setupBackButton(e -> {
			if(!myIsSaved) {
				getView().loadErrorAlert(DEFAULT_NOTSAVED_KEY);
				myIsSaved = true;
			}
			else {
				getView().goBackFrom(this.getClass().getSimpleName());
			}
		},myView.getErrorCheckedPrompt(DEFAULT_CANCEL_KEY));
	    
    }
    protected Button setupBackButtonSuperclass() {
	{
		return getUIFactory().setupBackButton(e -> {
		    if(!myIsSaved) {
			getView().loadErrorAlert(DEFAULT_NOTSAVED_KEY);
			myIsSaved = true;
		    }
		    else {
			getView().goBackFrom(this.getClass().getSuperclass().getSimpleName());
		    }
		},myView.getErrorCheckedPrompt(DEFAULT_CANCEL_KEY));
		    
	    }
    }
    protected Button setupBackButtonCustom(EventHandler<ActionEvent> e) {
	return getUIFactory().setupBackButton(event ->{
	    if(!myIsSaved) {
		getView().loadErrorAlert(DEFAULT_NOTSAVED_KEY);
		myIsSaved = true;
	    }
	    else {
		e.handle(event);
		getView().goBackFrom(this.getClass().getSuperclass().getSimpleName());
	    }
	},myView.getErrorCheckedPrompt(DEFAULT_CANCEL_KEY));
    }
}
