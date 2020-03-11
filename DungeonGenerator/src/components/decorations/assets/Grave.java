
package components.decorations.assets;

import components.decorations.SimpleDecoration;
import static utils.Utils.getRandomItem;

/**
 *
 * @author Adam Whittaker
 */
public class Grave extends SimpleDecoration{

    
    public enum GraveType{
        
        WOODEN("Wooden"), SHORT("Short"), CROSS("Cross"), ANGEL("Angel");
        
        private final String name;
        
        private GraveType(String str){
            name = str;
        }
        
    }
    
    
    public Grave(GraveType type){
        super("graves/grave" + type.name + ".png", "Gravestone", "@Unfinished", 
                false);
    }
    
    public Grave(){
        this(getRandomItem(GraveType.values()));
    }

}
