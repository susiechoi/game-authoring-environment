# API Review
Andrew Arnold(asa48), Miles Todzo(mt265) and Ryan Pond(rp159)

#### Flexibility:
Miles/ Ryan - Our usage of composition allows for a lot of flexibility since it reduces dependecies between classes. Rather than having a really deep inheritance hierarchy where if one class is altered all the subclasses must be changed to accomodate it. In some cases we still chose to implement inheritance, but we did our best to keep it to a single level or two.

Andrew (Game Player Frontend) - The usage of the Panel and Screen abstract classes along with the specific panel abstract class allows for new panels to be created and inserted into the program without any major modifications. These new panels can have functionaility needed as they have access to the Game Screeen and Mediator. Adding new functionality is as easy as adding a new panel.


#### Encapsulation of Implementation

Miles / Ryan- He passes ObservableLists from his manager classes to other classes that may need them. This serves to protect the Tower and Enemy objects so that their attributes are not altered anywhere but in their respective Manager classes. Also, when passing objects to the frontend, he enapsulates them in interfaces that restrict access to the methods (i.e. FrontEndTower).

Andrew - Each panel and screen is encapsulated from the others. The actual layout and implementation of each panel is fully encapsulated and protected by the abstract classes they extend. As a result, any given panel could have its implementation fully changed without affecting the other panels. 

#### Linking

Miles / Ryan - The backend controls the game loop and progresses the objects through the game. It communicates any changes in the scene with the frontend through the Mediator class which handles events and other communication.

Andrew - The engine front end is linked to the engine backend in two places. The first is a top level connection between the Screen Manager and the Play Controller. The second is through the mediator. The mediator is responsible for passing information between the lower classes of the front and back end. The entire Game Player is linked to the authoring side of the program through the ChiefController. 



#### Exceptions and Errors

Miles / Ryan - When the authoring environment extracts data from its various fields, it must instantiate the appropriate objects, so the backend must ensure that the values are of the appropriate type.


Andrew - The front end might encounter a multitude of exceptions. The first would be a MissingPropertiesExcepetion. This would occur at any time the front end was relying on information to be read in from a properties file. This includes reading in the location of images and the reading of prompts for buttons. Additionally we could encounter a MissingResourceExpection which would occur if we tried to load and display an image that wasn't at the specified filepath. 


#### Why is this good?


Andrew - The design of the front end is good because it has good encapsulation and flexibility. Further more it is easily extendable which will help as add features quickly. 

