
package components.tiles;

import components.traps.Trap;
import graph.Point.Type;

/**
 * A tile with the potential of being considered as a pathway by pathfinding 
 * algorithms.
 * @author Adam Whittaker
 */
public abstract class PassageTile extends Tile{
    
    
    /**
     * Whether pathfinding algorithms should consider this tile as a pathway.
     */
    public final boolean pathfind;

    
    /**
     * Creates a new instance by forwarding parameters to the Tile constructor.
     * @param na
     * @param desc
     * @param t
     * @param al
     * @param tr
     * @param p Whether pathfinding algorithms should consider this tile as a 
     * pathway.
     */
    public PassageTile(String na, String desc, Type t, Tile al, Trap tr, boolean p){
        super(na, desc, p ? Type.DOOR : t, al, tr);
        pathfind = p;
    }

}
