
package components.tiles;

import components.Area;
import filterGeneration.ImageBuilder;
import graph.Point.Type;

/**
 * A cauldron.
 * @author Adam Whittaker
 */
public class Cauldron extends OverFloorTile{
    
    
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
    public void buildImage(Area area, int x, int y){
        super.buildImage(area, x, y);
        
        image.getGraphics().drawImage(ImageBuilder.getImageFromFile("tiles/cauldrons/cauldron0.png"), 0, 0, null);
    }

}
