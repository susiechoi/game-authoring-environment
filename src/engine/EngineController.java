package engine;

import authoring.AuthoringModel;
/**
 * 
 * @author milestodzo
 *
 */
public class EngineController implements EngineControllerI {
	private AuthoringModel myGameModel;
	private PlayState myPlayState;
	
	public EngineController(AuthoringModel gameModel, PlayState playState) {
		myGameModel = gameModel;
		myPlayState = playState;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpeed(Integer speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void savePlay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jumpLevel(int l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLevel(PlayState newLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartLevel() {
		// TODO Auto-generated method stub
		
	}

}
