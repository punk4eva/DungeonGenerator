
package components.rooms;

import components.Area;
import components.tiles.Barricade;
import components.tiles.Bookshelf;
import components.tiles.PassageTile;
import components.tiles.SpecialFloor;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * A barricaded storage room filled with bookshelves and wooden logs.
 * @author Adam Whittaker
 */
public class StorageRoom extends PlainRoom{

    
    /**
     * The chance for a Tile to be a "decoration".
     */
    private static final double DECORATION_CHANCE = 0.1;
    
    
    /**
     * Creates an instance.
     * @param w The width.
     * @param h The height.
     */
    public StorageRoom(int w, int h){
        super(DoorStyle.ONE, "Storage", w, h);
        assertDimensions(w, h, 4, 4);
    }
    
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        
        //Places the logs and shelves and fills in the floor.
        for(int y=1;y<height-1;y++){
            for(int x=1;x<width-1;x++){
                if(y != 1 && y != height-2 && x != 1 && x != width-2 
                        && R.nextDouble()<DECORATION_CHANCE){
                    if(R.nextDouble()<0.5) map[y][x] = new Barricade(false);
                    else map[y][x] = new Bookshelf(null, false);
                }else map[y][x] = new SpecialFloor("dusty floor");
            }
        }
    }

    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public PassageTile getEntrance(Area area){
        return new Barricade(true);
    }

}
