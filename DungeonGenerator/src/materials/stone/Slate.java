
package materials.stone;

import java.awt.Color;
import materials.Material;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Slate extends Material{

    public Slate(){
        super("@Unfinished", new Color(50, 50, 50), 50, 25, 400, -30, 60, true, false, false, true);
        setDefaultTexture("slate", R.nextInt(4));
    }

}
