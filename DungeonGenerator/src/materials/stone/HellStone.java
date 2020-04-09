
package materials.stone;

import materials.Material;
import textureGeneration.DichromeTexture;
import textureGeneration.ImageBuilder;
import textureGeneration.Texture;

/**
 * Represents the type of stone found in hell.
 * @author Adam Whittaker
 */
public class HellStone extends Material{

    
    /**
     * Creates an instance.
     */
    public HellStone(){
        super("@Unfinished", ImageBuilder.getColor("hellstone"), 100, 0, 450, -100, -80, false, false, true, true);
        texture = new DichromeTexture(Texture::defaultSupplier, color);
        texture.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 35, 19));
        texture.buildImage();
    }
    
}
