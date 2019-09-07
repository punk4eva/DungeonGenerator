
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Wood;

/**
 *
 * @author Adam Whittaker
 */
public class Birch extends Wood{

    public Birch(){
        super("@Unfinished", ImageBuilder.getColor("birch"), 40, 20, 42, -20, 30);
    }

}
