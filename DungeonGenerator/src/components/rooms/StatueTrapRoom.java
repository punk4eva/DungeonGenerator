
package components.rooms;

import builders.TrapBuilder;
import components.Area;
import components.tiles.Desk;
import components.tiles.Floor;
import components.tiles.SpecialFloor;
import components.tiles.Statue;
import components.traps.FloorTrap;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * A Room full of traps with a statue and reward at one end.
 * @author Adam Whittaker
 */
public class StatueTrapRoom extends PlainLockedRoom{
    
    
    /**
     * Creates a new instance.
     * @param w
     * @param h
     */
    public StatueTrapRoom(int w, int h){
        super(DoorStyle.SOUTH, "Statue trap room", w, h);
        assertDimensions(w, h, 7, 9);
    }
    
    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        
        //Builds the pedestal platform and places two statues.
        map[1][width/2-1] = new SpecialFloor("floor");
        map[1][width/2] = new Desk("pedestal", "A place-holder of some high value item.", area.info, true);
        map[1][width/2+1] = new SpecialFloor("floor");
        map[2][width/2] = new SpecialFloor("floor");
        
        map[2][width/2-2] = new Statue(false);
        map[2][width/2+2] = new Statue(false);
        
        //Creates a random trap.
        FloorTrap trap = TrapBuilder.getFloorTrap(area);
        trap.revealed = true;
        
        //Copies the trap on to the Tiles in the Room.
        for(int y=1;y<height-1;y++)
            for(int x=1;x<width-1;x++) if(map[y][x] == null){
                map[y][x] = new Floor(R.nextDouble()<0.5 ? trap.copy() : null);
        }
    }

}
