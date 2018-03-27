package file;

import javafx.scene.image.Image;

/**
 * 
 * @author Ben Hodgson 3/27/18
 *
 * Interface used for accessing files within the program file system. 
 */

public interface FileIO {
    
    /**
     * Returns an Image within the program file system given a file path
     * 
     * @param path: the file path used to find the Image
     * @return Image: an Image specified by the path argument
     */
    public Image getImageFile(String path);
    
    /**
     * 
     * 
     * @param directory: String name for a directory
     * @return File[]: an array of File objects contained in the directory
     */
    public File[] getFiles(String directory);
    
    
    

}
