
package components.rooms;

import components.Area;
import components.tiles.Embers;
import components.tiles.Tile;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * A room having suffered a terrible accident is full of embers.
 * @author Adam Whittaker
 */
public class BurntRoom extends PlainRoom{
    
    
    /**
     * The chance of an individual tile being an ember.
     */
    private static final double EMBER_CHANCE = 0.5;

    
    /**
     * Creates an instance.
     * @param w The width.
     * @param h The height.
     */
    public BurntRoom(int w, int h){
        super(w, h);
    }


    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        
        //Fills the room with embers.
        for(int y=1;y<height-1;y++){
            for(int x=1;x<width-1;x++){
                if(R.nextDouble()<EMBER_CHANCE) map[y][x] = new Embers(false);
                else map[y][x] = Tile.genFloor(area);
            }
        }
    }

}
