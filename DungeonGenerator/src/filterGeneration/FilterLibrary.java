
package filterGeneration;

import java.awt.Color;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class FilterLibrary{

    @Unfinished("Just for debug.")
    public Filter floor = new DichromeFilter(new Color(193, 183, 145), new Color(122, 119, 110));
    public Filter wall = new DichromeFilter(new Color(50, 50, 50), new Color(180, 180, 180));
    {
        floor.supplier = () -> ImageBuilder.getImageFromFile("stoneBricks/stoneBricks2.png");
        //floor.instructions.add(i -> ImageBuilder.applyAlphaNoise(i, 30, 15));
        wall.supplier = () -> ImageBuilder.getImageFromFile("stoneBricks/stoneBricks4.png");
        //wall.instructions.add(i -> ImageBuilder.applyAlphaNoise(i, 50, 25));
        floor.buildFilter();
        wall.buildFilter();
    }
    
}
