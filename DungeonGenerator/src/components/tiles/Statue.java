
package components.tiles;

import components.Area;
import textureGeneration.ImageBuilder;
import graph.Point.Type;
import java.awt.Graphics2D;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * Part of a decorative statue ( a single instance is either head or body, 
 * decided at run-time assuming no statues are on top of each other).
 * @author Adam Whittaker
 */
public class Statue extends OverFloorTile{
    
    
    /**
     * The number of the head and body image choice for this statue.
     */
    private final int headCode, bodyCode;
    
    
    /**
     * Generates a random instance.
     * @param sp Whether this tile is on a special floor.
     */
    public Statue(boolean sp){
        this(sp, R.nextInt(5), R.nextInt(2));
    }
    
    /**
     * Generates an instance.
     * @param sp Whether this tile is on a special floor.
     * @param hC The head code.
     * @param bC The body code.
     */
    public Statue(boolean sp, int hC, int bC){
        super("statue", "@Unfinished", Type.FLOOR, null, null, sp);
        headCode = hC;
        bodyCode = bC;
    }

    
    @Override
    public void buildImage(Area area, int x, int y){
        super.buildImage(area, x, y);
        
        Graphics2D g = (Graphics2D) image.getGraphics();
        if(isHead(area, x/16, y/16)){
            g.drawImage(ImageBuilder.getImageFromFile("tiles/statues/head/head"+headCode+".png"), 0, 0, null);
        }else{
            g.drawImage(ImageBuilder.getImageFromFile("tiles/statues/body/body"+bodyCode+".png"), 0, 0, null);
        }
    }
    
    
    /**
     * Checks whether this statue is a head or a body.
     * @param area The area.
     * @param x The tile x.
     * @param y The tile y.
     * @return True if it is a head, false if not.
     */
    @Unfinished("Deal with case when multiple statues are on top of each other.")
    private boolean isHead(Area area, int x, int y){
        return area.map[y+1][x] instanceof Statue;
    }

}
