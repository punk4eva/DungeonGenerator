
package materials.improvised;

import materials.Material;
import materials.Wood;

/**
 *
 * @author Adam Whittaker
 */
public class Thatch extends Material{

    public Thatch(Wood w){
        super("@Unfinished", w.color, 25, 10, 42, -10, 30, true, true, true, true);
        throw new UnsupportedOperationException("No filter");
    }

}
