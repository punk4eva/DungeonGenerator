
package components.tiles;

import components.Trap;
import components.mementoes.AreaInfo;
import graph.Point.Type;
import java.awt.image.BufferedImage;
import utils.ImageBuilder;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a metre-squared of dungeon.
 */
public class Tile{
    
    /**
     * Name: Name of this tile.
     * Description: Description of this tile.
     * RealIdentity: The real alias of this hidden tile.
     * Trap: The trap which this tile contains.
     * Type: The type of tile (for image noise generation).
     * Image: The image of the tile.
     */
    public String name, description;
    public Tile realIdentity;
    public Trap trap;
    public Type type;
    public transient BufferedImage image;
    
    
    /**
     * Creates a new instance.
     * @param na
     * @param desc
     * @param t
     * @param real
     * @param tr
     */
    public Tile(String na, String desc, Type t, Tile real, Trap tr){
        name = na;
        type = t;
        description = desc;
        realIdentity = real;
        trap = tr;
    }
    
    /**
     * Builds the image of this Tile.
     * @param info The information necessary from the Area.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public final void buildImage(AreaInfo info, int x, int y){
        image = ImageBuilder.constructImage(this, info, x, y);
    }
    
}
