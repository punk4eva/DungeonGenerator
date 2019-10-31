
package materials.stone;

import filterGeneration.DichromeFilter;
import filterGeneration.Filter;
import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Marble extends Material{

    public Marble(){
        super("@Unfinished", ImageBuilder.getColor("marble"), 62, 58, 400, -80, 75);
        filter = new DichromeFilter(/*Filter::defaultSupplier*/() -> ImageBuilder.getImageFromFile("zigzags/zigzag1.png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 20, 13));
        filter.buildFilter();
    }
    
}
