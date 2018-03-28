# Use Cases
## Authoring Environment
1. User changes a tower's range. 
2. **User is satisfied with their game and clicks "Demo."** GamePlay receives some filepath to a temporary view of the authored game state (elaborate)
3. User clicks "Demo" but has an incomplete path in their game. 
4.  User creates a new kind of tower available during gameplay
5.  User loads in new image file to depict an enemy 
6.  User adds a new path to the level
7.  User wants another level but is too lazy to populate custom settings (auto-generate new levels).
8.  User wants to customize how many enemy "waves" there are, and the composition of those waves. 
9. User wants to return to main menu to start over without saving
10. **User wants to specify which kind of attack ability a tower has (i.e. freezing, shooting, etc.)**: Upon creation of the DropdownMenu object (wrapping a ComboBox) that represents the tower attack type within the AuthoringView class, a Listener is placed on the user selection of an option in the DropdownMenu. A BooleanProperty instance variable in the AuthoringView class is listened to by AuthoringController; this BooleanProperty is changed when the user makes a selection, and separate AuthoringView instance variables are populated with information about the field the user changed (tower ability) and the value to which the user changed it. AuthoringController calls methods to get these values from AuthoringView when the BooleanProperty changes. AuthoringController then calls the `applyChange` method in AuthoringModel, which then applies an ability change to the appropriate Tower object.  
11. **User is in "Demo" mode and wants to return back to editing the game**: ChiefController is notified when the Return to Authoring/Editing button is clicked, upon which it switches its Scene from that provided by PlayController to that provided by AuthoringController. AuthoringController uses FileIO to load in the state of the authoring environment that the user previously left in order to demo. 
12. **User wants to specify custom starting value for player health:** Upon creation of the TextField object that represents the starting health, a Listener is placed on the user input value. A BooleanProperty instance variable in the AuthoringView class is listened to by AuthoringController; this BooleanProperty is changed when the user inputs a value, and separate AuthoringView instance variables are populated with information about the field the user changed (starting health) and the value to which the user changed it. AuthoringController calls methods to get these values from AuthoringView when the BooleanProperty changes. AuthoringController then calls the `applyChange` method in AuthoringModel, which then applies a change in health to the Resources object.  
13. **User loads in a new background image**: FileChooser opens from the SettingsScreen, and the selected File is added to the library of background images that the user can choose from. Noting that a user action has occurred, the AuthoringController updates the SettingsScreen. Upon this update, AuthoringController ensures that FileIO is invoked to get the newest list of the files in the background images folder, which will be provided to SettingsScreen. SettingsScreen may then use the List of Files to create Image objects to display background image options to the user. 
14. User changes the tower's damage 
15. User changes the tower's cost 
16. User wants to add a custom theme song
17. User changes enemies' penalty to health (damage)
18. User wants to load in a previously saved tower into new game
19. User wants to delete an element of the path (drags a path block to the trash icon)
20. User wants to make a path but is too lazy to click and drag path elements one by one (can drag the path block)

## Engine/Gameplay