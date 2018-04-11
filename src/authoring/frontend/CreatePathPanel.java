package authoring.frontend;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreatePathPanel extends PathPanel implements Panel{

	public static final int PANEL_PATH_SIZE = 90;
	public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";

	private VBox pathPanel;
	private VBox dragAndDropPanel;
	private VBox buttonPanel;
	private DraggableImage pathImage;
	private DraggableImage startImage;
	private DraggableImage endImage;
	private HBox pathSizeButtons;
	private Button startImageChooser;
	private Button endImageChooser;
	private Button pathImageChooser;
	private Button applyButton;
	private Button backButton;
	private Button backgroundButton;
	private ImageView trashImage;


	public CreatePathPanel(AuthoringView view) {
		super(view);
		makePanel();
	}

	@Override
	public Button getApplyButton() {
		return applyButton;
	}


	public void makePanel() { //separate into smaller methods

		pathPanel = new VBox();
		buttonPanel = new VBox();
		dragAndDropPanel = new VBox();

		pathPanel.setMaxSize(280, 900);
		buttonPanel.setMinSize(280, 200);
		pathPanel.getStylesheets();

		Label panelTitle = new Label("Drag and Drop Paths");

		Image pathImg = new Image("file:images/cobblestone.png");
		pathImage = new DraggableImage(pathImg);
		pathImage.setCopyDraggable();
		pathImage.getPathImage().getStyleClass().add("img-view");

		Image startImg = new Image("file:images/start.png");
		startImage = new DraggableImage(startImg);
		startImage.setCopyDraggable();
		startImage.getPathImage().getStyleClass().add("img-view");

		Image endImg = new Image("file:images/end.png");
		endImage = new DraggableImage(endImg);
		endImage.setCopyDraggable();
		endImage.getPathImage().getStyleClass().add("img-view");
		

		//		HBox backgroundImageSelector = new HBox();
		//		ImageView imageDisplay = new ImageView(); 
		//		try {
		//			List<String> imageDropdownOptions = getPropertiesReader().allKeys(BACKGROUND_IMAGES);
		//			ComboBox<String> imageDropdown = getUIFactory().makeTextDropdown("", imageDropdownOptions);
		//			backgroundImageSelector = getUIFactory().setupImageSelector(getPropertiesReader(),"", BACKGROUND_IMAGES, 100, getErrorCheckedPrompt("Background"), getErrorCheckedPrompt("LoadImage"),
		//					getErrorCheckedPrompt("NewImageName"), imageDropdown, imageDisplay);
		//		}
		//		catch(MissingPropertiesException e) {
		//			getView().loadErrorScreen("NoImageFile");
		//		}
		//		HBox backgroundImagePrompted = getUIFactory().addPromptAndSetupHBox("", backgroundImageSelector, getErrorCheckedPrompt("Background"));


		backgroundButton = getUIFactory().makeTextButton("", "Choose Background Image");

		Button changeImageButton = getUIFactory().makeTextButton("", "Change Images");
		changeImageButton.setOnAction(new EventHandler <ActionEvent>() {
			public void handle(ActionEvent event) {
				VBox imageButtons = new VBox();
				Scene imageButtonScene = new Scene(imageButtons, 600, 600);
				imageButtonScene.getStylesheets().add(CreatePathScreen.DEFAULT_OWN_STYLESHEET);
				imageButtons.getChildren().addAll(backgroundButton, pathImageChooser, endImageChooser, startImageChooser);
				Stage stage = new Stage();
				stage.setScene(imageButtonScene);
				stage.show();
			}
		});

		pathImageChooser = getUIFactory().makeTextButton("", "Choose Path Image");
		setImageOnButtonPressed(pathImageChooser, pathImage);

		startImageChooser = getUIFactory().makeTextButton("", "Choose Start Image");
		setImageOnButtonPressed(startImageChooser, startImage);

		endImageChooser = getUIFactory().makeTextButton("", "Choose End Image");
		setImageOnButtonPressed(endImageChooser, endImage);

		applyButton = getUIFactory().makeTextButton("", "Apply");

		backButton = setupBackButton();

		pathSizeButtons = makeSizingButtons();
		
		trashImage = makeTrashImage();

		dragAndDropPanel.getChildren().addAll(panelTitle, startImage.getPathImage(), pathImage.getPathImage(), endImage.getPathImage(), trashImage);
		buttonPanel.getChildren().addAll(pathSizeButtons, changeImageButton, applyButton, backButton);
		pathPanel.getChildren().addAll(dragAndDropPanel, buttonPanel);
		pathPanel.getStyleClass().add("rootPanel");
	}

	private void setImageOnButtonPressed(Button button, DraggableImage image) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("View Pictures");
				fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
				File file = fileChooser.showOpenDialog(new Stage());
				image.setNewImage(new Image(file.toURI().toString()));
			}
		});
	}

	public Button getBackgroundButton() {
		return backgroundButton;
	}

	public HBox getSizeButtons() {
		return pathSizeButtons;
	}
	public Node getPanel() {
		return pathPanel;
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		//TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setApplyButtonAction(EventHandler<ActionEvent> e) {
		// TODO Auto-generated method stub
		applyButton.setOnAction(event -> e.handle(event));
	}

}

