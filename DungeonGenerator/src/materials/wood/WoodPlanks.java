
package materials.wood;

import java.util.Objects;
import textureGeneration.DichromeTexture;
import textureGeneration.ImageBuilder;
import materials.Material;
import static textureGeneration.ImageBuilder.colorToPixelArray;
import static textureGeneration.Texture.rgbPixelEquals;
import static utils.Utils.R;

/**
 * Represents a wooden plank material.
 * @author Adam Whittaker
 */
public class WoodPlanks extends Material{

    
    /**
     * Creates an instance.
     * @param wood The wood that the planks are made from.
     */
    public WoodPlanks(Wood wood){
        super(combineDescriptions("This surface is made of standard wooden planks.", wood.description),
                wood.color, wood.resilience, 20, wood.maxTemp, wood.minHeight, wood.maxHeight, true, true, true, true);
        int boardNum = 2 + R.nextInt(2);
        texture = new DichromeTexture(() -> ImageBuilder.getImageFromFile("tiles/floorBoards/boards" + boardNum + ".png"), color);
        texture.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 30, 15));
        texture.buildFilterImage();
    }
    
    
    /**
     * Combines the description of the planks with that of the wood.
     * @param plankDesc The description of the planks.
     * @param woodDesc The description of the wood.
     * @return
     */
    private static String combineDescriptions(String plankDesc, String woodDesc){
        return plankDesc + "\nThe wood itself is " + woodDesc;
    }
    
    @Override
    public boolean equals(Object mat){
        if(mat instanceof WoodPlanks) return rgbPixelEquals(
                colorToPixelArray(color), 
                colorToPixelArray(((Material)mat).color));
        else return false;
    }

    @Override
    public int hashCode(){
        return 37 * 3 + Objects.hashCode(this.color);
    }

}
