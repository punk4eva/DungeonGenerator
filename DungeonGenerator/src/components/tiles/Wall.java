
package components.tiles;

import components.Area;
import components.decorations.Decoration;
import graph.Point.Type;
import java.awt.Graphics2D;

/**
 * A non-traversable wall.
 * @author Adam Whittaker
 */
public class Wall extends Tile{

    
    /**
     * Creates a new default instance.
     * @param deco The decoration.
     */
    public Wall(Decoration deco){
        this("wall", "@Unfinished", deco);
    }
    
    /**
     * Creates a new instance.
     * @param na The name.
     * @param desc The description.
     * @param deco The decoration.
     */
    public Wall(String na, String desc, Decoration deco){
        super(na, desc, Type.WALL, null, deco);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        generateWallImage(area, x, y);
        /*if(decoration!=null){
            decoration.drawImage((Graphics2D) image.getGraphics(), 0, 0);
        }*/
    }

}
