
package materials;

import filterGeneration.DichromeFilter;
import filterGeneration.ImageBuilder;
import java.awt.Color;

/**
 *
 * @author Adam Whittaker
 */
public class WoodPlanks extends Material{

    public WoodPlanks(int boardNum, String desc, Color col, double res, double comp, double mTemp, double minH, double maxH){
        super(desc, col, res, comp, mTemp, minH, maxH);
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile("floorBoards/boards" + boardNum + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 30, 15));
        filter.buildFilter();
    }

}
