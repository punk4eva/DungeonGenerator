
package components.decorations;

import components.Area;

/**
 *
 * @author Adam Whittaker
 */
public interface WallDecoration{

    public static Decoration getWallDecoration(Area area, int x, int y){
        return new Torch(area.info, x, y);
    }

}
