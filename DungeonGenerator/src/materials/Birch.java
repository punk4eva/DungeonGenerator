
package materials;

import materials.Wood;
import filterGeneration.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Birch extends Wood{

    
    public Birch(){
        super("@Unfinished", ImageBuilder.getColor("birch"), 57, 50, -20, 40);
    }

}
