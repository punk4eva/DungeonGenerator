
package components.rooms;

import components.Area;
import components.tiles.Door;
import components.tiles.PassageTile;
import utils.Utils.Unfinished;

/**
 * Barren locked room.
 * @author Adam Whittaker
 */
public class PlainLockedRoom extends PlainRoom{
    
    
    /**
     * Creates a new instance with a default name.
     * @param w The width
     * @param h The height
     */
    public PlainLockedRoom(int w, int h){
        this(DoorStyle.ONE, "Plain locked room", w, h);
        assertDimensions(w, h, 5, 5);
    }
    
    /**
     * Creates a new instance.
     * @param ds The type of door placement.
     * @param name The name of the Room.
     * @param w The width
     * @param h The height
     */
    public PlainLockedRoom(DoorStyle ds, String name, int w, int h){
        super(ds, name, w, h);
    }
    
    
    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    
    @Override
    public void addDoorsRandomly(Area area){
        addDoors(area, 1);
    }
    
    @Override
    public void addDoorsSparcely(Area area){
        addDoors(area, 1);
    }
    
    @Override
    public PassageTile getEntrance(Area area){
        return new Door(null, null, true, true);
    }

}
