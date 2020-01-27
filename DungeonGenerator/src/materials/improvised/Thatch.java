
package materials.improvised;

import materials.Material;
import materials.Wood;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Thatch extends Material{

    public Thatch(Wood w){
        super("@Unfinished", w.color, 25, 10, 42, -10, 30, true, false, true, false);
        setDefaultFilter("thatch", R.nextInt(2));
    }

}
