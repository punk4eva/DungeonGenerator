
package components.rooms;

import components.Area;
import components.tiles.Tile;

/**
 *
 * @author Adam Whittaker
 */
public class PlainRoom extends Room{

    public PlainRoom(int w, int h){
        super("Plain Room", w, h);
    }

    protected void paintAndPave(Area area){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                if(y==0||x==0||x==width-1||y==height-1) map[y][x] = Tile.genWall(area);
                else map[y][x] = Tile.genFloor(area);
            }
        }
    }
    
    @Override
    public void generate(Area area){
        paintAndPave(area);
    }

}
