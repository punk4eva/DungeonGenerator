
package generation.rooms;

import components.Area;
import components.tiles.Tile;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class DenseFractalRoomPlacer implements RoomPlacer{

    private final Area area;
    private final double splitChance;
    
    private final static int MIN_WIDTH = 5;
    
    
    public DenseFractalRoomPlacer(Area a, double spC){
        area = a;
        splitChance = spC;
    }
    
    
    @Override
    public void generate(){
        buildOuterWall();
        fractalSplit(0, 0, area.info.width, area.info.height);
        fillFloor();
    }
    
    
    private void buildOuterWall(){
        for(int x=0;x<area.info.width;x++){
            area.map[0][x] = Tile.genWall(area);
            area.map[area.info.height-1][x] = Tile.genWall(area);
        }
        for(int y=1;y<area.info.height-1;y++){
            area.map[y][0] = Tile.genWall(area);
            area.map[y][area.info.width-1] = Tile.genWall(area);
        }
    }
    
    private void fractalSplit(int x_, int y_, int w, int h){
        int[] c = generateCoords(x_, y_, w, h);
        for(int x=c[0]+1;x<x_+w;x++) area.map[c[1]][x] = Tile.genWall(area);
        for(int y=c[1]+1;y<y_+h;y++) area.map[y][c[2]] = Tile.genWall(area);
        for(int x=c[2]-1;x>x_;x--) area.map[c[3]][x] = Tile.genWall(area);
        for(int y=c[3]-1;y>y_;y--) area.map[y][c[0]] = Tile.genWall(area);
        
        if(c[0]-x_>MIN_WIDTH*3 && c[3]-y_>MIN_WIDTH*3) fractalSplit(x_, y_, c[0]-x_, c[3]-y_);
        else if((c[0]-x_>MIN_WIDTH*2 || c[3]-y_>MIN_WIDTH*2) && R.nextDouble()<splitChance) disectRoom(x_, y_, c[0]-x_, c[3]-y_);
        
        if(x_+w-c[0]>MIN_WIDTH*3 && c[1]-y_>MIN_WIDTH*3) fractalSplit(c[0], y_, x_+w-c[0], c[1]-y_);
        else if((x_+w-c[0]>MIN_WIDTH*2 || c[1]-y_>MIN_WIDTH*2) && R.nextDouble()<splitChance) disectRoom(c[0], y_, x_+w-c[0], c[1]-y_);
        
        if(x_+w-c[2]>MIN_WIDTH*3 && y_+h-c[1]>MIN_WIDTH*3) fractalSplit(c[2], c[1], x_+w-c[2], y_+h-c[1]);
        else if((x_+w-c[2]>MIN_WIDTH*2 || y_+h-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) disectRoom(c[2], c[1], x_+w-c[2], y_+h-c[1]);
        
        if(c[2]-x_>MIN_WIDTH*3 && y_+h-c[3]>MIN_WIDTH*3) fractalSplit(x_, c[3], c[2]-x_, y_+h-c[3]);
        else if((c[2]-x_>MIN_WIDTH*2 || y_+h-c[3]>MIN_WIDTH*2) && R.nextDouble()<splitChance) disectRoom(x_, c[3], c[2]-x_, y_+h-c[3]);
        
        if(c[2]-c[0]>MIN_WIDTH*3 && c[3]-c[1]>MIN_WIDTH*3) fractalSplit(c[0], c[1], c[2]-c[0], c[3]-c[1]);
        else if((c[2]-c[0]>MIN_WIDTH*2 || c[3]-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) disectRoom(c[0], c[1], c[2]-c[0], c[3]-c[1]);
    }
    
    private void disectRoom(int x_, int y_, int w, int h){
        if(w>MIN_WIDTH*2){
            int x0 = x_ + MIN_WIDTH + R.nextInt(w-MIN_WIDTH*2);
            for(int y=y_;y<h+y_;y++) area.map[y][x0] = Tile.genWall(area);
        }
        if(h>MIN_WIDTH*2){
            int y0 = y_ + MIN_WIDTH + R.nextInt(h-MIN_WIDTH*2);
            for(int x=x_;x<w+x_;x++) area.map[y0][x] = Tile.genWall(area);
        }
    }
    
    private int[] generateCoords(int x_, int y_, int w, int h){
        return new int[]{x_ + R.nextInt(w/2-MIN_WIDTH*3/2) + MIN_WIDTH, 
            y_ + R.nextInt(h/2-MIN_WIDTH*3/2) + MIN_WIDTH,
            x_ + R.nextInt(w/2-MIN_WIDTH*3/2) + w/2 + MIN_WIDTH/2,
            y_ + R.nextInt(h/2-MIN_WIDTH*3/2) + h/2 + MIN_WIDTH/2};
        /*return new int[]{x_ + w/4 + R.nextInt(2*w/5 - w/4), 
            y_ + h/4 + R.nextInt(2*h/5 - h/4),
            x_ + R.nextInt(2*w/5 - w/4) + 3*w/5,
            y_ + R.nextInt(2*h/5 - h/4) + 3*h/5};*/
    }
    
    private void fillFloor(){
        for(int y=1;y<area.info.height-1;y++)
            for(int x=1;x<area.info.width-1;x++)
                if(area.map[y][x]==null) area.map[y][x] = Tile.genFloor(area);
    }
    
}
