
package materials.stone;

import textureGeneration.DichromeTexture;
import textureGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Marble extends Material{

    public Marble(){
        super("@Unfinished", ImageBuilder.getColor("marble"), 62, 58, 400, -80, 75, true, true, true, true);
        texture = new DichromeTexture(/*Filter::defaultSupplier*/() -> ImageBuilder.getImageFromFile("tiles/zigzags/zigzag1.png"), color);
        texture.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 20, 13));
        texture.buildFilterImage();
    }
    
}
