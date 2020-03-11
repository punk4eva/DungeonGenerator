
package components.decorations;

import java.io.Serializable;
import textureGeneration.SerImage;

/**
 *
 * @author Adam Whittaker
 * This class represents an aesthetic/functional addition to a tile.
 */
public interface Decoration extends Serializable{
    
    
    /**
     * Adds instructions to draw the Decoration to the given serializable image.
     * @param im The image.
     */
    public abstract void addDecoration(SerImage im);
    
    public abstract void removeDecoration(SerImage im);
    
    public abstract String getName();
    
    public abstract String getDescription();
    
    public abstract boolean isAboveBackground();
    
}
