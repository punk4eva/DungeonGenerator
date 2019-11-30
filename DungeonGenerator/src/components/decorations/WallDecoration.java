
package components.decorations;

import components.Area;

/**
 *
 * @author Adam Whittaker
 */
public interface WallDecoration{

    public static Decoration getWallDecoration(Area area){
        return new Torch(area.info);
    }

}
