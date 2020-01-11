
package materials.composite;

import filterGeneration.DichromeFilter;
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
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile("stoneBricks/stoneBricks" + R.nextInt(5) + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 10, 4));
        filter.buildFilter();
    }

}
