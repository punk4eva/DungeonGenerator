
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 * Represents mahogany wood.
 * @author Adam Whittaker
 */
public class Mahogany extends Wood{

    
    /**
     * Creates an instance.
     */
    public Mahogany(){
        super("@Unfinished", ImageBuilder.getColor("mahogany"), 53, 0, 67, -20, 25);
    }
    
}
