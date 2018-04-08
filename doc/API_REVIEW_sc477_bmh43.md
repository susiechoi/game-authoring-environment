# API Review 
sc477 & bmh43

## Part 1 
1. Ben's design is intended to be flexible in that, through inheritance hierarchy and composition, AuthoringModel is able to handle many different kinds of objects despite the lack of explicit hardcoding. Susie's design is intended to be flexible in the ability to put together different elements on Screens through a shared UI element factory. Any classes can use the return values from UIFactory methods to create Screens however they desire.

2. Ben has ensured use of interfaces, superclasses and having specific implementations extend these classes. For example, his Property superclass has an upgrade abstract class that can be flexibly used by different Property subclasses, such as health, damage, firerate. Susie has also done this through the use of Screens (screen hierarchy e.g.: AdjustEnemyScreen extends AdjustScreen which extends Screen which implements a Screen interface that is shared between the authoring and gameplay frontends). 

3. Ben's part of the project must obtain information inputed by the user on the authoring frontend, thereby making communication with the authoring frontend crucial. This is ensured by his AuthoringModel class, which acts as the Model in the authoring environment's MVC. I asked him how data would be sent from the authoring frontend to the authoring model (i.e. through a Properties file or through a Map?). He stated that this communication would occur thorugh constructors: specifically, the frontend would put user input into arguments in an AuthoringModel method, and AuthoringModel would then delegate the creation of objects based on this user input. 

4. In Susie's case, excpetions occur when Prompts files are not available for a given language. They also occur when the image files that are specified in Properties files do not exist in the file system. Also when the user hasn't inputed information that is needed (e.g. the name of the new image file they want to load in). Error screens are displayed to the user in these cases. For Ben, exceptions involve ensuring that interpretable information is obtained from the frontend and that the information is correct format/data types. 

5. Extensive use of interfaces and abstract superclasses that can be flexibly used by a  number of objects such as Screens and attribute objects like DamageProperty.

## Part 2
1. For Susie, the use cases are descriptive and have helped in the creaiton of comprehensive screens for receiving authoring user input. However, many of them are closely connected, therefore making it seem redundant to have so many different issues. For Ben, he finds that there are cases in which issue were not anticipated, such as creating a level object or enemy wave object. In these cases, he has found it fruitful to create new, descriptive, appropriately-sized issues. 

2. Ben finds that the possibility of finding new issue cases makes the issue-resolution and creation process unpredictable. Susie finds that the communication between subteams increases uncertainty: many issues rely upon communication by multiple subteams. 

3. Ben is most excited to work on object creation in the Authoring Model (e.g. handling and defining a Tower object and writing to a file how it interacts with a game). Susie is excited to facilitate the process of passing user authoring input to object constructors.

4. Ben is most worried about ensuring that our design is actually flexible, i.e. can handle characteristics such as new tower and enemy attributes. Susie is most worried about adding consistency to the authoring and gameplay definitions of objects. 

5. Susie needs to implement an input fields scraper so that the information can be passed to the AuthoringModel. Ben plans to implement work in the game engine, adding to the Model in the authoring environment to create a new game/play state. 


