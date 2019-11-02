
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.WoodPlanks;

/**
 *
 * @author Adam Whittaker
 */
public class Oak extends WoodPlanks{

    public Oak(int bN){
        super(bN, "@Unfinished", ImageBuilder.getColor("oak"), 40, 20, 42, -30, 50);
    }

}
