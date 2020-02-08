
package materials.composite;

import textureGeneration.ImageBuilder;
import materials.Material;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Brick extends Material{

    public Brick(){
        super("@Unfinished", ImageBuilder.getColor("brick"), 62, 35, 300, -100, 100, false, false, false, true);
        setDefaultTexture("stoneBricks", R.nextInt(6));
    }

}
