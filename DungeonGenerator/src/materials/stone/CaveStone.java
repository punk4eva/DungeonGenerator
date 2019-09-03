
package materials.stone;

import filterGeneration.ImageBuilder;
import materials.Material;

/**
 *
 * @author Adam Whittaker
 */
public class CaveStone extends Material{

    public CaveStone(){
        super("@Unfinished", ImageBuilder.getColor("stone"), 70, 0, 400, -100, 50);
    }

}
