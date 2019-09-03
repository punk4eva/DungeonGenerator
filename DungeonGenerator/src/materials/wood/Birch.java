
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Birch extends Material{

    public Birch(){
        super("@Unfinished", ImageBuilder.getColor("birch"), 40, 20, 42, -20, 30);
    }

}
