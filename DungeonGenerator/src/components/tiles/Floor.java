
package components.tiles;

import components.Area;
import components.traps.Trap;
import graph.Point.Type;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class Floor extends Tile{

    public Floor(Trap tr){
        this("floor", "@Unfinished", tr);
    }
    
    protected Floor(String name, String desc, Trap tr){
        super(name, desc, Type.FLOOR, null, tr);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        generateFloorImage(area, x, y);
        if(trap!=null && trap.revealed) trap.drawImage((Graphics2D) image.getGraphics(), 0, 0);
    }

}
