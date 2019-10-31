
package components.tiles;

import components.Area;
import components.traps.Trap;
import graph.Point;

/**
 *
 * @author Adam Whittaker
 */
public class Pot extends Tile{

    
    public Pot(String na, String desc, Point.Type t, Tile al, Trap tr){
        super(na, desc, t, al, tr);
    }

    
    @Override
    public void buildImage(Area area, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
