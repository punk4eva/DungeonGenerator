
package components.tiles;

import components.Area;
import components.decorations.Decoration;
import graph.Point.Type;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class Floor extends Tile{

    
    public Floor(Decoration deco){
        this("floor", "@Unfinished", deco);
    }
    
    protected Floor(String name, String desc, Decoration deco){
        super(name, desc, Type.FLOOR, null, deco);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        generateFloorImage(area, x, y);
        if(decoration!=null) decoration.drawImage((Graphics2D) image.getGraphics(), 0, 0);
    }

}
