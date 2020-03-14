
package components.tiles;

import components.Area;
import graph.Point.Type;
import static graph.Point.Type.FLOOR;
import java.awt.Graphics2D;
import textureGeneration.ImageBuilder;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * Part of a decorative statue ( a single instance is either head or body, 
 * decided at run-time assuming no statues are on top of each other).
 * @author Adam Whittaker
 */
public class Statue extends OverFloorTile implements PostProcessingTile{

    
    private static final long serialVersionUID = 5872093L;
    
    
    /**
     * The number of the head and body image choice for this statue.
     */
    private final int headCode, bodyCode;
    private boolean paired = false;
    
    
    /**
     * Generates a random instance.
     * @param sp Whether this tile is on a special floor.
     */
    public Statue(boolean sp){
        this(sp, R.nextInt(7), R.nextInt(4));
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
        
        image.addInstruction(img -> {
            Graphics2D g = (Graphics2D) img.getGraphics();
            if(isHead(area, x/16, y/16)){
                g.drawImage(ImageBuilder.getImageFromFile("tiles/statues/head/head"+headCode+".png"), 0, 0, null);
            }else{
                g.drawImage(ImageBuilder.getImageFromFile("tiles/statues/body/body"+bodyCode+".png"), 0, 0, null);
            }
        });
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
    
    @Override
    public void postProcessing(Area area, int x, int y){
        if(!paired){
            if(area.map[y-1][x].equals(FLOOR)){
                area.map[y-1][x] = new Statue(area.map[y-1][x] instanceof SpecialFloor);
                ((Statue) area.map[y-1][x]).paired = true;
            }else{
                if(area.map[y][x].equals(FLOOR)) PERFORMANCE_LOG.dualPrint("Statue erroneously generated.");
                area.map[y+1][x] = new Statue(area.map[y+1][x] instanceof SpecialFloor);
                ((Statue) area.map[y+1][x]).paired = true;
            }
            paired = true;
        }
    }

}
