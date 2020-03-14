
package components.decorations.assets;

import components.decorations.SimpleDecoration;
import static utils.Utils.R;

/**
 * Represents animal faeces.
 * @author Adam Whittaker
 */
public class Excrement extends SimpleDecoration{

    private static final long serialVersionUID = 5692729345L;

    
    /**
     * Creates a new instance.
     * @param above Whether the poo should be rendered above background 
     * features.
     */
    public Excrement(boolean above){
        super("misc/poo.png", "Excrement", "@Unfinished", above);
    }
    
    /**
     * Creates a new instance.
     * @param aboveChance The 0 -> 1 chance of being rendered above background 
     * features.
     */
    public Excrement(double aboveChance){
        this(R.nextDouble()<aboveChance);
    }

}
