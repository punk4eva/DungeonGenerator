
package builders;

import components.Area;
import components.traps.*;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 * This class contains the building algorithms for traps.
 */
public final class TrapBuilder{    
    
    
    private TrapBuilder(){}

    
    @Unfinished("Complete")
    public static Trap getDoorTrap(Area area){
        return null;
    }
    
    @Unfinished("Complete")
    public static FloorTrap getFloorTrap(Area area){
        return new BearTrap(R.nextDouble()<0.5); //debug @Unfinished
    }
    
    @Unfinished("Complete")
    public static WallTrap getWallTrap(Area area){
        return new DartTrap(R.nextDouble()<0.5); //debug @Unfinished
    }
    
}
