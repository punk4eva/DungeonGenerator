
package generation.rooms;

import components.Area;
import components.rooms.Room;
import components.tiles.Embers;
import components.tiles.Tile;
import generation.MultiPlacer;
import java.util.LinkedList;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class DenseFractalRoomPlacer implements MultiPlacer{

    private final Area area;
    private final double splitChance;
    private final LinkedList<int[]> rooms = new LinkedList<>();
    
    private final static int MIN_WIDTH = 5;
    
    
    public DenseFractalRoomPlacer(Area a, double spC){
        area = a;
        splitChance = spC;
    }
    
    
    @Override
    public void generate(){
        //buildOuterWall();
        fractalSplit(0, 0, area.info.width, area.info.height);
        generateRooms(area.info.architecture.biome.roomSelector);
        //fillFloor();
    }
    
    
    private void buildOuterWall(){
        for(int x=0;x<area.info.width;x++){
            area.graph.map[0][x].checked = true;
            area.graph.map[area.info.height-1][x].checked = true;
        }
        for(int y=1;y<area.info.height-1;y++){
            area.graph.map[y][0].checked = true;
            area.graph.map[y][area.info.width-1].checked = true;
        }
    }
    
    private void fractalSplit(int x_, int y_, int w, int h){
        int[] c = generateCoords(x_, y_, w, h);
        for(int x=c[0]+1;x<x_+w;x++) area.graph.map[c[1]][x].checked = true;
        for(int y=c[1]+1;y<y_+h;y++) area.graph.map[y][c[2]].checked = true;
        for(int x=c[2]-1;x>x_;x--) area.graph.map[c[3]][x].checked = true;
        for(int y=c[3]-1;y>y_;y--) area.graph.map[y][c[0]].checked = true;
        
        if(c[0]-x_>MIN_WIDTH*3 && c[3]-y_>MIN_WIDTH*3) fractalSplit(x_, y_, c[0]-x_, c[3]-y_);
        else if((c[0]-x_>MIN_WIDTH*2 || c[3]-y_>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(x_, y_, c[0]-x_, c[3]-y_);
        else rooms.add(new int[]{x_, y_, c[0]-x_+1, c[3]-y_});
        
        if(x_+w-c[0]>MIN_WIDTH*3 && c[1]-y_>MIN_WIDTH*3) fractalSplit(c[0], y_, x_+w-c[0], c[1]-y_);
        else if((x_+w-c[0]>MIN_WIDTH*2 || c[1]-y_>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[0], y_, x_+w-c[0], c[1]-y_);
        else rooms.add(new int[]{c[0], y_, x_+w-c[0], c[1]-y_+1});
        
        if(x_+w-c[2]>MIN_WIDTH*3 && y_+h-c[1]>MIN_WIDTH*3) fractalSplit(c[2], c[1], x_+w-c[2], y_+h-c[1]);
        else if((x_+w-c[2]>MIN_WIDTH*2 || y_+h-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[2], c[1], x_+w-c[2], y_+h-c[1]);
        else rooms.add(new int[]{c[2]-1, c[1], x_+w-c[2]+1, y_+h-c[1]});
        
        if(c[2]-x_>MIN_WIDTH*3 && y_+h-c[3]>MIN_WIDTH*3) fractalSplit(x_, c[3], c[2]-x_, y_+h-c[3]);
        else if((c[2]-x_>MIN_WIDTH*2 || y_+h-c[3]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(x_, c[3], c[2]-x_, y_+h-c[3]);
        else rooms.add(new int[]{x_, c[3]-1, c[2]-x_, y_+h-c[3]+1});
        
        if(c[2]-c[0]>MIN_WIDTH*3 && c[3]-c[1]>MIN_WIDTH*3) fractalSplit(c[0], c[1], c[2]-c[0], c[3]-c[1]);
        else if((c[2]-c[0]>MIN_WIDTH*2 || c[3]-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[0], c[1], c[2]-c[0], c[3]-c[1]);
        else rooms.add(new int[]{c[0], c[1], c[2]-c[0], c[3]-c[1]});
    }
    
    private void dissectRoom(int x_, int y_, int w, int h){
        if(w>MIN_WIDTH*2){
            int x0 = x_ + MIN_WIDTH + R.nextInt(w-MIN_WIDTH*2);
            for(int y=y_;y<h+y_;y++) area.graph.map[y][x0].checked = true;
        }
        if(h>MIN_WIDTH*2){
            int y0 = y_ + MIN_WIDTH + R.nextInt(h-MIN_WIDTH*2);
            for(int x=x_;x<w+x_;x++) area.graph.map[y0][x].checked = true;
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
    
    private void generateRooms(RoomSelector selector){
        Room r;
        for(int[] coords : rooms){
            r = selector.select(coords[2], coords[3]);
            
            r.ensureGenerated(area);
            r.map[0][0] = new Embers(false);
            
            area.blitRoom(r, coords[0], coords[1]);
        }
    }
    
}
