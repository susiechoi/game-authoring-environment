package authoring.frontend;

public class AdjustNewLauncherProjectileScreen extends AdjustLauncherProjectileScreen {

	protected AdjustNewLauncherProjectileScreen(AuthoringView view) {
		super(view);
	}

	/**
	 * In the case that the user is making a new tower,
	 * the data fields on the AdjustLauncherProjectileScreen need not be prepopulated. 
	 * 
	 * Null object design pattern: takes away the need for the instantiated AdjustLauncherProjectileScreen to 
	 * hold logic such as 'if (editingExistingTower)'
	 */
	@Override
	protected void populateFieldsWithData() {
		// DO NOTHING
	}

}
