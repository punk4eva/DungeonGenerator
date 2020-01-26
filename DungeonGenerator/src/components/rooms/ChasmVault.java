
package components.rooms;

import components.Area;
import components.tiles.Chasm;
import components.tiles.Desk;
import components.tiles.Door;
import components.tiles.SpecialFloor;
import utils.Utils.Unfinished;

/**
 * A massive pit with a reward at one end.
 * @author Adam Whittaker
 */
public class ChasmVault extends PlainLockedRoom{

    
    public ChasmVault(int w, int h){
        super("chasm vault", w, h);
        assertDimensions(w, h, 5, 5);
    }
    
    
    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    @Override
    protected void addDoors(Area area, int numDoors){
        ensureGenerated(area);
        map[height-1][width/2] = new Door(null, null, true, true);
    }
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        
        map[1][width/2-1] = new SpecialFloor("floor");
        map[1][width/2] = new Desk("pedestal", "A place-holder of some high value item.", area.info, true);
        map[1][width/2+1] = new SpecialFloor("floor");
        
        map[2][width/2-1] = new SpecialFloor("floor");
        map[2][width/2] = new SpecialFloor("floor");
        map[2][width/2+1] = new SpecialFloor("floor");
        
        for(int y=1;y<height-1;y++)
            for(int x=1;x<width-1;x++) if(map[y][x] == null)
                map[y][x] = new Chasm();
        
    }

}
