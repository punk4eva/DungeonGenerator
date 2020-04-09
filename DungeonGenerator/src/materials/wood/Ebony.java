
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 * Represents ebony wood
 * @author Adam Whittaker
 */
public class Ebony extends Wood{

    
    /**
     * Creates an instance.
     */
    public Ebony(){
        super("@Unfinished", ImageBuilder.getColor("ebony"), 55, -27, 54, -10, 50);
    }

}
