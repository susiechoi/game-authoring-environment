package authoring.frontend;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;

public abstract class PathScreen extends AdjustScreen {

    public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";
    public static final String DEFAULT_PLUS_IMAGE = "file:images/plus.png";
    public static final String DEFAULT_MINUS_IMAGE = "file:images/minus.png";
    public static final String DEFAULT_NOFILE_ERROR_KEY = "NoFile";
    private StackPane pathRoot;
    protected GridPane pathGrid;
    protected CreatePathGrid grid;
    
    protected PathScreen(AuthoringView view) {	
	super(view);
	setStyleSheet(view.getCurrentCSS());

	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	int gridWidth = (int) primaryScreenBounds.getWidth() - CreatePathPanel.PANEL_WIDTH;
	int gridHeight = (int) primaryScreenBounds.getHeight() - PathToolBar.TOOLBAR_HEIGHT;

	grid = new CreatePathGrid(view, gridWidth, gridHeight);
    }

    protected void setPathPanel(PathPanel panel, PathToolBar toolbar) {
	pathRoot.getChildren().clear();
	pathRoot.getChildren().add(panel.getPanel());
	pathRoot.getChildren().add(toolbar.getPanel());

	pathRoot.getChildren().add(grid.getGrid());

	StackPane.setAlignment(grid.getGrid(), Pos.TOP_LEFT);
	StackPane.setAlignment(panel.getPanel(), Pos.CENTER_RIGHT);
	StackPane.setAlignment(toolbar.getPanel(), Pos.BOTTOM_LEFT);
    }

    @Override
    public Parent makeScreenWithoutStyling() {

	pathGrid = grid.makePathGrid();
	pathRoot = new StackPane();
	makePanels();
	initializeGridSettings(grid);
	setSpecificUIComponents();
	return pathRoot; 	
    }

    public abstract void makePanels();

    public abstract void initializeGridSettings(CreatePathGrid grid);

    public abstract void setSpecificUIComponents();

    protected CreatePathGrid getGrid() {
	return grid;
    }
}
