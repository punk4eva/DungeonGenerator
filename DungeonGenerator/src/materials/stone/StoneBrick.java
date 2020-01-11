
package materials.stone;

import filterGeneration.DichromeFilter;
import filterGeneration.ImageBuilder;
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
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile("stoneBricks/stoneBricks" + (R.nextInt(4)+1) + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 10, 4));
        filter.buildFilter();
    }

}
