
package components.decorations;

import java.io.Serializable;
import textureGeneration.SerImage;

/**
 * This class represents an aesthetic addition to a Tile.
 * @author Adam Whittaker
 */
public abstract class Decoration implements Serializable{

    
    private static final long serialVersionUID = 58792306L;
    
    /**
     * name: The name of the decoration.
     * description: The description of the decoration.
     * aboveWater: Whether to render the decoration above water.
     */
    public final String name;
    public final String description;
    public final boolean aboveBackground;
    
    
    /**
     * Creates a new instance.
     * @param na The name.
     * @param desc The description.
     * @param above Whether the decoration is above background elements such as
     * water or not.
     */
    public Decoration(String na, String desc, boolean above){
        name = na;
        description = desc;
        aboveBackground = above;
    }
    
    
    /**
     * Adds instructions to draw the Decoration to the given serializable image.
     * @param im The image.
     */
    public abstract void addDecoration(SerImage im);
    
    /**
     * Removes instructions to draw the Decoration from the given serializable 
     * image.
     * @param im The image.
     */
    public abstract void removeDecoration(SerImage im);
    
}
