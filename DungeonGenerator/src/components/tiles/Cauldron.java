
package components.tiles;

import components.Area;
import filterGeneration.ImageBuilder;
import graph.Point.Type;

/**
 *
 * @author Adam Whittaker
 */
public class Cauldron extends Tile{
    
    
    private final boolean specialFloor;
    
    
    public Cauldron(String name, String desc, boolean specFloor){
        super(name, desc, Type.FLOOR, null, null);
        specialFloor = specFloor;
    }

    
    @Override
    public void buildImage(Area area, int x, int y){
        if(specialFloor)
            image = area.info.architecture.specFloorMaterial.filter.generateImage(x, y, area.info.floorNoise);
        else generateFloorImage(area, x, y);
        
        image.getGraphics().drawImage(ImageBuilder.getImageFromFile("cauldrons/cauldron0.png"), 0, 0, null);
    }

}
