# API Changes

## XML

Updated XMLWriter, XMLReader APIs to reflect what has been in use since almost the very start of the project.

### XMLWriter

Moved most  deprecated methods to XMLDocumentBuilder, since they are used in the general process of building a document but have nothing to do with writing using XStream. New write() method is the only public method.

### XMLReader

Removed readInFile() because there is no need to create a Document before reading from it when using XStream. Deprecated getObjectData() for two reasons: 1) it is bad practice to return an Object type and 2) it was turned into a more specific method createModel() that uses XStream to write a GameData object.

## Backend Objects

###Wave - Sarah will finish!!

At the beginning of the sprint, the Wave backend object was refactored to contain a map of paths to the enemies going down that path for that particular wave (rather than just holding a path and being contained within a wave, as before). This restructure improved readability/usability of the code because initializing a path does not require waves/initializing a wave does not require a path. Additionally, the structure better mirrors the user's experience, as one wave particular is defined for all of the paths on the screen. The following changes resulted:
addEnemy(Enemy enemy, int number) TODO
getUnmodifiableEnemies() TODO
getEnemy() TODO
Wave(path) TODO
