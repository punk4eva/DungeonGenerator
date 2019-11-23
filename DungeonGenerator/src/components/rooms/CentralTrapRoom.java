
package components.rooms;

import builders.TrapBuilder;
import components.Area;
import components.traps.FloorTrap;

/**
 *
 * @author Adam Whittaker
 */
public class CentralTrapRoom extends PlainRoom{
    
    
    public CentralTrapRoom(int w, int h){
        super("central trap room", w, h);
        assertDimensions(w, h, 7, 7);
    }
    
    
    @Override
    public void generate(Area area){
        super.generate(area);
        
        FloorTrap trap = TrapBuilder.getFloorTrap(area);
        trap.revealed = true;
        
        for(int y=height/2-1;y<=height/2+1;y++){
            for(int x=width/2-1;x<=width/2+1;x++){
                if(y!=height/2||x!=width/2) map[y][x].decoration = trap.copy();
                else map[y][x].decoration = null;
            }
        }
    }

    @Override
    protected void plopItems(Area area){}
    
}
