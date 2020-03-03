
package materials.improvised;

import materials.Material;
import materials.wood.Wood;
import static utils.Utils.R;

/**
 * Represents thatch made out of wood.
 * @author Adam Whittaker
 */
public class Thatch extends Material{

    
    /**
     * Creates an instance.
     * @param w The wood that the thatch is made from.
     */
    public Thatch(Wood w){
        super("@Unfinished", w.color, 25, 10, 42, -10, 30, true, false, true, false);
        setDefaultTexture("thatch", R.nextInt(2));
    }

}
