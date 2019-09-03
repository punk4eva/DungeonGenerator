
package components.tiles;

import components.Area;
import graph.Point.Type;
import java.awt.image.BufferedImage;

/**
 *
 * @author Adam Whittaker
 */
public class Chasm extends Tile{

    public Chasm(){
        super("chasm", "@Unfinished", Type.NULL, null, null);
    }
    
    @Override
    public void buildImage(Area area, int x, int y){
        image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
    }

}
