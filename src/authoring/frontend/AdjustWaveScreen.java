package authoring.frontend;

import javafx.scene.Parent;

/**
 * Class to create the Screen where a Wave is specified. Dependent on the Path classes (CreatePathGrid, 
 * etc.) because an interactive view of the Path is displayed to allow designers to choose a specific
 * path for a wave to go down. 
 * @author Sarahbland
 *
 */
public class AdjustWaveScreen extends PathScreen {
	private String myWaveNumber;
	private WaveDirectionsPanel myWaveDirectionsPanel;
	private WaveToolBar myWaveToolBar;


	protected AdjustWaveScreen(AuthoringView view, String waveNumber) {
		super(view);
		myWaveNumber = waveNumber;

	}
	
	public void makePanels() {
		myWaveDirectionsPanel = new WaveDirectionsPanel(getView(), myWaveNumber);
		myWaveToolBar = new WaveToolBar(getView());
	}
	/**
	 * Sets up grid to show user a new WavePanel (containing choices specific to a given Path) whenever
	 * a new Path start block is clicked.
	 * @see authoring.frontend.PathScreen#initializeGridSettings(authoring.frontend.CreatePathGrid)
	 */
	@Override
	public void initializeGridSettings(CreatePathGrid grid) {
		setPathPanel(myWaveDirectionsPanel, myWaveToolBar);
		grid.setUpForWaves(e -> {setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber), myWaveToolBar);});//TODO: action here!!!);
	}
	/**
	 * There are no specific UI components in the UI of the WaveScreen, so this method is empty.
	 * @see authoring.frontend.PathScreen#setSpecificUIComponents()
	 */
	@Override
	public void setSpecificUIComponents() {
//	    setGridUIComponents(myWaveDirectionsPanel, myWaveToolBar);
	}
	protected Parent populateScreenWithFields() {
	    // TODO Auto-generated method stub
	    return null;
	}
	protected void populateFieldsWithData() {
	    // TODO Auto-generated method stub
	    
	}
}

