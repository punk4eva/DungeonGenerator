
package materials;

import textureGeneration.ImageBuilder;

/**
 * Represents birch wood.
 * @author Adam Whittaker
 */
public class Birch extends Wood{

    
    /**
     * Creates an instance.
     */
    public Birch(){
        super("@Unfinished", ImageBuilder.getColor("birch"), 57, 50, -20, 40);
    }

}
