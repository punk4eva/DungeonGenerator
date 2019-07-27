
package components.tiles;

import components.Trap;
import components.mementoes.AreaInfo;
import graph.Point.Type;
import java.awt.image.BufferedImage;
import utils.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 */
public class Tile{
    
    public String name, description;
    public Tile realIdentity;
    public Trap trap;
    public Type type;
    public transient BufferedImage image;
    
    public Tile(String na, String desc, Type t, Tile real, Trap tr){
        name = na;
        type = t;
        description = desc;
        realIdentity = real;
        trap = tr;
    }
    
    public final void buildImage(AreaInfo info, int x, int y){
        image = ImageBuilder.constructImage(this, info, x, y);
    }
    
}
