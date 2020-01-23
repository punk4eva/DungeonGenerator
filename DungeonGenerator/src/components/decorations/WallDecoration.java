
package components.decorations;

import components.Area;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * A decoration for the wall.
 */
public interface WallDecoration{

    
    /**
     * Returns a random Wall decoration for the given Area.
     * @param area
     * @return
     */
    @Unfinished("Make more dependent on biome")
    public static Decoration getWallDecoration(Area area){
        return new Torch(area.info);
    }

}
