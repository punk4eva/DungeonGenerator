
package components.tiles;

import components.Trap;
import graph.Point.Type;

/**
 *
 * @author Adam Whittaker
 */
public class Wall extends Tile{

    public Wall(Trap tr){
        super("wall", "@Unfinished", Type.WALL, null, tr);
    }
    
    protected Wall(String na, String desc, Trap tr){
        super(na, desc, Type.WALL, null, tr);
    }

}
