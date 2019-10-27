
package components.tiles;

import components.Area;
import graph.Point.Type;

/**
 *
 * @author Adam Whittaker
 */
public class Pedestal extends Tile{

    public Pedestal(){
        super("pedestal", "@Unfinished", Type.FLOOR, null, null);
    }

    
    @Override
    public void buildImage(Area area, int x, int y){
        throw new UnsupportedOperationException("@Unfinished");
    }

}
