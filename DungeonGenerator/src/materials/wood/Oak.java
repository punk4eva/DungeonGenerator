
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Material;
import materials.Wood;

/**
 *
 * @author Adam Whittaker
 */
public class Oak extends Wood{

    public Oak(){
        super("@Unfinished", ImageBuilder.getColor("oak"), 40, 20, 42, -30, 50);
    }

}
