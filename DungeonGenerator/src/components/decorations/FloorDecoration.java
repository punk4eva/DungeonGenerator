
package components.decorations;

import components.Area;

/**
 *
 * @author Adam Whittaker
 */
public interface FloorDecoration{

    public static Decoration getFloorDecoration(Area area){
        return new Pot(area.info);
    }

}
