package authoring.frontend;


/**
 * Class to create the Screen where a Wave is specified. Dependent on the Path classes (CreatePathGrid, 
 * etc.) because an interactive view of the Path is displayed to allow designers to choose a specific
 * path for a wave to go down. 
 * @author Sarahbland
 *
 */
public class AdjustWaveScreen extends PathScreen {
	private String myWaveNumber;


	protected AdjustWaveScreen(AuthoringView view, String waveNumber) {
		super(view);
		myWaveNumber = waveNumber;
	}
	/**
	 * Sets up grid to show user a new WavePanel (containing choices specific to a given Path) whenever
	 * a new Path start block is clicked.
	 * @see authoring.frontend.PathScreen#initializeGridSettings(authoring.frontend.CreatePathGrid)
	 */
	@Override
	public void initializeGridSettings(CreatePathGrid grid) {
		setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber), new WaveToolBar(getView()));
		grid.setUpForWaves(e -> {setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber), new WaveToolBar(getView()));});//TODO: action here!!!);
	}
	/**
	 * There are no specific UI components in the UI of the WaveScreen, so this method is empty.
	 * @see authoring.frontend.PathScreen#setSpecificUIComponents()
	 */
	@Override
	public void setSpecificUIComponents() {
	    // TODO Auto-generated method stub - need to fix this
	    
	}
}

