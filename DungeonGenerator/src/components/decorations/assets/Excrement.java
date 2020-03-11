
package components.decorations.assets;

import components.decorations.SimpleDecoration;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Excrement extends SimpleDecoration{

    private static final long serialVersionUID = 1L;

    
    public Excrement(boolean above){
        super("misc/poo.png", "Excrement", "@Unfinished", above);
    }
    
    public Excrement(double aboveChance){
        this(R.nextDouble()<aboveChance);
    }

}
