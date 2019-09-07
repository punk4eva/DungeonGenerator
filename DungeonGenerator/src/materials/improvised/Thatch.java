
package materials.improvised;

import biomes.Biome;
import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Thatch extends Material{

    public Thatch(Biome b){
        super("@Unfinished", ImageBuilder.getColor(b.getRandomTreeType()), 25, 10, 42, -10, 30);
        throw new UnsupportedOperationException("No filter");
    }

}
