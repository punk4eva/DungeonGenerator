
package components.rooms;

import components.Area;
import components.tiles.Wall;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class StalagmiteRoom extends PlainRoom{
    
    
    private static final double STALAGMITE_CHANCE = 0.30;

    
    public StalagmiteRoom(int w, int h){
        super(DoorStyle.ANY, "Stalagmite Room", w, h);
        assertDimensions(w, h, 7, 7);
    }
    
    
    @Override
    public void generate(Area a){
        super.generate(a);
        
        for(int x=2;x<width-2;x++){
            for(int y=2;y<height-2;y++){
                if(R.nextDouble()<STALAGMITE_CHANCE) map[y][x] = 
                        new Wall("Stalagmite", "This is a geological formation "
                                + "that grows slowly when mineral water drips "
                                + "from the cave ceiling.", null);
            } 
        }
    }

}
