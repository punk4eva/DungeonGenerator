
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Oak extends Material{

    public Oak(){
        super("@Unfinished", ImageBuilder.getColor("oak"), 40, 20, 42, -30, 50);
    }

}
