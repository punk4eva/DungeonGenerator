
package components.rooms;

import components.Area;
import components.tiles.Tile;
import utils.Utils.Unfinished;

/**
 * A barren Room with standard methods for generating such a room.
 * @author Adam Whittaker
 */
public class PlainRoom extends Room{
    
    
    /**
     * Creates a new instance with a default name.
     * @param w
     * @param h
     */
    public PlainRoom(int w, int h){
        super("Plain Room", w, h);
        assertDimensions(w, h, 5, 5);
    }
    
    /**
     * Creates a new instance.
     * @param name The name of the Room.
     * @param w
     * @param h
     */
    protected PlainRoom(String name, int w, int h){
        super(name, w, h);
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

}
