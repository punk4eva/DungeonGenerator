
package materials.composite;

import filterGeneration.ImageBuilder;
import materials.Material;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Brick extends Material{

    public Brick(){
        super("@Unfinished", ImageBuilder.getColor("brick"), 62, 35, 300, -100, 100, false, false, false, true);
        setDefaultFilter("stoneBricks", R.nextInt(6));
    }

}
