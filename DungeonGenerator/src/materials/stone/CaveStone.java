
package materials.stone;

import textureGeneration.DichromeTexture;
import textureGeneration.Texture;
import textureGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class CaveStone extends Material{

    public CaveStone(){
        super("@Unfinished", ImageBuilder.getColor("stone"), 70, 0, 400, -100, 50, false, false, true, true);
        texture = new DichromeTexture(Texture::defaultSupplier, color);
        texture.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 15, 9));
        texture.buildFilterImage();
    }

}
