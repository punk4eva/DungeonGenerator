
package materials.improvised;

import filterGeneration.DichromeFilter;
import filterGeneration.ImageBuilder;
import materials.Material;
import materials.Wood;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Thatch extends Material{

    public Thatch(Wood w){
        super("@Unfinished", w.color, 25, 10, 42, -10, 30, true, true, true, true);
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile("thatch/thatch" + R.nextInt(2) + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 10, 4));
        filter.buildFilter();
    }

}
