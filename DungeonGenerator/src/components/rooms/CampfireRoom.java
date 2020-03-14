
package components.rooms;

import components.Area;
import components.tiles.Barricade;
import components.tiles.Embers;

/**
 * A room with an abandoned campfire in it.
 * @author Adam Whittaker
 */
public class CampfireRoom extends PlainRoom{

    
    /**
     * Creates an instance.
     * @param w The width.
     * @param h The height.
     */
    public CampfireRoom(int w, int h){
        super(DoorStyle.ANY, "Campfire room", w, h);
        assertDimensions(w, h, 5, 5);
    }
    
    
    @Override
    public void generate(Area a){
        super.generate(a);
        
        int _x = width/2, _y = height/2;
        
        //Generates the campfire.
        for(int x = _x-1;x<=_x+1;x++){
            for(int y = _y-1;y<=_y+1;y++){
                if(x==_x && y==_y) map[y][x] = new Barricade(false);
                else map[y][x] = new Embers(false);
            } 
        }
    }

}
