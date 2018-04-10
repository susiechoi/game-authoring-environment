package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import frontend.Screen;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

public abstract class PathPanel extends AuthoringScreen{
    public static final String DEFAULT_PLUS_IMAGE = "file:images/plus.png";
    public static final String DEFAULT_MINUS_IMAGE = "file:images/minus.png";
    private List<Button> mySizingButtons;
    public PathPanel(AuthoringView view) {
	super(view);
	mySizingButtons = new ArrayList<>();
    }
    public List<Button> getSizingButtons(){
	return mySizingButtons;
    }
    public HBox makeSizingButtons() {
	HBox hb = new HBox();
	Image plusImg = new Image(DEFAULT_PLUS_IMAGE, 60, 40, true, false);
	mySizingButtons.add(getUIFactory().makeImageButton("", plusImg));

	Image minusImg = new Image(DEFAULT_MINUS_IMAGE, 60, 40, true, false);
	mySizingButtons.add(getUIFactory().makeImageButton("", minusImg));
	hb.getChildren().addAll(mySizingButtons);
	return hb;
    }


}
