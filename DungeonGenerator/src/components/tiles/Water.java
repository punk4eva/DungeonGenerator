
package components.tiles;

import components.Area;
import graph.Point.Type;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class Water extends Tile{
    
    public Water(){
        this("water", "@Unfinished");
    }

    protected Water(String na, String desc){
        super(na, desc, Type.FLOOR, null, null);
    }

    @Override
    public void buildImage(Area area, int x, int y){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void draw(Graphics2D g, int _x, int _y){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
