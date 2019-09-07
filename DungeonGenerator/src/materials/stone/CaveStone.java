
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
        super("@Unfinished", ImageBuilder.getColor("stone"), 70, 0, 400, -100, 50);
        filter = new DichromeFilter(Filter::defaultSupplier, color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 12, 7));
        filter.buildFilter();
    }

}
