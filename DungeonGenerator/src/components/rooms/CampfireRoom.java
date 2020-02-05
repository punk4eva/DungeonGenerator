
package components.rooms;

import components.Area;
import components.tiles.Barricade;
import components.tiles.Embers;

/**
 *
 * @author Adam Whittaker
 */
public class CampfireRoom extends PlainRoom{

    
    public CampfireRoom(int w, int h){
        super(DoorStyle.ANY, "Campfire room", w, h);
        assertDimensions(w, h, 5, 5);
    }
    
    
    @Override
    public void generate(Area a){
        super.generate(a);
        
        int _x = width/2, _y = height/2;
        
        for(int x = _x-1;x<=_x+1;x++){
            for(int y = _y-1;y<=_y+1;y++){
                if(x==_x && y==_y) map[y][x] = new Barricade(false);
                else map[y][x] = new Embers(false);
            } 
        }
    }

}
