
package components.rooms;

import components.Area;
import components.tiles.Bookshelf;
import components.tiles.SpecialFloor;
import static components.tiles.Tile.wallTrap;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Library extends PlainRoom{
    
    public final static int MIN_WIDTH = 7, MIN_HEIGHT = 7;
    

    public Library(int w, int h){
        super("Library", w, h);
        assertDimensions(w, h, 7, 7);
    }

    @Override
    public void generate(Area area){
        buildWalls(area);
        
        int mod;
        if(width<14) mod = 3;
        else if(width<18) mod = 4;
        else mod = 5;
        
        for(int y=1;y<height-1;y++){
            for(int x=1;x<width-1;x++){
                if(y>1&&y<height-2&&y%2==0&&x%mod!=0&&
                        x>1&&x<width-2) map[y][x] = new Bookshelf(null, 
                                R.nextDouble()<0.5 ? wallTrap(area) : null, false); //trap chance halved
                else map[y][x] = new SpecialFloor("library floor");
            }
        }
    }

    @Override
    protected void plopItems(Area area){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
