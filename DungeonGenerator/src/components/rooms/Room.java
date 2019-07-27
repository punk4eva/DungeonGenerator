
package components.rooms;

import components.tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Room{

    public int x, y;
    public final String name;
    public String description;
    public final int width, height;
    public int orientation;
    protected boolean generated = false;
    protected final Tile[][] map;
    
    public Room(String n, int w, int h){
        name = n;
        width = w;
        height = h;
        map = new Tile[height][width];
    }
    
    public abstract void generate();
    
    public Tile[][] getMap(){
        if(!generated){
            generate();
            generated = true;
        }
        return map;
    }
    
}
