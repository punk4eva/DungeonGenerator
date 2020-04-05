
package generation.rooms;

import components.Area;
import components.rooms.Room;
import generation.RoomPlacer;

/**
 * A blueprint class for room placement algorithms.
 * @author Adam Whittaker
 */
public abstract class AbstractRoomPlacer implements RoomPlacer{
    
    
    /**
     * The area that the rooms will be placed in.
     */
    protected final Area area;
    
    
    /**
     * Creates a new instance.
     * @param a The Area.
     */
    protected AbstractRoomPlacer(Area a){
        area = a;
    }
    
    
    /**
     * Marks the Area's graph with the position of a given Room.
     * @param _x The x of the top left of the room.
     * @param _y The y of the top left of the room.
     * @param width The width of the Room.
     * @param height The height of the Room.
     * @param n The room number.
     */
    protected void mark(int _x, int _y, int width, int height, int n){
        for(int x=_x;x<_x+width;x++)
            for(int y=_y;y<_y+height;y++)
                area.graph.map[y][x].roomNum = n;
    }
    
    /**
     * Checks whether the given coordinate can fit in the Area given the 
     * positions of existing rooms (i.e: checks for room overlap).
     * @param _x The x of the top left of the room.
     * @param _y The y of the top left of the room.
     * @param width The width of the Room.
     * @param height The height of the Room.
     * @return
     */
    protected boolean spaceFree(int _x, int _y, int width, int height){
        if(_x<0 || _y<0 || _y+height>area.info.height || _x+width>area.info.width)
            return false;
        for(int y=_y;y<_y+height;y++){
            for(int x=_x;x<_x+width;x++){
                if(area.graph.map[y][x].roomNum!=-1) return false;
            }
        }
        return true;
    }
    
    
    /**
     * A default comparator that compares rooms based on their area.
     * @param r
     * @param r1
     * @return -1, 0, 1 if the first room is smaller, the same size as, or 
     * larger than the second room in volume.
     */
    public static int roomSizeComparator(Room r, Room r1){
        return new Integer(r1.getWidth()*r1.getHeight())
                .compareTo(r.getWidth()*r.getHeight());
    }

}
