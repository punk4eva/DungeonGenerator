
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Spruce extends Wood{
    
    
    /**
     * Creates an instance.
     */
    public Spruce(){
        super("@Unfinished", ImageBuilder.getColor("spruce"), 57, -28, 62, -40, 60);
    }

}
