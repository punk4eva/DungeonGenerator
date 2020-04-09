
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 * Represents real life palm wood.
 * @author Adam Whittaker
 */
public class Palm extends Wood{

    
    /**
     * Creates an instance.
     */
    public Palm(){
        super("@Unfinished", ImageBuilder.getColor("palm"), 55, -10, 65, -5, 20);
    }
    
}
