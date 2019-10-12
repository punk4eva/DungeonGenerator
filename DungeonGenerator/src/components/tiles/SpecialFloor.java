
package components.tiles;

import components.Area;

/**
 *
 * @author Adam Whittaker
 */
public class SpecialFloor extends Floor{

    public SpecialFloor(String name){
        super(name, "@Unfinished", null);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        image = area.info.architecture.specFloorMaterial.filter.generateImage(x, y, area.info.floorNoise);
    }
    
}
