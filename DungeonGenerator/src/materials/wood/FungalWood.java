
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 * Represents fantasy wood-like materials formed by fungi.
 * @author Adam Whittaker
 */
public class FungalWood extends Wood{

    
    /**
     * Creates an instance.
     */
    public FungalWood(){
        super("@Unfinished", ImageBuilder.getColor("fungal wood"), 55, -35, 45, -100, 60);
    }
    
}
