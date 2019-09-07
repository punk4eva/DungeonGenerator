
package materials.composite;

import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Brick extends Material{

    public Brick(){
        super("@Unfinished", ImageBuilder.getColor("firebrick"), 62, 35, 300, -100, 100);
        throw new UnsupportedOperationException("No filter");
    }

}
