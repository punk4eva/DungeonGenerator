
package components.decorations;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import textureGeneration.ImageBuilder;
import textureGeneration.ImageBuilder.SerInstruction;
import textureGeneration.SerImage;

/**
 *
 * @author Adam Whittaker
 */
public class SimpleDecoration extends AbstractDecoration{

    
    private static final long serialVersionUID = 78930L;
    
    /**
     * name: The name of the decoration.
     * description: The description of the decoration.
     * aboveWater: Whether to render the decoration above water.
     * animation: A supplier for an animation based on the coordinates where the
     * decoration is.
     */
    private final SerInstruction instruction;
    
    
    /**
     * Creates a new instance.
     * @param imagePath
     * @param na The name.
     * @param desc The description.
     * @param above whether the decoration is above water.
     */
    public SimpleDecoration(String imagePath, String na, String desc, 
            boolean above){
        super(na, desc, above);
        instruction = (BufferedImage t) -> {
            BufferedImage img = ImageBuilder
                    .getImageFromFile("tiles/decorations/" + imagePath);
            Graphics2D g = (Graphics2D) t.getGraphics();
            g.drawImage(img, 0, 0, null);
        };
    }

    
    @Override
    public void addDecoration(SerImage image){
        image.addInstruction(instruction);
    }
    
    @Override
    public void removeDecoration(SerImage image){
        image.removeInstruction(instruction);
    }

}
