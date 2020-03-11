
package components.decorations;

/**
 *
 * @author Adam Whittaker
 */
public abstract class AbstractDecoration implements Decoration{

    
    private static final long serialVersionUID = 58792306L;
    
    /**
     * name: The name of the decoration.
     * description: The description of the decoration.
     * aboveWater: Whether to render the decoration above water.
     */
    private final String name;
    private final String description;
    private final boolean aboveBackground;
    
    
    public AbstractDecoration(String na, String desc, boolean above){
        name = na;
        description = desc;
        aboveBackground = above;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getDescription(){
        return description;
    }

    @Override
    public boolean isAboveBackground(){
        return aboveBackground;
    }
    
}
