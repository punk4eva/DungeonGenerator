
package components.rooms;

import components.Area;
import components.decorations.assets.Grave;
import components.tiles.Grass;
import components.tiles.SpecialFloor;
import components.tiles.Statue;
import components.tiles.Tile;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * A room full of graves.
 * @author Adam Whittaker
 */
public class Graveyard extends PlainRoom{

    
    /**
     * Creates an instance.
     * @param w The width.
     * @param h The height.
     */
    public Graveyard(int w, int h){
        super(DoorStyle.ANY, "Graveyard", w, h);
        assertDimensions(w, h, 9, 9);
    }
    
    
    @Override
    @Unfinished
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("@Unfinished");
    }
    
    @Override
    public void generate(Area area){
        buildWalls(area);
        
        //Places a statue in the center.
        map[height/2][width/2] = new Statue(true);
        map[height/2 - 1][width/2] = new SpecialFloor("floor");
        map[height/2 + 1][width/2] = new SpecialFloor("floor");
        map[height/2][width/2 - 1] = new SpecialFloor("floor");
        map[height/2][width/2 + 1] = new SpecialFloor("floor");
        
        //Places the graves.
        for(int y=1;y<height-1;y++) for(int x=1;x<width-1;x++){
            if(map[y][x] == null){
                map[y][x] = new Grass(
                        R.nextDouble()<area.info.architecture.biomeProcessor.society.ruination/100D,
                        Tile.genFloor(area));
                if(isGraveEligable(x, y)) map[y][x].decoration = new Grave();
            }
        }
    }
    
    /**
     * Checks if a grave can be placed in the given space.
     * @param x The tile x.
     * @param y The tile y.
     * @return
     */
    private boolean isGraveEligable(int x, int y){
        //A grave cannot be placed too close to a wall or in the central row or
        //column.
        if(x<=1 || y<=1 || x>=width-2 || y>=height-2 ||
                x == width/2 || y == height/2) return false;
        //A grave can only be on an odd numbered tile.
        boolean xComp = (x<width/2) ? x%2==0 : (width - 1 - x)%2==0;
        boolean yComp = (y<height/2) ? y%2==0 : (height - 1 - y)%2==0;
        return xComp && yComp;
    }

}
