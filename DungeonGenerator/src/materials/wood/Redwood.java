
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 * Represents real life redwood.
 * @author Adam Whittaker
 */
public class Redwood extends Wood{

    
    /**
     * Creates an instance.
     */
    public Redwood(){
        super("@Unfinished", ImageBuilder.getColor("redwood"), 61, -27, 63, -20, 52);
    }
    
}
