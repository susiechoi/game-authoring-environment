# VOOGA Design Plan
**Team: if (x == true) { return true; } else { return false; }**

[Rubric](https://www2.cs.duke.edu/courses/compsci308/spring18/assign/04_voogasalad/part2_IG7.php)

## Intro

The goal of our program is to create a game-authoring environment that can be used by someone with no programming knowledge to create and play new games within the tower-defense genre. The main challenge of this task is to make the user experience accessible to those with limited knowledge of computers but still robust enough to allow for games complex enough to be enjoyable. 


The primary design goals of the project are to allow maximum flexibility for the user while limiting constraints the user must follow. This means making as many customizable features to the game as possible but also allowing creation of a simple game with little effort. Ideally, the authoring environment would be flexible enough to support new features easily and the game engine would be robust enough to support those features with few or no changes. The engine and gameplay environment should be closed, while the authoring environment should be open (changes to the authoring environment would ideally cause changes in the gameplay without specifically adding code in both locations).


The genre we chose is Tower Defense, in which the primary objective is to place towers that defend resources from onslaughts of attackers. Unique qualities of this type of game include "smart" enemies that follow a set path, towers that can have wildly different responses to collisions (i.e. a freezing tower vs. an exploding tower), and the extent of customizability (placing towers) present during gameplay as well as in the authoring environment.

The design will be split into three main parts: game engine/gameplay, game authoring environment, and file input/output. The three will be connected through a central controller, and both the gameplay engine and authoring environment will be divided into frontend and backend.


## Overview

### Engine
Before we delve into the code, there are a few definitions that we use when discussing our design that are necessary to understand:

**Game** - the specific set of rules/parameters that have been authored (XML File specifying rules)

**Play** - a specific instance of a game that is currently being played. A new game engine defined for each play. Game engine contains state of current play, which knows objects in the game (i.e. towers, enemies) and operates on them.

The high-level design is displayed in the following UML diagram:

**![](https://lh5.googleusercontent.com/nVMH2HVWd4Jv0gvSegsrKvVEbah5Ka2Spl8vO3sHg2nxp2KNqrYplzWEQOlQxIppvjs6Imbct4xodhAFfuJh7WbzXqhKvngFmqx8gnwiIc2Qysu0oH5sptWN02EF38dFTIRITkQt)**

#### Backend Engine

##### The Interacting Elements Module

In general, the EngineController will be the high level class of the Engine backend. It will read in the XML game data from whichever game the user chooses to load. From there, the XML parser will pass the relevant Tower data to a TowerData class. Here, the different Launcher and Projectile data will also be passed to subsequent LauncherData and ProjectileData classes. When the user places one of these towers, the backend will use TowerData to create a Tower object. The steps are below:

1. XML Game file is read, all of the Tower parameters are read and put into TowerData
    
2. User clicks on an available Tower and places it somewhere -> Front end does all of the moving on the screen until placement
    
3. Once placed, front end calls placeTower(name, xcoord, ycoord)
    
4.  TowerData.createTower(name, xcoord, ycoord) will create the tower using existing parameters already read from xml
    
5.  TowerData returns a Tower object, which is placed into Set of Towers
    
Using Composition, the Tower will hold a Launcher object which was created by LauncherData. When Tower.fire() is called, Launcher.launch() will be called, and a projectile will be created and returned up the chain to the GameState, where it will be added to the projectile set, and there it will be update every step by checking if it intersects with any enemies, and if not it will move along its path.


The Enemy module will be similar to Tower in that it will use composition to define itself. Depending on what type of enemy the game creator made and put into XML data, the Enemy object will be updated accordingly. For instance, it might hold a Launcher object if it can fire projectiles, or hold a Flyer object if it can disregard the map. These types of things will be used to define the different enemies behaviors. However, at the basic level the general data about an enemy will be found the same way it was for the Tower. EnemyData reads the XML and then when GameState creates an enemy, it will tell EnemyData to create it and will pass its x and y coordinates. Enemies will use animation to follow a preset path. The set of enemies will be stored in the GameState module.

##### Timeline Control

The Game Engine (Timeline Control) module will be the second level of the backend of the engine. This class will hold the Timeline and execute the Game Loop. Its instance will be created in the Engine Controller module. The TimelineControl will, on every game loop, tell the GameState to update itself. You can see what the GameState does on every loop below. The Timeline Control will hold the current GameState object, and it will also handle any update to the timeline that the front end calls. For instance, if pause is pressed on the frontend, the Timeline Control will handle that and tell the Game Loop to pause itself. This will also be in charge of passing the GameState to an XML writer if the User wants to save the current Play.

The GameConnector class in the TimelineControl module will handle the interactions between the frontend and the backend. Any actions caught by the frontend that need to be handled in the backend will be sent to the GameConnector, where the GameConnector will tell the GameState to handle whichever action is presented. The GameConnector will also pass any necessary updated information from each GameState on each step to the front end, so that the front end can display the information. The front end will also use the GameConnector class to tell the backend to execute any methods that were handled by specific events in the front end (such as button clicking). This GameConnector is a mediator in the Mediator design pattern, which will help our front and back ends communicate effectively.

##### The Game State Module

The GameState module contains the entire current play’s state, and methods to change the landscape of the current game, including the positions of towers, all spawned enemies, launched projectiles, the current score, and new unlocks. The Game State module’s top level is the GameState class, which is updated for every iteration of the game loop. Directly in this update method, several things will happen, including:

1.  Checking if any enemies have intersected any projectiles.
    
2.  Calling launch projectiles from active towers if appropriate
    
3.  Move any Moveable objects (enemies, projectiles, etc)
    
The GameState class also contains methods to receive information from the frontend, such as to place, sell and upgrade a Tower, based on User action (drag and drop, purchasing an item from the sidebar, etc.). The GameState class interacts with the Unlock listener to handle passing point/level upgrades to the User.

##### Unlock Listener

The Unlock Listener uses an observer design pattern to release new versions of towers to the user as they pass a certain threshold of points or make it to a new level. This Listener is updated each time points are incremented, and iterates through a list of authored towers (from the TowerData class) to check if it passes the points/level increment to warrant availability to the User. This updated list of towers is then passed to the frontend for display to the User, where they can drag/drop the newly available tower. The Listener also updates the Listeners when a User completes the level.

##### Tower

The Tower Object is the top-level object placed by the User. Using composition, each Tower contains an instance of an Upgrade object, and a Launcher, which further by composition, contains multiple projectiles which are launched according to the launcher’s launch rate. A Tower object also contains an Image, used in its implementation of the Intersect interface to check if it overlaps with the image of a projectile. Each time the Tower is updated in the Game loop, the Launcher is prompted to ‘launch’ if appropriate, which then sends a projectile along a specified track. A Tower also handles maintaining its current health. A Listener is attached to the Tower, using the Observer design pattern, to check if the Tower’s health dips below 0. If it does, the Game State class is told to remove the Tower from the active Towers collection, and the frontend version of the Tower is likewise removed. Besides containing methods to handle its launchers, a Tower also communicates to its Upgrade object, which handles returning its new Health, Rate of Fire, and Damage for its projectiles. Each of the features of the Tower object is described in more detail below.

##### Upgrade

The Upgrade object is specific to each tower and details how a tower upgrades per level, and also individual upgrades such as health, attack rate, and its launcher’s damage. The Upgrade object’s update methods are called by the Tower object. The frontend, when a user presses a button, calls the Tower to upgrade itself when the User presses an ‘Upgrade’ button. The Upgrade object then updates the Tower object accordingly (by editing instance variables such as its health, etc.), and the resulting tower is then displayed on the front end by sending back a front end tower object.

##### Launcher

There is one launcher per tower, and a launcher contains a timer, a projectile type and a rate. The rate can be adjusted based on upgrades specified in the XML file. For each rate-long interval, an instance of a projectile is created and the projectile is launched across the screen according to a path create in its object. The Launcher’s rate can be updated when the tower calls the corresponding Upgrade object’s method.

##### Projectile

Many instances of the Projectile object are created per launcher, according to the  Launcher’s rate. A projectile is an object that contains an Imageview (to keep track of where it intersects), its damage value (as specified by XML), as well as its start/ending positions. The projectile is launched according to a launch() method, which uses the physics interface to create a curved path for the Projectile, as well as the Moveable interface to create a corresponding animation path. The Enemy checks if it has intersected a projectile and decrements itself by the Projectile’s damage accordingly.

##### Physics

The Physics interface contains methods to create a curve of motion according to a Projectile’s starting positions, ending positions, and speed. It is implemented in the Projectile class and is used in conjunction with the Movable interface to create an animation path to follow.

##### Enemy

The Enemy object is also created within the XML file and contains an image, health, starting position, and Path. The Enemy object also implements the Movable interface in order to follow its path as specified in the XML file as an animation when it is spawned. An enemy is launched by a followPath call. It contains a method for when it is hit by a projectile, in which its health is decremented. The Enemy object is also responsible for checking whether it has been hit by a projectile, which is why it implements the Intersect interface. An enemy’s health is tracked by a separate instance of the Health Listener interface, which notifies the GameState if an enemy’s health has dipped below 0 (according to an Observer design pattern).

##### Movable/Intersect

The Movable and Intersect interfaces are implemented by all moving objects, and objects that need to track if they overlap with another object, respectively. The Movable class contains two methods: a move method, and a rotate method. Both of these create a translational animation path and a rotational animation, respectively. The intersect method checks if two images have overlapped and returns a boolean if so.

##### Frontend Engine

The Frontend Engine will hold all pieces responsible for displaying the game. We will separate the engine into four main parts, the ScreenManager, the Screen class, the Panel class, and the Pop-Up class. The different screen implementations are described below in detail in the User Interface section. The Panel class will be extended by subclasses named SpecificPanel, ControlsPanel, GamePanel, TowerPanel, and ScorePanel. The SpecificPanel will be extended further into the UpgradesPanel, BuyPanel, and the TowerInfoPanel. The different panel classes will describe how they will be represented in the different screens as the games play. The Pop-up class will be extended into the DifficultyPopup, LevelPopup, HelpPopup, EndgamePopup, and the NextLevelPopup classes. Their uses are described below as well. The final piece of the Frontend Engine is the ScreenManager. This class will be the highest level class in the manager, holding all of the different screens and panes, and handling the switches between them. The data from the backend about the towers and the enemies will be routed through the ScreenManager as well.

### Game Authoring Environment
![](https://i.imgur.com/CxVuxai.png)
![](https://i.imgur.com/7YMjY0f.png)

As depicted in the image above, the Game Authoring Environment will be controlled by the AuthoringController, which will mediate between the AuthoringModel and AuthoringView. The AuthoringView will be responsible for controlling classes related to screens/panels/visual aspects of the game authoring environment, while the AuthoringModel class will be responsibile for delegating to FileIO for reading/writing files and for housing/changing Authoring objects such as AuthoringTower, AuthoringEnemy, or AuthoringPathBlock. These objects will hold authoring-specific information and will be compatible with the Gameplay versions of the same objects.



## User Interface
### Engine
The game will start by loading up the main menu screen where the player can choose to play and existing version of the game, open the game authoring environment to edit and create a game mode, and edit an existing created game mode. The play button is initially grayed out until the user selects a version of the game. Once they select a version, the play button is un-grayed out and becomes clickable. Once the button is clicked the user is sent to a new screen where they are presented with the instructions for the selected game, as well as a New Game button and a Continue button. If the user has previously been playing the selected game mode and has saved the continue button will be un-grayed out and when clicked will send the user directly into the game where they stopped before. Otherwise, the continue button will be grayed out and the user will only be able to select the New Game button. When the user clicks that button, they are presented with a pop-up to select between three difficulties; easy, medium and hard. Once the difficulty is selected, the user can select levels on the chosen version of the game. Levels they have not previously reached will be grayed out, and will be unlocked as they reach those levels. Once the user selects a level they are brought into the game screen.

A picture of what the game screen will look like is attached below. The user can click on a tower type on the panel on the right to select a tower to place, and place it by clicking on the map if they have enough money for it. The user will then click play to start the game. Once a level is complete a pop-up will show up to tell the user their statistics for the level (i.e., how many enemies killed, how much money they gained, and what new towers they have unlocked). During or in-between rounds, if the user clicks on a tower they will see the statistics of that individual tower (its health, kills, and level) and a sell button in order to sell the tower where the play, pause, fast forward, save, restart, and quit buttons were before. Along with replacing the pane with options, on the bottom of the screen a panel with available upgrades will pop up. If the user clicks on one of the possible upgrades, the information on the upgrade as well as a “buy upgrade button” will replace the tower’s sell button in the same location. After the user buys an upgrade the options will reappear and the upgrade panel will go back down to the bottom of the screen.

 If the user selects a game mode that is improperly formatted, they will be prompted with a message that tells them to fix the data file or select a new valid file. If the user attempts to place a tower they do not have enough money for, or tries to upgrade a tower they do not have enough money for, they will be told they do not have enough money to do that. If the user loses they will be asked if they want to restart or quit.
**![](https://lh3.googleusercontent.com/jHosc5FC3rSKXt1uEPz7811rltC2MOYEHmfOk-XmT4qUjYZgy9yXVrLpRSV5kp0dZE9CO-S2kNqfSS2iXGvW3iPfgKApmlxjgz5WSh2Vgr12Gc_CWOhsgLrW-U2lLU8WcVt2s1Ys)**
**![](https://lh4.googleusercontent.com/YnZW1DetH4Oer5NLAxsU7TmwlnlrRm31SQyQG1NBFXO1EkB7Xk24O9WqY9kACHHt5vdMDPQ7cfqKSX9HvY0Xy6MEFUU2mLLDjDFAysQvPAiiibsOCo5lMZTl7lUQCo0GbIcOg7xV)**



### Game Authoring Environment![](https://i.imgur.com/Yn5dKov.jpg)
As depicted in the image above, the User Interface for the authoring environment will be based around a central menu, from which users will be able to select which facet of the game they would like to customize. Once users are satisfied with their choices for a certain element, they can click "Apply" to temporarily apply the changes and potentially demo them. Once the user clicks "Save", the game will be permanently saved and available for play. The default game will be functional, and no changes will be allowed that would "break" the game (by enabling/disabling the Apply button), so no errors should need to be reported to the user.

### File I/O

The user never directly interacts with File I/O. File I/O methods are only called 

This section describes how the user will interact with your program (keep it _simple_ to start). Describe the overall appearance of program's user interface components and how users interact with these components (especially those specific to your program, i.e., means of input other than menus or toolbars). Include one or more pictures of the user interface (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a dummy program that serves as a exemplar). Describe how a game is represented to the designer and what support is provided to make it easy to create a game. Finally, describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.).

## Design Details
### Engine
Described in detail below are the Design details of both the Frontend and Backend Engine, as well as how they interact.

#### Backend Engine

##### The Interacting Elements Module

EngineController is the high level class of the Backend Engine. It will utilize the XML module to load the game/play that was selected by the User. The XML will create TowerData and EnemyData objects based on the parsed parameters, and then use that in the creation of a new GameEngine. After telling the XML to parse, the EngineController calls XML.createEngine(), which returns the GameEngine object that will have the TowerData, and EnemyData objects. This GameEngine class will then create a GameState by using the above Data objects. This GameState module is described below, but it will hold the important information about a specific play. In the case the user decided to load a specific play and not just a specific game, The EngineController will utilize another XML parser that will create a GameState object and return that for usage in the newly created GameEngine, rather than having the GameEngine create its own GameState.

NewGame(XML dataPath) This method is called in EngineController and it will have the XML file reader parse the document given in the path. It will return a GameEngine object, which will primarily hold all of the ObjectData objects that will be used to create specific objects (towers, enemies, etc) when they are created in the GameState.

##### Timeline Control

The GameEngine module, will contain the TimelineControl module. GameEngine will also hold the top level of the Front End, called ScreenManager. However, there is a a GameConnector class which will be the mediator between the ScreenManager and the GameEngine. The GameEngine has the GameLoop, which will control all of the animation. Any play, pause, or fast forward buttons will be handled in the GameLoop, where the animation will be updated based on the action. This will update the speed of calls to GameState.update(), which is the method and class that handles all of the step computation. The TimelineControl module will handle all of the front-end back-end interaction through the GameConnector, and this info is then sent to the GameState module. It is in the GameState module where the computation and methods will be held to update any of the enemy, tower, projectile, or path data.

Start() Stop() SetSpeed() These methods will be called in the TimelineControl class. It will edit the GameLoop, and tell the animation.stop(), or whichever is corresponding to the specific command that the user pressed. TimelineControl will get this call from the GameConnector class, which is the mediator between the frontend and the backend.

JumpLevel() This will tell the GameState to go to the next level. LevelData will be able to create the new level, and subsequently it will update the Lists of enemies and towers that are available for that level. GameState.nextLevel() will call LevelData.nextLevelEnemies() and return all of the available enemies, and also call LevelData.nextLevelTowers() and return all of the available towers for that level. This will be updated in the game state and reflected on the front end by passing the information through the GameConnector().

SavePlay() This method will use the current GameState and call XML.savePlay(GameState). That method will convert all of the necessary state information into an xml data file, and it will be readable from the EngineController in the future if the user decides they want to load another game. This method will also need to update the front end, by telling it to go back to the main menu screen so that the user can either start a new play or a new game.

##### The Game State Module

The GameState module, as mentioned above, holds the current Game State and is updated by the Game Engine via its update() method. The Game State module consists of a collection of all current backend Towers, launched Projectiles, and spawned Enemy objects. The GameState communicates with the Frontend through a controller class, which passes specific Tower data to the Frontend it is clicked to be updated, a new Tower is purchased, or a new Tower is sold. The methods that handle additions, deletions and changes to the current Tower collection are described below:

**update():** The update() method in the Game State class first checks if any enemies have intersected any projectiles, and will use a composite design pattern to check if any projectile intersects an Enemy, and whether an Enemy hits a Tower. This will eventually be done by creating a separate EnemyComposite class which holds a list of all spawned Enemies and iterates through them in a method checkHit(Enemy). A similar procedure is used for Tower’s and Enemies, where a list of Tower’s is passed to the EnemyComposite class, where the EnemyComposite class calls the Tower’s .hitBy() method to check if the Tower and Enemy have intersected. What happens in these instances is described in detail in the Tower class.
 
**placeTower(String type, xPos, yPos):** The mediator class calls this method when the frontend indicates that a Tower has been dragged/dropped by the User (by a specific button handler). This method constructs a new backend Tower object of ‘type’ and places its image at the specified coordinates. It also decrements the current user’s money by the value of the Tower, which is stored as an instance variable within the Tower object.

**sellTower(Tower t):** The mediator class calls this method on the GameState when the Frontend screen controller indicates that a User has sold a tower (by a specific button click). This method prompts the Game State class to increment the user’s earnings based on the Tower’s value, and then removes the Tower from the backend collection.

**unlockTower():** This method is attached to the Unlock Listener, which calls the unlockTower() method every time the user’s points or the game’s levels are incremented. The unlockTower() method iterates through a collection of authored towers, available in the GameState’s reference to an immutable copy of the Tower Data’s collection of all towers specified in the XML.

The GameState module contains the entire current play’s state, and methods to change the landscape of the current game, including the positions of towers, all spawned enemies, launched projectiles, the current score, and new unlocks. The Game State module’s top level is the GameState class, which is updated for every iteration of the game loop. Directly in this update method, several things will happen, including:

The GameState class also contains methods to receive information from the frontend, such as to place, sell and upgrade a Tower, based on User action (drag and drop, purchasing an item from the sidebar, etc.). The GameState class interacts with the Unlock listener to handle passing point/level upgrades to the User.

###### Unlock Listener

The Unlock Listener handles releasing a new type of Tower object to the User once they have reached a certain threshold of points or they have progressed to a new level. For instance, when the Points, held in the Game State are updated, the Unlock Listener’s checkUnlock(int points) method is called. This method iterates through a list of all authored Towers, as passed to its Constructor (by the Game State) from the TowerData object. The Listener then checks if either the level/points threshold is satisfied and passes back to the Game State a list of Tower types (Strings) that are available to use. The Game State constructs Tower objects from this list of Types (via its access to the TowerData object’s createTower(String type) method), and this information is then passed to the Frontend Screen Manager for display.

We think that the Tower/Launcher/Projectile hierarchy is flexible since a Tower can contain one to many instances of a Launcher, and each Launcher can be responsible for releasing a different Type of Projectile. Many instances of the Projectile can be created in one iteration of the Game Loop, and then these can all be turned into a list and passed up to the Game State (and eventually the frontend for display).

###### Tower

The Tower object is central to maintaining a working tower defense game. The Tower object will hold a Launcher. The Tower is created when a user places a Tower. The user passes coordinates and the tower type up to the GameState, where the GameState calls TowerData.createTower(Type, x, y) and returns a tower. In TowerData, createTower matches the type of tower with a map of types and parameters for that Tower. These parameters are passed into the tower constructor and then the tower is created. Also, Tower.createLauncher(type) is then called. This will utilize LauncherData, and using the same method of mapping type to parameters, the Launcher object will be created with the parameters as a constructor. This will return a Launcher object that is held in the Tower. The Tower will have its health, coordinates, image, and value. It will also have an Upgrade object. This is used as composition for the tower, and any Tower.upgrade() method will call myUpgrade.upgrade(this), and the tower will be appropriately changed based on the UpgradeData which created the Upgrade object.

###### Launcher

The Launcher class is created within the Tower class, and regulates the releasing of projectiles, by creating different instances of Projectile objects and calling their launch() method.The Launcher contains the instance variables ProjectileType, which is a string that specifies the type that the Launcher releases, and an integer denoting the RateOfFire.  The Launcher also contains the methods launch(), which returns a List of Projectiles to the Tower, which are eventually passed up to the GameState. The Launcher’s RateOfFire can be updated by its Tower Object, which obtains this information from the Upgrade class.

###### Projectile 

A launcher creates one to many instances of a Projectile. A projectile knows its starting coordinates (X, Y), and it’s Speed. It uses the Physics interface (and creates an instance of the Physics object via composition) to determine the projectile’s path. This path is translated into an animation via the Movable class (an instance of which is also created in the Projectile via Composition). The Projectile’s launch() method returns a single Projectile object. The Launcher then compiles multiple Projectile objects and passes them up to the TowerMethod and eventually to the frontend.

##### Physics

The Physics Interface has a getCurve() method that takes in 3 arguments: speed, and initial (x, y) coordinates. The Physics interface will be implemented by a Physics class that creates a curve with a pre-defined curvature value, specifiying the Projectile’s path. This curve will be returned in the form of a coordinate pair specifying the highest point of the Curve. The Movable interface will use this set of coordinates to

##### Enemy

The enemy object is created in the GameState via an EnemyFactory class. This will keep track of the spawn timing of the Enemies, as well as use EnemyData to go through and find the EnemyType in the map, and then call EnemyData.createEnemy(name, params), where the params are found in the map of types of enemies, which is parsed and held in EnemyData. The Factory will return these new enemies and place them into an Enemy collection, which will be used to check if they intersect with any projectiles (and thus die). We will use composition with our enemies, so that if there is an enemy that can shoot, it will have an instance of Launcher, and thus have some LauncherData. Enemies will also hold an Intersector object, which again uses composition and will check if the enemy intersects with any projectiles. It will also follow a preset path through an animation, so if the pause button is called, GameState will loop through the Enemies and tell them all to pause their animation. It will also have a getHitBy(Projectile) method. This method will be called if a projectile intersects with the enemy. It will take the Projectile’s damage and subtract it from the enemies health. If that is less than 0, the enemy will be killed and getHitBy(Projectile) will return the health (a negative or zero number). This will trigger the GameState to remove that enemy from the collection, and it will update the front end accordingly.

##### Movable/Intersect BEN

The IMover interface is to be implemented by a Mover which has methods for moving or rotating an object.  Any game object which should have the behaviors of moving and rotating should hold an instance of this interface to delegate these behaviors to.  This design is based on the design concept of Composition. It has the advantage of encapsulating the implementation of movement and rotation, so implementations can be substituted without affecting code in the game object.  For example, a basic implementation might snap an object from its current position to a new one, and another implementation animates this transition. By delegating to this interface, none of the calls made by the game object would have to change to accommodate this.  The only change would be in the constructor from:

```java

myMover = new BasicMover();

```
to:

```java

myMover = new AnimatedMover();

```

#### Frontend Engine
The Frontend Engine will hold all pieces responsible for displaying the game. We will separate the engine into four main parts, the ScreenManager, the Screen class, the Panel class, and the Pop-Up class. The different screen implementations are described below in detail in the User Interface section. The Panel class will be extended by subclasses named SpecificPanel, ControlsPanel, GamePanel, TowerPanel, and ScorePanel. These panels will hold all of the useful buttons and commands for each of the different panels they sit in. The Controls panel will hold the play, pause, speedup, restart, and exit buttons that will communicate with the backend as well as the help button that will create a pop-up that will handle volume control, language, and allow the user to click for help. The SpecificPanel will be extended further into the UpgradesPanel, BuyPanel, and the TowerInfoPanel. These panels will let the user change their upgrades, and buy and sell towers during the game. The different panel classes will describe how they will be represented in the different screens as the games play. The Pop-up class will be extended into the DifficultyPopup, LevelPopup, HelpPopup, EndgamePopup, and the NextLevelPopup classes. Each pop-up’s name is self explanatory for its functionality. The difficulty pop-up will show when the user is creating a new game and needs to choose their difficulty. The Level pop-up will follow the difficulty one for the user to choose the level they want to start on. The help pop-up will show when the user clicks the gear icon. The endGame popup and the next level pop-up are very similar in that they show options for when the next level starts and when the game ends as described above. The final piece of the Frontend Engine is the ScreenManager. This class will be the highest level class in the manager, holding all of the different screens and panes, and handling the switches between them. The data from the backend about the towers and the enemies will be routed through the ScreenManager as well. The ScreenManager simply sits at the top of the frontend engine so that all of the backend data will come through it to move into the correct panels and screens.


### Authoring Environment
1. AuthoringModel
The AuthoringModel handles creating each Level interface by filling them with the apprpriate data to be written to the xml file. Each level will have its number of waves, the enemies on each wave, the towers available, the path and everything else like background color, theme songs, etc. Each level will then be passed into FileIO and written into xml files. There will also be an overarching game file which will hold the paths to each of the level files.
2. AuthoringView 
The AuthoringView is the frontend portion of the Authroing Environment. It handles the GUI that enables designers to enter information regarding the game they are builing. This information is extracted and passed to the AuthoringModel by calling methods like addTower from the AuthoringController which passes the parameters into the AuthoringModel where they are added to the level interfaces.



## Example Games
Describe three example games from your genre _in detail_ that differ significantly. Clearly identify how the functional differences in these games is supported by your design and enabled by your authoring environment. Use these examples to help make concrete the abstractions in your design.

### 1. Bloons Tower Defense
    In Bloons Tower Defense, the objective is to keep balloons from floating off the screen. There is one set path on which the balloons will travel across the screen, and the user can use earned currency to purchase and upgrade monkey-operated defense towers that pop the balloons. This game design is supported by our authoring environment because the designer has the ability to create their own path, create their own enemies with different images and health/responses to shots (i.e. having a number of weaker balloons spawn after destroying a large, powerful balloon), the user can define a variety of towers with different images and characteristics (attack type, range, speed, damage, etc) and upgrades. This is an iconic tower defense game, so it is what we initially based our authoring environment design on, so we can support this game type.
2. Plants vs. Zombies
    Plants vs Zombies has a number of important differentiating factors from Bloons Tower Defense. First, it has multiple paths and each zombie (except for specially powered ones) only occupies one path. Second, the plants (or towers) are placed on a specific path and can only attack zombies on that specific one and have their own health. Third, within each level, the zombies come in waves. Our authoring environment will support the design of this game type since it gives the designer options to create multiple paths by inserting multiple start points (spots where enemies spawn from), and in the case of plants v zombies, connecting them all to the same end point since there is just one house being protected. The designer can specify for each level which path has how many zombies and of what type, and in the creation of each zombie, they can select the option to have an attack with a certain damage, and the plants can be assigned a certain path to fire on within their range definition and they can also be assigned a health.
3. Defense!
    We also envision having a game type that will be similar to a basketball game. A designer may choose to create "towers" that are Duke basketball players with images of Grayson Allen, Jack White, etc. These players will have their capabilities defined so that they have variable defensive capabilities. For example, a tough and experienced Grayson may be able to fend off a number of UNC enemies while Jack White may have less defensive prowess so may let some opponents get by him to the basket. Perhaps added features for a user vs user type game could allow one player to set up defenders and also deploy offensive players so that it more closely resembles a basketball game.


## Design Considerations
1. Location of FileIO
    * The FileIO package is intended to handle all of the writing to and reading from files. FileIO will be used by the Authoring Environment when a designer creates or alters a game and wants to save it (writing to file) or when loading in a previously made game or game elements (reading from a file). However, FileIO also needs to be made availble to the Game Engin since files need to be read in order to initiate/play a game and files need to be written to in order to save a game's progress. Currently our plan is to hold an instance of FileIO in the Chief Controller class and pass interfaces (specifying the access into FileIO into the Authoring Controller subclasses).
2. How path will be drawn (pixel by pixel or grid)
    * There was much debate over how the Path should be specified in the xml files. We talked about defining a grid on the designer canvas and having each grid cell hold some object (i.e. path, tower, etc.). However, we realized this may have caused issues since it would restrict where users could place towers since they'd have to be in a cell, and the cell size would affect how large towers could be. For now, we've decided that we'll have a coordinate plane underlying the designer canvas where every pixel is a point. The path will be specified by passing sets of two pixels which should be connected by the path. Curves can be shown by having a bunch of sets of pixels that slowly turn at an angle to show a curve (like turning in slogo).
3. How to temporarily save state of game authoring environment 
    * When designer switches to new tab or menu or demos without pressing save, we need to somehow preserve the environment where they were working so they don't lose their progress. Currently we are planning on having a seperate xml file which is updated with every change the user makes (regardless of whether or not it is saved). The alternative was to store all instances and values in a Temp class but we thought this would hinder flexibility.
>>>>>>> master
