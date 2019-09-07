
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Wood;

/**
 *
 * @author Adam Whittaker
 */
public class Mahogany extends Wood{

    public Mahogany(){
        super("@Unfinished", ImageBuilder.getColor("mahogany"), 40, 20, 42, -20, 25);
    }
    
}
