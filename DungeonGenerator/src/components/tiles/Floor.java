
package components.tiles;

import components.Area;
import components.decorations.Decoration;
import graph.Point.Type;
import java.awt.Graphics2D;

/**
 * A stereotypical tile that can be walked on.
 * @author Adam Whittaker
 */
public class Floor extends Tile{

    
    /**
     * Creates a default instance.
     * @param deco The decoration on this floor.
     */
    public Floor(Decoration deco){
        this("floor", "@Unfinished", deco);
    }
    
    /**
     * Creates an instance.
     * @param name The name of this floor.
     * @param desc The description of this floor.
     * @param deco The decoration on this floor.
     */
    protected Floor(String name, String desc, Decoration deco){
        super(name, desc, Type.FLOOR, null, deco);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        generateFloorImage(area, x, y);
        if(decoration!=null) decoration.drawImage((Graphics2D) image.getGraphics(), 0, 0);
    }

}
