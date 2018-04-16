# API Changes

## XML

Updated XMLWriter, XMLReader APIs to reflect what has been in use since almost the very start of the project.

### XMLWriter

Moved most  deprecated methods to XMLDocumentBuilder, since they are used in the general process of building a document but have nothing to do with writing using XStream. New write() method is the only public method.

### XMLReader

Removed readInFile() because there is no need to create a Document before reading from it when using XStream. Deprecated getObjectData() for two reasons: 1) it is bad practice to return an Object type and 2) it was turned into a more specific method createModel() that uses XStream to write a GameData object.