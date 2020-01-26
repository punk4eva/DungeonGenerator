
package components.rooms;

import components.Area;
import components.tiles.Door;
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
        this("Plain locked room", w, h);
        assertDimensions(w, h, 5, 5);
    }
    
    /**
     * Creates a new instance.
     * @param name The name of the Room.
     * @param w The width
     * @param h The height
     */
    public PlainLockedRoom(String name, int w, int h){
        super(name, w, h);
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
    protected void addDoors(Area area, int numDoors){
        ensureGenerated(area);
        map[height-1][width/2] = new Door(null, null, true, true);
    }

}
