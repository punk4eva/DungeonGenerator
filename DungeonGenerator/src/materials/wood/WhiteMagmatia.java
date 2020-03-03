
package materials.wood;

import textureGeneration.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class WhiteMagmatia extends Wood{

    
    /**
     * Creates an instance.
     */
    public WhiteMagmatia(){
        super("@Unfinished", ImageBuilder.getColor("white magmatic wood"), 
                69, -50, 35, 40, 100);
    }

}
