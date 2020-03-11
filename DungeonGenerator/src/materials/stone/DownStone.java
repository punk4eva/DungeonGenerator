
package materials.stone;

import materials.Material;
import textureGeneration.DichromeTexture;
import textureGeneration.ImageBuilder;
import textureGeneration.Texture;

/**
 *
 * @author Adam Whittaker
 */
public class DownStone extends Material{

    
    /**
     * Creates an instance.
     */
    public DownStone(){
        super("@Unfinished", ImageBuilder.getColor("downstone"), 100, 0, 450, -100, -65, false, false, true, true);
        texture = new DichromeTexture(Texture::defaultSupplier, color);
        texture.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 35, 19));
        texture.buildImage();
    }
    
}
