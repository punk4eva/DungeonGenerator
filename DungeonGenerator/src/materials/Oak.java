
package materials;

import textureGeneration.ImageBuilder;

/**
 * Represents oak wood.
 * @author Adam Whittaker
 */
public class Oak extends Wood{

    
    /**
     * Creates an instance.
     */
    public Oak(){
        super("@Unfinished", ImageBuilder.getColor("oak"), 59, 62, -30, 50);
    }

}
