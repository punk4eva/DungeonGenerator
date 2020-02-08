
package components.tiles;

import components.Area;
import components.mementoes.AreaInfo;
import textureGeneration.ImageBuilder;
import java.awt.Graphics2D;

/**
 * A water tile.
 * @author Adam Whittaker
 */
public class Water extends Tile{    

    
    /**
     * info: The information of the Area.
     * underTile: The tile underneath this water.
     */
    private final AreaInfo info;
    private final Tile underTile;
    
    
    /**
     * Creates a default instance.
     * @param inf The information of the Area.
     * @param tile The tile underneath this water.
     */
    public Water(AreaInfo inf, Tile tile){
        this(inf, "water", "@Unfinished", tile);
    }

    /**
     * Creates an instance.
     * @param inf The information of the Area.
     * @param na The name.
     * @param desc The description.
     * @param tile The tile underneath this water.
     */
    protected Water(AreaInfo inf, String na, String desc, Tile tile){
        super(na, desc, tile.type, null, tile.decoration);
        info = inf;
        underTile = tile;
    }
    

    @Override
    public void buildImage(Area area, int x, int y){
        if(underTile.image==null) underTile.buildImage(area, x, y);
        image = ImageBuilder.applyWaterShader(
                underTile.image, genShaderCode(area, x/16, y/16));
    }
    
    /**
     * Gets the int code corresponding to the location of the adjacent water 
     * tiles.
     * @param area The area.
     * @param x The tile x.
     * @param y The tile y.
     * @return
     */
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
        if(underTile.decoration!=null && underTile.decoration.aboveWater) underTile.decoration.drawImage(g, _x, _y);
    }

}
