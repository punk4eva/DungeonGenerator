
package components.rooms;

import components.Area;
import components.tiles.Tile;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
public class PlainLockedRoom extends PlainRoom{

    public PlainLockedRoom(int w, int h){
        super("Plain locked room", w, h);
    }
    
    
    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    
    @Override
    public void addDoorsRandomly(Area area){
        addDoors(area, 1);
    }
    
    @Override
    public void addDoorsSparcely(Area area){
        addDoors(area, 1);
    }
    
    @Override
    protected void addDoors(Area area, int numDoors){
        ensureGenerated(area);
        switch(R.nextInt(4)){
            case 0: 
                map[R.nextInt(height-2)+1][0] = Tile.genDoor(area, true);
                break;
            case 1:
                map[R.nextInt(height-2)+1][width-1] = Tile.genDoor(area, true);
                break;
            case 2:
                map[0][R.nextInt(width-2)+1] = Tile.genDoor(area, true);
                break;
            case 3:
                map[height-1][R.nextInt(width-2)+1] = Tile.genDoor(area, true);
                break;
        }
    }

}
