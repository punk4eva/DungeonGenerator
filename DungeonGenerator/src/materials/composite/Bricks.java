
package materials.composite;

import textureGeneration.ImageBuilder;
import materials.Material;
import static utils.Utils.R;

/**
 * Represents hardened clay bricks.
 * @author Adam Whittaker
 */
public class Bricks extends Material{

    
    /**
     * Creates an instance.
     */
    public Bricks(){
        super("@Unfinished", ImageBuilder.getColor("brick"), 62, 35, 300, -100, 100, false, false, false, true);
        setDefaultTexture("stoneBricks", R.nextInt(6));
    }

}
