
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.WoodPlanks;

/**
 *
 * @author Adam Whittaker
 */
public class Ebony extends WoodPlanks{

    public Ebony(int bN){
        super(bN, "@Unfinished", ImageBuilder.getColor("ebony"), 40, 20, 30, -10, 50);
    }

}
