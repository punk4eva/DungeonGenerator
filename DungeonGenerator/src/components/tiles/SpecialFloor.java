
package components.tiles;

import components.Area;

/**
 * An alternative to normal floor.
 * @author Adam Whittaker
 */
public class SpecialFloor extends Floor{

    
    /**
     * Creates a new instance.
     * @param name The name.
     */
    public SpecialFloor(String name){
        super(name, "@Unfinished", null);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        image = area.info.architecture.specFloorMaterial.texture.generateImage(x, y, area.info.floorNoise);
    }
    
}
