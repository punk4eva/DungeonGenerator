
package materials.wood;

import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Ebony extends Material{

    public Ebony(){
        super("@Unfinished", ImageBuilder.getColor("ebony"), 40, 20, 30, -10, 50);
    }

}
