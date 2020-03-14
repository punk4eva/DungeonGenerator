
package components.decorations;

import textureGeneration.ImageBuilder.SerInstruction;
import textureGeneration.SerImage;

/**
 * A Decoration that has an image building instruction associated with it.
 * @author Adam Whittaker
 */
public abstract class ComplexDecoration extends Decoration 
        implements SerInstruction{

    
    private static final long serialVersionUID = 452421L;

    
    /**
     * Creates a new instance by forwarding the parameters to the super 
     * constructor.
     * @param na
     * @param desc
     * @param above
     */
    public ComplexDecoration(String na, String desc, boolean above){
        super(na, desc, above);
    }

    
    @Override
    public void addDecoration(SerImage im){
        im.addInstruction(this);
    }

    @Override
    public void removeDecoration(SerImage im){
        im.removeInstruction(this);
    }

}
