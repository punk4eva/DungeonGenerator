
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Mahogany extends Material{

    public Mahogany(){
        super("@Unfinished", ImageBuilder.getColor("mahogany"), 40, 20, 42, -20, 25);
    }
    
}
