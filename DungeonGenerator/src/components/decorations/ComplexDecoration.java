
package components.decorations;

import textureGeneration.ImageBuilder.SerInstruction;
import textureGeneration.SerImage;

/**
 *
 * @author Adam Whittaker
 */
public abstract class ComplexDecoration extends AbstractDecoration 
        implements SerInstruction{

    
    private static final long serialVersionUID = 452421L;

    
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
