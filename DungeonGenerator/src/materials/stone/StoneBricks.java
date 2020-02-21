
package materials.stone;

import java.awt.Color;
import materials.Material;
import static utils.Utils.R;

/**
 * Represents bricks made from stone.
 * @author Adam Whittaker
 */
public class StoneBricks extends Material{

    
    /**
     * Creates an instance.
     */
    public StoneBricks(){
        super("@Unfinished", new Color(80, 80, 80), 65, 40, 400, -50, 60, false, false, true, true);
        setDefaultTexture("stoneBricks", R.nextInt(6));
    }

}
