
package components.tiles;

import components.Area;
import graph.Point.Type;
import static textureGeneration.ImageBuilder.getImageFromFile;

/**
 * A cauldron.
 * @author Adam Whittaker
 */
public class Cauldron extends OverFloorTile{

    
    private static final long serialVersionUID = 94519327L;
    
    
    /**
     * Creates a new instance.
     * @param name The name of the cauldron.
     * @param desc The description.
     * @param specFloor Whether it is located on a special floor.
     */
    public Cauldron(String name, String desc, boolean specFloor){
        super(name, desc, Type.FLOOR, null, null, specFloor);
    }

    
    @Override
    public void generateImage(Area area, int x, int y){
        super.generateImage(area, x, y);
        
        image.addInstruction(img -> 
                img.getGraphics().drawImage(getImageFromFile(
                        "tiles/cauldrons/cauldron0.png"), 0, 0, null));
    }

}
