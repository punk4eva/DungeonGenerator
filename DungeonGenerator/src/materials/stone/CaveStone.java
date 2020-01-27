
package materials.stone;

import filterGeneration.DichromeFilter;
import filterGeneration.Filter;
import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class CaveStone extends Material{

    public CaveStone(){
        super("@Unfinished", ImageBuilder.getColor("stone"), 70, 0, 400, -100, 50, false, false, true, true);
        filter = new DichromeFilter(Filter::defaultSupplier, color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 15, 9));
        filter.buildFilterImage();
    }

}
