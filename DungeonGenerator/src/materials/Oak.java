
package materials;

import materials.Wood;
import filterGeneration.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Oak extends Wood{

    public Oak(){
        super("@Unfinished", ImageBuilder.getColor("oak"), 59, 62, -30, 50);
    }

}
