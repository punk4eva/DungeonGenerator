
package components.decorations;

import components.Area;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * A decoration for the floor.
 */
public interface FloorDecoration{

    
    /**
     * Gets a random floor decoration.
     * @param area
     * @return 
     */
    @Unfinished("Make more dependent on Biome")
    public static Decoration getFloorDecoration(Area area){
        return new Pot(area.info);
    }

}
