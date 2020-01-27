
package materials.wood;

import filterGeneration.DichromeFilter;
import filterGeneration.ImageBuilder;
import materials.Material;
import materials.Wood;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class WoodPlanks extends Material{

    
    public WoodPlanks(Wood wood){
        super(combineDescriptions("This surface is made of standard wooden planks.", wood.description),
                wood.color, wood.resilience, 20, wood.maxTemp, wood.minHeight, wood.maxHeight, true, true, true, true);
        int boardNum = 2 + R.nextInt(2);
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile("tiles/floorBoards/boards" + boardNum + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 30, 15));
        filter.buildFilterImage();
    }
    
    
    private static String combineDescriptions(String plankDesc, String woodDesc){
        return plankDesc + "\nThe wood itself is " + woodDesc;
    }

}
