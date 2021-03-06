
package components.rooms;

import builders.TrapBuilder;
import components.Area;
import components.traps.FloorTrap;
import utils.Utils.Unfinished;

/**
 * A room with a ring of traps with a reward in the centre.
 * @author Adam Whittaker
 */
public class CentralTrapRoom extends PlainRoom{
    
    
    /**
     * Creates an instance.
     * @param w The width.
     * @param h The height.
     */
    public CentralTrapRoom(int w, int h){
        super(DoorStyle.ANY, "central trap room", w, h);
        assertDimensions(w, h, 7, 7);
    }
    
    
    @Override
    public void generate(Area area){
        super.generate(area);
        
        //Creates a random Trap.
        FloorTrap trap = TrapBuilder.getFloorTrap(area);
        trap.revealed = true;
        
        //Copies the trap to random places in the Room.
        for(int y=height/2-1;y<=height/2+1;y++){
            for(int x=width/2-1;x<=width/2+1;x++){
                if(y!=height/2||x!=width/2) map[y][x].decoration = trap.copy();
                else map[y][x].decoration = null;
            }
        }
    }

    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
