
package materials;

import materials.Wood;
import filterGeneration.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Ebony extends Wood{

    
    public Ebony(){
        super("@Unfinished", ImageBuilder.getColor("ebony"), 55, 54, -10, 50);
    }

}
