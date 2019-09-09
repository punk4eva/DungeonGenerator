
package materials.improvised;

import biomes.Biome;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class Thatch extends Material{

    public Thatch(Biome b){
        super("@Unfinished", b.getRandomTreeType().color, 25, 10, 42, -10, 30);
        throw new UnsupportedOperationException("No filter");
    }

}
