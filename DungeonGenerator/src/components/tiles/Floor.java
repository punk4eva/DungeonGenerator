
package components.tiles;

import components.Trap;
import graph.Point.Type;

/**
 *
 * @author Adam Whittaker
 */
public class Floor extends Tile{

    public Floor(Trap tr){
        super("floor", "@Unfinished", Type.FLOOR, null, tr);
    }
    
    protected Floor(String name, String desc, Trap tr){
        super(name, desc, Type.FLOOR, null, tr);
    }

}
