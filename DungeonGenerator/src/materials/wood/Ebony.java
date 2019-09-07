
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Wood;

/**
 *
 * @author Adam Whittaker
 */
public class Ebony extends Wood{

    public Ebony(){
        super("@Unfinished", ImageBuilder.getColor("ebony"), 40, 20, 30, -10, 50);
    }

}
