package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

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
		hb.getChildren().addAll(mySizingButtons);
		return hb;
	}
	
	protected ImageView makeTrashImage() {
		ImageView trashImage = new ImageView(new Image("file:images/trash.png", 120, 120, true, false));
		trashImage.getStyleClass().add("img-view");
		trashImage.setOnDragOver(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {
				if (event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.ANY);
				}
			}
		});

		trashImage.setOnDragDropped(new EventHandler <DragEvent>() {
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.ANY);
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasImage()) {
					success = true;
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
		return trashImage;
	}

	protected abstract void makePanel();
	protected abstract Button getApplyButton();
	protected abstract Node getPanel();
	protected abstract void setApplyButtonAction(EventHandler<ActionEvent> e);


}
