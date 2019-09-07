
package materials;

import filterGeneration.DichromeFilter;
import filterGeneration.ImageBuilder;
import java.awt.Color;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Wood extends Material{

    public Wood(String desc, Color col, double res, double comp, double mTemp, double minH, double maxH){
        super(desc, col, res, comp, mTemp, minH, maxH);
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile("floorBoards/boards" + (R.nextInt(2)+2) + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 30, 15));
        filter.buildFilter();
    }

}
