
package components.rooms;

import components.Area;
import components.tiles.Bookshelf;
import components.tiles.Cauldron;
import components.tiles.SpecialFloor;
import static components.tiles.Tile.wallTrap;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Laboratory extends PlainLockedRoom{

    public Laboratory(int w, int h){
        super("Laboratory", w, h);
        assertDimensions(w, h, 6, 6);
    }
    
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        
        for(int x=1;x<width-1;x++){
            map[1][x] = new Bookshelf(null, 
                    R.nextDouble()<0.5 ? wallTrap(area) : null, false); //trap chance halved
            for(int y=2;y<height-1;y++){
                map[y][x] = new SpecialFloor("laboratory floor");
            }
        }
        
        map[height/2][width/2] = new Cauldron("alchemy pot", true);
    }

}
