
package components.tiles;

import components.Area;
import components.mementoes.AreaInfo;
import filterGeneration.ImageBuilder;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class Water extends Tile{    

    private final AreaInfo info;
    private final Tile underTile;
    
    public Water(AreaInfo inf, Tile tile){
        this(inf, "water", "@Unfinished", tile);
    }

    protected Water(AreaInfo inf, String na, String desc, Tile tile){
        super(na, desc, tile.type, null, tile.trap);
        info = inf;
        underTile = tile;
    }
    

    @Override
    public void buildImage(Area area, int x, int y){
        if(underTile.image==null) underTile.buildImage(area, x, y);
        image = ImageBuilder.generateWaterShader(
                underTile.image, genShaderCode(area, x/16, y/16));
    }
    
    private int genShaderCode(Area area, int x, int y){
        int c = 0;
        if(!(area.map[y-1][x] instanceof Wall) && !(area.map[y-1][x] instanceof Water)) c += 8;
        if(!(area.map[y][x+1] instanceof Wall) && !(area.map[y][x+1] instanceof Water)) c += 4;
        if(!(area.map[y+1][x] instanceof Wall) && !(area.map[y+1][x] instanceof Water)) c += 2;
        if(!(area.map[y][x-1] instanceof Wall) && !(area.map[y][x-1] instanceof Water)) c += 1;
        return c;
    }
    
    @Override
    public void draw(Graphics2D g, int _x, int _y){
        info.waterPainter.paint(g, _x, _y);
        super.draw(g, _x, _y);
    }

}
