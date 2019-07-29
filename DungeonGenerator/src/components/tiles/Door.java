
package components.tiles;

import components.Trap;
import graph.Point.Type;

/**
 *
 * @author Adam Whittaker
 */
public class Door extends Tile{

    public Door(Tile al, Trap tr){
        super("Door", "@Unfinished", Type.DOOR, al, tr);
    }

}
