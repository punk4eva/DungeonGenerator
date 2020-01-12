
package materials.improvised;

import materials.Material;
import materials.Wood;

/**
 *
 * @author Adam Whittaker
 */
public class Thatch extends Material{

    public Thatch(Wood w){
        super("@Unfinished", w.color, 25, 10, 42, -10, 30, true, false, true, false);
        setDefaultFilter("thatch", 2);
    }

}
