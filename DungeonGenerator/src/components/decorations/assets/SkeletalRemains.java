
package components.decorations.assets;

import components.decorations.SimpleDecoration;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class SkeletalRemains extends SimpleDecoration{

    
    private static final long serialVersionUID = 467285432656L;
    
    
    public SkeletalRemains(boolean above){
        super("bones/bones" + R.nextInt(4) + ".png", 
                "Bones", "@Unfinished", above);
    }
    
    public SkeletalRemains(double aboveChance){
        this(R.nextDouble()<aboveChance);
    }

}
