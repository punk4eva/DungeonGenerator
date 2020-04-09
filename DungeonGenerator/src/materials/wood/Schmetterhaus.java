
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 * Represents a fantasy wood.
 * @author Adam Whittaker
 */
public class Schmetterhaus extends Wood{

    
    /**
     * Creates an instance.
     */
    public Schmetterhaus(){
        super("@Unfinished", ImageBuilder.getColor("schmetterhaus wood"), 60, -30, 50, -35, 55);
    }
    
}
