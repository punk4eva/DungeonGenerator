
package components.rooms;

import components.tiles.Tile;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a room in the dungeon.
 */
public abstract class Room{

    /**
     * x, y: The x and y coordinates.
     * Name: The name of this room.
     * Description: The description of this room.
     * width, height: The dimension of the room.
     * Generated: Whether the Room has been generated or not (lazy generation).
     * Map: The tile map.
     */
    public int x, y;
    public final String name;
    public String description;
    public final int width, height;
    public int orientation;
    protected boolean generated = false;
    protected final Tile[][] map;
    
    
    /**
     * Creates a new instance.
     * @param n
     * @param w
     * @param h
     */
    public Room(String n, int w, int h){
        name = n;
        width = w;
        height = h;
        map = new Tile[height][width];
    }
    
    /**
     * Gets the tile map, ensuring it is generated.
     * @return
     */
    public Tile[][] getMap(){
        if(!generated){
            generate();
            generated = true;
        }
        return map;
    }
    
    /**
     * Generates this room.
     */
    protected abstract void generate();
    
}
