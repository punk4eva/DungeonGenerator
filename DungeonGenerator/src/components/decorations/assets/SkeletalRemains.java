
package components.decorations.assets;

import components.decorations.SimpleDecoration;
import static utils.Utils.R;

/**
 * This class represents the decayed remains of an entity.
 * @author Adam Whittaker
 */
public class SkeletalRemains extends SimpleDecoration{

    
    private static final long serialVersionUID = 467285432656L;
    
    
    /**
     * Creates a new instance.
     * @param above Whether to draw the decoration above background features.
     */
    public SkeletalRemains(boolean above){
        super("bones/bones" + R.nextInt(4) + ".png", 
                "Bones", "@Unfinished", above);
    }
    
    /**
     * Creates a new instance.
     * @param aboveChance The chance to draw the decoration above background 
     * features.
     */
    public SkeletalRemains(double aboveChance){
        this(R.nextDouble()<aboveChance);
    }

}
