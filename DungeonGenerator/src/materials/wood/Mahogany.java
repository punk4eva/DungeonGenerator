
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.WoodPlanks;

/**
 *
 * @author Adam Whittaker
 */
public class Mahogany extends WoodPlanks{

    public Mahogany(int bN){
        super(bN, "@Unfinished", ImageBuilder.getColor("mahogany"), 40, 20, 42, -20, 25);
    }
    
}
