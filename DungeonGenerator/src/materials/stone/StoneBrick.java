
package materials.stone;

import java.awt.Color;
import materials.Material;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class StoneBrick extends Material{

    public StoneBrick(){
        super("@Unfinished", new Color(80, 80, 80), 65, 40, 400, -50, 60, false, false, true, true);
        setDefaultTexture("stoneBricks", R.nextInt(6));
    }

}
