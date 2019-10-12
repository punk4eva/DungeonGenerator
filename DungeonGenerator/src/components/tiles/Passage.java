
package components.tiles;

import components.traps.Trap;
import graph.Point.Type;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Passage extends Tile{
    
    public final boolean pathfind;

    public Passage(String na, String desc, Type t, Tile al, Trap tr, boolean p){
        super(na, desc, p ? Type.DOOR : t, al, tr);
        pathfind = p;
    }

}
