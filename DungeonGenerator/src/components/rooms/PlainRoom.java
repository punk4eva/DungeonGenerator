
package components.rooms;

import components.Area;
import components.tiles.PassageTile;
import components.tiles.Tile;
import utils.Utils.Unfinished;

/**
 * A barren Room with standard methods for generating such a room.
 * @author Adam Whittaker
 */
public class PlainRoom extends Room{
    
    
    /**
     * Creates a new instance with a default name.
     * @param w The width.
     * @param h The height.
     */
    public PlainRoom(int w, int h){
        super(DoorStyle.ANY, "Plain Room", w, h);
        assertDimensions(w, h, 5, 5);
    }
    
    /**
     * Creates a new instance.
     * @param ds The style of door placement.
     * @param name The name of the Room.
     * @param w The width.
     * @param h The height.
     */
    protected PlainRoom(DoorStyle ds, String name, int w, int h){
        super(ds, name, w, h);
    }
    
    
    /**
     * Builds a wall at the perimeter of the Room.
     * @param area The Area that this room belongs to.
     */
    protected void buildWalls(Area area){
        for(int x=0;x<width;x++){
            map[0][x] = Tile.genWall(area);
            map[height-1][x] = Tile.genWall(area);
        }
        for(int y=1;y<height-1;y++){
            map[y][0] = Tile.genWall(area);
            map[y][width-1] = Tile.genWall(area);
        }
    }
    
    /**
     * Fills the interior of this Room with floor.
     * @param area The Area that this room belongs to.
     */
    protected void layFloor(Area area){
        for(int y=1;y<height-1;y++)
            for(int x=1;x<width-1;x++)
                map[y][x] = Tile.genFloor(area);
    }
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        layFloor(area);
    }

    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("@Unfinished");
    }

    @Override
    public PassageTile getEntrance(Area area){
        return Tile.genDoor(area, true);
    }

}
