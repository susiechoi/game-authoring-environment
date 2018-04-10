package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import frontend.Screen;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class PathPanel extends AuthoringScreen{
    public static final String DEFAULT_PLUS_IMAGE = "file:images/plus.png";
    public static final String DEFAULT_MINUS_IMAGE = "file:images/minus.png";
    private List<Button> mySizingButtons;
    private Button myPlusButton;
    private Button myMinusButton;
    public PathPanel(AuthoringView view) {
	super(view);
	mySizingButtons = new ArrayList<>();
    }
    protected Button getPlusButton(){
	return myPlusButton;
    }
    protected Button getMinusButton() {
	return myMinusButton;
    }
    protected HBox makeSizingButtons() {
	HBox hb = new HBox();
	Image plusImg = new Image(DEFAULT_PLUS_IMAGE, 60, 40, true, false);
	myPlusButton = getUIFactory().makeImageButton("", plusImg);
	mySizingButtons.add(myPlusButton);

	Image minusImg = new Image(DEFAULT_MINUS_IMAGE, 60, 40, true, false);
	myMinusButton = getUIFactory().makeImageButton("", minusImg);
	mySizingButtons.add(myMinusButton);

	mySizingButtons.add(getUIFactory().makeImageButton("", minusImg));
	hb.getChildren().addAll(mySizingButtons);
	return hb;
    }
    protected abstract void makePanel();
    protected abstract Button getApplyButton();
    protected abstract Node getPanel();


}
