
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.WoodPlanks;

/**
 *
 * @author Adam Whittaker
 */
public class Birch extends WoodPlanks{

    public Birch(int bN){
        super(bN, "@Unfinished", ImageBuilder.getColor("birch"), 40, 20, 42, -20, 30);
    }

}
