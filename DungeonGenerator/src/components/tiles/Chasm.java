
package components.tiles;

import components.Area;
import graph.Point.Type;
import java.awt.image.BufferedImage;
import utils.Utils.Unfinished;

/**
 * A hole.
 * @author Adam Whittaker
 */
@Unfinished("Link to other dungeon floors.")
public class Chasm extends Tile{

    
    /**
     * Creates an instance.
     */
    public Chasm(){
        super("chasm", "This is a <appearance>-looking void in the ground. It would probably be extremely painful or even lethal to fall into it.", Type.NULL, null, null);
    }
    
    
    @Override
    public void buildImage(Area area, int x, int y){
        image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
    }

}
