package authoring.frontend;


import frontend.Screen;
public abstract class AuthoringScreen extends Screen {
	private AuthoringView myView;
	public AuthoringScreen(AuthoringView view) {
		super();
		myView = view;
	}
	protected AuthoringView getView() {
		return myView; 
	}
}
