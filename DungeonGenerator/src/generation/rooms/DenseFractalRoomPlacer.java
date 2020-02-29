
package generation.rooms;

import components.Area;
import components.rooms.Room;
import components.tiles.Embers;
import components.tiles.Floor;
import components.tiles.Tile;
import components.tiles.Wall;
import generation.MultiPlacer;
import java.util.LinkedList;
import static utils.Utils.R;
import static utils.Utils.printArray;

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
        fractalSplit(0, 0, area.info.width, area.info.height);
        generateRooms(area.info.architecture.biomeProcessor.roomSelector);
    }
    
    private void fractalSplit(int x_, int y_, int w, int h){
        int[] c = generateCoords(x_, y_, w, h);
        
        if(c[0]-x_>MIN_WIDTH*3 && c[3]-y_>MIN_WIDTH*3) fractalSplit(x_, y_, c[0]-x_+1, c[3]-y_+1);
        else if((c[0]-x_>MIN_WIDTH*2 || c[3]-y_>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(x_, y_, c[0]-x_+1, c[3]-y_+1);
        else rooms.add(new int[]{x_, y_, c[0]-x_+1, c[3]-y_+1});
        
        if(x_+w-c[0]>MIN_WIDTH*3 && c[1]-y_>MIN_WIDTH*3) fractalSplit(c[0], y_, x_+w-c[0], c[1]-y_+1);
        else if((x_+w-c[0]>MIN_WIDTH*2 || c[1]-y_>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[0], y_, x_+w-c[0], c[1]-y_+1);
        else rooms.add(new int[]{c[0], y_, x_+w-c[0], c[1]-y_+1});
        
        if(x_+w-c[2]>MIN_WIDTH*3 && y_+h-c[1]>MIN_WIDTH*3) fractalSplit(c[2], c[1], x_+w-c[2], y_+h-c[1]);
        else if((x_+w-c[2]>MIN_WIDTH*2 || y_+h-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[2], c[1], x_+w-c[2], y_+h-c[1]);
        else rooms.add(new int[]{c[2], c[1], x_+w-c[2], y_+h-c[1]});
        
        if(c[2]-x_>MIN_WIDTH*3 && y_+h-c[3]>MIN_WIDTH*3) fractalSplit(x_, c[3], c[2]-x_+1, y_+h-c[3]);
        else if((c[2]-x_>MIN_WIDTH*2 || y_+h-c[3]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(x_, c[3], c[2]-x_+1, y_+h-c[3]);
        else rooms.add(new int[]{x_, c[3], c[2]-x_+1, y_+h-c[3]});
        
        if(c[2]-c[0]>MIN_WIDTH*3 && c[3]-c[1]>MIN_WIDTH*3) fractalSplit(c[0], c[1], c[2]-c[0]+1, c[3]-c[1]+1);
        else if((c[2]-c[0]>MIN_WIDTH*2 || c[3]-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[0], c[1], c[2]-c[0]+1, c[3]-c[1]+1);
        else rooms.add(new int[]{c[0], c[1], c[2]-c[0]+1, c[3]-c[1]+1});
    }
    
    private void dissectRoom(int x_, int y_, int w, int h){
        int x0, y0;
        if(w>MIN_WIDTH*2 && h>MIN_WIDTH*2){
            x0 = x_ + MIN_WIDTH + R.nextInt(w-MIN_WIDTH*2);
            for(int y=y_;y<h+y_;y++) area.graph.map[y][x0].checked = true;
            y0 = y_ + MIN_WIDTH + R.nextInt(h-MIN_WIDTH*2);
            for(int x=x_;x<w+x_;x++) area.graph.map[y0][x].checked = true;
            rooms.add(new int[]{x_, y_, x0-x_+1, y0-y_+1});
            rooms.add(new int[]{x0, y_, x_+w-x0, y0-y_+1});
            rooms.add(new int[]{x_, y0, x0-x_+1, y_+h-y0});
            rooms.add(new int[]{x0, y0, x_+w-x0, y_+h-y0});
        }else{
            if(w>MIN_WIDTH*2){
                x0 = x_ + MIN_WIDTH + R.nextInt(w-MIN_WIDTH*2);
                for(int y=y_;y<h+y_;y++) area.graph.map[y][x0].checked = true;
                rooms.add(new int[]{x_, y_, x0-x_+1, h});
                rooms.add(new int[]{x0, y_, x_+w-x0, h});
            }
            if(h>MIN_WIDTH*2){
                y0 = y_ + MIN_WIDTH + R.nextInt(h-MIN_WIDTH*2);
                for(int x=x_;x<w+x_;x++) area.graph.map[y0][x].checked = true;
                rooms.add(new int[]{x_, y_, w, y0-y_+1});
                rooms.add(new int[]{x_, y0, w, y_+h-y0});
            }
        }
    }
    
    private int[] generateCoords(int x_, int y_, int w, int h){
        return new int[]{x_ + R.nextInt(w/2-3*MIN_WIDTH/2) + MIN_WIDTH, 
            y_ + R.nextInt(h/2-3*MIN_WIDTH/2) + MIN_WIDTH,
            x_ + R.nextInt(w/2-3*MIN_WIDTH/2) + w/2 + MIN_WIDTH/2,
            y_ + R.nextInt(h/2-3*MIN_WIDTH/2) + h/2 + MIN_WIDTH/2};
    }
    
    private void generateRooms(RoomSelector selector){
        Room r;
        for(int[] c : rooms){
            r = selector.select(c[2], c[3]);
            r.ensureGenerated(area);
            //r.addDoorsSparcely(area);
            area.blitRoom(r, c[0], c[1]);
        }
    }
    
}
