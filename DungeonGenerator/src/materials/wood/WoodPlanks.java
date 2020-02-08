
package materials.wood;

import textureGeneration.DichromeTexture;
import textureGeneration.ImageBuilder;
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
        texture = new DichromeTexture(() -> ImageBuilder.getImageFromFile("tiles/floorBoards/boards" + boardNum + ".png"), color);
        texture.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 30, 15));
        texture.buildFilterImage();
    }
    
    
    private static String combineDescriptions(String plankDesc, String woodDesc){
        return plankDesc + "\nThe wood itself is " + woodDesc;
    }

}
