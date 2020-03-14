
package components.rooms;

import components.Area;
import components.tiles.Desk;
import components.tiles.SpecialFloor;
import components.tiles.Statue;

/**
 * A Room for giving sacrifices to deities.
 * @author Adam Whittaker
 */
public class AltarRoom extends PlainRoom{

    
    /**
     * Creates an instance.
     * @param w
     * @param h
     */
    public AltarRoom(int w, int h){
        super(DoorStyle.ANY, "Altar room", w, h);
        assertDimensions(w, h, 11, 11);
        System.out.println("Altar");
    }
    
    
    @Override
    public void generate(Area a){
        super.generate(a);
        
        //Generates the central altar space.
        for(int y=height/2-1;y<=height/2+1;y++){
            for(int x=width/2-1;x<=width/2+1;x++){
                if(x == width/2 && y == height/2)
                    map[y][x] = new Desk("Altar", "A place to give offerings.", 
                            a.info, true);
                else map[y][x] = new SpecialFloor("floor");
            }
        }
        
        //Places four statues on the corners of the pedestal.
        map[height/2-2][width/2-2] = new Statue(true);
        map[height/2+2][width/2-2] = new Statue(true);
        map[height/2-2][width/2+2] = new Statue(true);
        map[height/2+2][width/2+2] = new Statue(true);
        map[height/2-1][width/2-2] = new SpecialFloor("floor");
        map[height/2+1][width/2-2] = new SpecialFloor("floor");
        map[height/2-1][width/2+2] = new SpecialFloor("floor");
        map[height/2+1][width/2+2] = new SpecialFloor("floor");
    }

}
