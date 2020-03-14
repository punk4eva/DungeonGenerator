
package components.rooms;

import components.Area;
import components.tiles.Bookshelf;
import components.tiles.Cauldron;
import components.tiles.SpecialFloor;

/**
 * A room with a cauldron and bookshelves.
 * @author Adam Whittaker
 */
public class Laboratory extends PlainLockedRoom{

    
    /**
     * Creates a new instance.
     * @param w The width.
     * @param h The height.
     */
    public Laboratory(int w, int h){
        super(DoorStyle.SOUTH, "Laboratory", w, h);
        assertDimensions(w, h, 6, 6);
    }
    
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        
        //Places a line of bookshelves and fills in the floor.
        for(int x=1;x<width-1;x++){
            map[1][x] = new Bookshelf(null, false);
            for(int y=2;y<height-1;y++){
                map[y][x] = new SpecialFloor("laboratory floor");
            }
        }
        
        //The alchemy pot.
        map[height/2][width/2] = new Cauldron("alchemy pot", "A cauldron for brewing magical items.", true);
    }

}
