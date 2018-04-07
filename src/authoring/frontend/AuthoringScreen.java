package authoring.frontend;


import frontend.Screen;
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
}
