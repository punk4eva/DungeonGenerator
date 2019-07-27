
package components.rooms;

import components.tiles.Tile;
import graph.Point.Type;

/**
 *
 * @author Adam Whittaker
 */
public class PlainRoom extends Room{

    public PlainRoom(int w, int h){
        super("Plain Room", w, h);
    }

    @Override
    public void generate(){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                if(y==0||x==0||x==width-1||y==height-1) map[y][x] = new Tile("wall", null, Type.WALL, null, null);
                else map[y][x] = new Tile("floor", null, Type.FLOOR, null, null);
            }
        }
    }

}
