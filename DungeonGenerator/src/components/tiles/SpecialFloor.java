
package components.tiles;

import components.Area;

/**
 * An alternative to normal floor.
 * @author Adam Whittaker
 */
public class SpecialFloor extends Floor{

    
    private static final long serialVersionUID = 57843091L;

    
    /**
     * Creates a new instance.
     * @param name The name.
     */
    public SpecialFloor(String name){
        super(name, "@Unfinished", null);
    }
    
    
    @Override
    public void generateImage(Area area, int x, int y){
        image = area.info.architecture.specFloorMaterial
                .texture.generateImage(x, y, area.info.floorNoise);
    }
    
}
