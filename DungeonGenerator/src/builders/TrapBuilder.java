
package builders;

import components.Area;
import components.traps.*;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * This class contains the building algorithms for traps.
 * @author Adam Whittaker
 */
public final class TrapBuilder{    
    
    
    /**
     * A private constructor combined with the final modifier in the class
     * declaration represents the "singleton" design pattern, preventing the 
     * non-instantiable class from being instantiated.
     */
    private TrapBuilder(){}

    
    /**
     * Builds a Trap for a door.
     * @param area The Area.
     * @return A random Trap.
     */
    @Unfinished("Complete")
    public static Trap getDoorTrap(Area area){
        return null;
    }
    
    /**
     * Builds a Trap for a floor.
     * @param area The Area.
     * @return A random Trap.
     */
    @Unfinished("Complete")
    public static FloorTrap getFloorTrap(Area area){
        return new BearTrap(R.nextDouble()<0.5); //debug @Unfinished
    }
    
    /**
     * Builds a Trap for a wall.
     * @param area The Area.
     * @return A random Trap.
     */
    @Unfinished("Complete")
    public static WallTrap getWallTrap(Area area){
        return new DartTrap(R.nextDouble()<0.5); //debug @Unfinished
    }
    
}
