
package components.decorations.assets;

import components.decorations.SimpleDecoration;
import static utils.Utils.getRandomElement;

/**
 * Represents a gravestone.
 * @author Adam Whittaker
 */
public class Grave extends SimpleDecoration{

    
    private static final long serialVersionUID = 1L;

    
    /**
     * The four different types of graves.
     */
    public enum GraveType{
        
        WOODEN("Wooden"), SHORT("Short"), CROSS("Cross"), ANGEL("Angel");
        
        private final String name;
        
        private GraveType(String str){
            name = str;
        }
        
    }
    
    
    /**
     * Creates a new instance with the given type.
     * @param type The type of grave.
     */
    public Grave(GraveType type){
        super("graves/grave" + type.name + ".png", "Gravestone", "@Unfinished", 
                false);
    }
    
    /**
     * Creates a new instance with a random grave type.
     */
    public Grave(){
        this(getRandomElement(GraveType.values()));
    }

}
