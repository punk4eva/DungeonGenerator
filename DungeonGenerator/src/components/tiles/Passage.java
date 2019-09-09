
package components.tiles;

import components.traps.Trap;
import graph.Point;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Passage extends Tile{
    
    public final boolean pathfind;

    public Passage(String na, String desc, Point.Type t, Tile al, Trap tr, boolean p){
        super(na, desc, t, al, tr);
        pathfind = p;
    }

}
