package engine.builders;

import engine.sprites.Sprite;

/**
 * Abstract class for creating a Sprite. Used by authoring model to create template objects.
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public abstract class Builder {
    
    public abstract Sprite construct();

}
