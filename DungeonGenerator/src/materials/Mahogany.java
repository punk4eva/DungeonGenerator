
package materials;

import materials.Wood;
import textureGeneration.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Mahogany extends Wood{

    public Mahogany(){
        super("@Unfinished", ImageBuilder.getColor("mahogany"), 53, 67, -20, 25);
    }
    
}
