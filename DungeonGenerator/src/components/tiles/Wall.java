
package components.tiles;

import components.Area;
import components.traps.Trap;
import graph.Point.Type;
import java.awt.Graphics2D;

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
    
    @Override
    public void buildImage(Area area, int x, int y){
        generateWallImage(area, x, y);
        if(trap!=null && trap.revealed) trap.drawImage((Graphics2D) image.getGraphics(), 0, 0);
    }

}
