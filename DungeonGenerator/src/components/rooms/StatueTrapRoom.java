
package components.rooms;

import builders.TrapBuilder;
import components.Area;
import components.tiles.DecoFloor;
import components.tiles.Door;
import components.tiles.Floor;
import components.tiles.Pedestal;
import components.tiles.SpecialFloor;
import components.tiles.Statue;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class StatueTrapRoom extends PlainLockedRoom{

    public StatueTrapRoom(int w, int h){
        super(w, h);
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
        map[1][width/2] = new Pedestal();
        map[1][width/2+1] = new SpecialFloor("floor");
        
        map[2][width/2] = new Statue(true);
        
        for(int y=1;y<height-1;y++)
            for(int x=1;x<width-1;x++) if(map[y][x] == null){
                if(R.nextDouble() < area.info.feeling.floorDecoChance)
                    map[y][x] = new DecoFloor(R.nextDouble()<0.8 ? TrapBuilder.getFloorTrap(area) : null);
                else map[y][x] = new Floor(R.nextDouble()<0.8 ? TrapBuilder.getFloorTrap(area) : null);
        }
    }

}
