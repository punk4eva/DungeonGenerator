
package components.tiles;

import components.Area;
import components.mementoes.AreaInfo;
import graph.Point.Type;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class Water extends Tile{    

    private final AreaInfo info;
    
    public Water(AreaInfo inf){
        this(inf, "water", "@Unfinished");
    }

    protected Water(AreaInfo inf, String na, String desc){
        super(na, desc, Type.FLOOR, null, null);
        info = inf;
    }
    

    @Override
    public void buildImage(Area area, int x, int y){}
    
    @Override
    public void draw(Graphics2D g, int _x, int _y){
        info.waterPainter.paint(g, _x, _y);
    }

}
