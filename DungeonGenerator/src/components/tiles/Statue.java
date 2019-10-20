
package components.tiles;

import components.Area;
import filterGeneration.ImageBuilder;
import graph.Point.Type;
import java.awt.Graphics2D;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class Statue extends Tile{
    
    private final boolean specialFloor;
    private final int headCode, bodyCode;
    

    public Statue(boolean sp){
        this(sp, R.nextInt(5), R.nextInt(2));
    }
    
    public Statue(boolean sp, int hC, int bC){
        super("statue", "@Unfinished", Type.FLOOR, null, null);
        specialFloor = sp;
        headCode = hC;
        bodyCode = bC;
    }

    
    @Override
    public void buildImage(Area area, int x, int y){
        if(specialFloor)
            image = area.info.architecture.specFloorMaterial.filter.generateImage(x, y, area.info.floorNoise);
        else generateFloorImage(area, x, y);
        
        Graphics2D g = (Graphics2D) image.getGraphics();
        if(isHead(area, x/16, y/16)){
            g.drawImage(ImageBuilder.getImageFromFile("statues/head/head"+headCode+".png"), 0, 0, null);
        }else{
            g.drawImage(ImageBuilder.getImageFromFile("statues/body/body"+bodyCode+".png"), 0, 0, null);
        }
    }
    
    @Unfinished("Deal with case when multiple statues are on top of each other.")
    private boolean isHead(Area area, int x, int y){
        return area.map[y+1][x] instanceof Statue;
    }

}
