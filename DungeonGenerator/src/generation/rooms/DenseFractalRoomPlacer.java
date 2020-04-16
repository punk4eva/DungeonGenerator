
package generation.rooms;

import components.Area;
import components.rooms.Room;
import generation.MultiPlacer;
import java.util.LinkedList;
import static utils.Utils.R;
import utils.Utils.Unfinished;

/**
 * A room placement algorithm that recursively splits the area into smaller 
 * rectangles until they are small enough to be Rooms.
 * @author Adam Whittaker
 */
@Unfinished("To implement fully")
public class DenseFractalRoomPlacer extends AbstractRoomPlacer 
        implements MultiPlacer{

    
    /**
     * splitChance: The chance for a bigger room to be split into two smaller
     * rooms.
     * rooms: The list of rooms that have been generated.
     */
    private final double splitChance;
    private final LinkedList<int[]> rooms = new LinkedList<>();
    
    private final static int MIN_WIDTH = 5;
    
    
    /**
     * Creates a new instance.
     * @param a The Area.
     * @param spC The chance to split a big room.
     */
    public DenseFractalRoomPlacer(Area a, double spC){
        super(a);
        splitChance = spC;
    }
    
    
    @Override
    public void generate(){
        //Initial call to the recursive splitting algorithm.
        fractalSplit(0, 0, area.info.width, area.info.height);
        //Fills the spaces generated with randomized rooms.
        generateRooms(area.info.architecture.biomeProcessor.roomSelector);
    }
    
    
    /**
     * The recursive room splitting algorithm.
     * @param x The x coordinate of the top left of the current rectangle.
     * @param y The y coordinate of the top left of the current rectangle.
     * @param w The width.
     * @param h The height.
     */
    private void fractalSplit(int x, int y, int w, int h){
        //Generates the top-left and bottom-right coordinates of the central 
        //rectangle.
        int[] c = generateCoords(x, y, w, h);
        
        //Runs the algorithm on the top-left rectangle.
        //If the rectangle is small enough to be a small room, the algorithm 
        //terminates, adding the room to the finished list.
        //If it is small enough to be a big room, it has a chance to be split 
        //into 2 to 4 smaller rooms (rather than 5).
        //If it is too big to be a room, the rectangle is split into 5 smaller
        //rectangles using the algorithm.
        if(c[0]-x>MIN_WIDTH*3 && c[3]-y>MIN_WIDTH*3) fractalSplit(x, y, c[0]-x+1, c[3]-y+1);
        else if((c[0]-x>MIN_WIDTH*2 || c[3]-y>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(x, y, c[0]-x+1, c[3]-y+1);
        else rooms.add(new int[]{x, y, c[0]-x+1, c[3]-y+1});
        
        //Runs the algorithm on the top-right rectangle.
        if(x+w-c[0]>MIN_WIDTH*3 && c[1]-y>MIN_WIDTH*3) fractalSplit(c[0], y, x+w-c[0], c[1]-y+1);
        else if((x+w-c[0]>MIN_WIDTH*2 || c[1]-y>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[0], y, x+w-c[0], c[1]-y+1);
        else rooms.add(new int[]{c[0], y, x+w-c[0], c[1]-y+1});
        
        //Runs the algorithm on the bottom-left rectangle.
        if(x+w-c[2]>MIN_WIDTH*3 && y+h-c[1]>MIN_WIDTH*3) fractalSplit(c[2], c[1], x+w-c[2], y+h-c[1]);
        else if((x+w-c[2]>MIN_WIDTH*2 || y+h-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[2], c[1], x+w-c[2],
                        y+h-c[1]);
        else rooms.add(new int[]{c[2], c[1], x+w-c[2], y+h-c[1]});
        
        //Runs the algorithm on the top-right rectangle.
        if(c[2]-x>MIN_WIDTH*3 && y+h-c[3]>MIN_WIDTH*3) fractalSplit(x, c[3], c[2]-x+1, y+h-c[3]);
        else if((c[2]-x>MIN_WIDTH*2 || y+h-c[3]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(x, c[3], c[2]-x+1, y+h-c[3]);
        else rooms.add(new int[]{x, c[3], c[2]-x+1, y+h-c[3]});
        
        //Runs the algorithm on the middle rectangle.
        if(c[2]-c[0]>MIN_WIDTH*3 && c[3]-c[1]>MIN_WIDTH*3) fractalSplit(c[0], c[1], c[2]-c[0]+1, c[3]-c[1]+1);
        else if((c[2]-c[0]>MIN_WIDTH*2 || c[3]-c[1]>MIN_WIDTH*2) && R.nextDouble()<splitChance) dissectRoom(c[0], c[1], c[2]-c[0]+1, 
                        c[3]-c[1]+1);
        else rooms.add(new int[]{c[0], c[1], c[2]-c[0]+1, c[3]-c[1]+1});
    }
    
    /**
     * Splits a big room into 2 to 4 smaller ones.
     * @param x The x coordinate of the top left of the current room.
     * @param y The y coordinate of the top left of the current room.
     * @param w The width.
     * @param h The height.
     */
    private void dissectRoom(int x, int y, int w, int h){
        //The coordinates of the split.
        int x0, y0;
        if(w>MIN_WIDTH*2 && h>MIN_WIDTH*2){
            //Splits the room in four using a wall and adds the new rooms to the
            //room list.
            x0 = x + MIN_WIDTH + R.nextInt(w-MIN_WIDTH*2);
            for(int y_=y;y_<h+y;y_++) area.graph.map[y_][x0].checked = true;
            y0 = y + MIN_WIDTH + R.nextInt(h-MIN_WIDTH*2);
            for(int x_=x;x_<w+x;x_++) area.graph.map[y0][x_].checked = true;
            rooms.add(new int[]{x, y, x0-x+1, y0-y+1});
            rooms.add(new int[]{x0, y, x+w-x0, y0-y+1});
            rooms.add(new int[]{x, y0, x0-x+1, y+h-y0});
            rooms.add(new int[]{x0, y0, x+w-x0, y+h-y0});
        //Otherwise splits the room in two lengthways or horizontally depending
        //on which side is longer.
        }else{
            if(w>MIN_WIDTH*2){
                x0 = x + MIN_WIDTH + R.nextInt(w-MIN_WIDTH*2);
                for(int y_=y;y_<h+y;y_++) area.graph.map[y_][x0].checked = true;
                rooms.add(new int[]{x, y, x0-x+1, h});
                rooms.add(new int[]{x0, y, x+w-x0, h});
            }
            if(h>MIN_WIDTH*2){
                y0 = y + MIN_WIDTH + R.nextInt(h-MIN_WIDTH*2);
                for(int x_=x;x_<w+x;x_++) area.graph.map[y0][x].checked = true;
                rooms.add(new int[]{x, y, w, y0-y+1});
                rooms.add(new int[]{x, y0, w, y+h-y0});
            }
        }
    }
    
    
    /**
     * Generates two randomized centralish coordinates within the given 
     * rectangle. The coordinates represent the position of the central 
     * rectangle in the splitting algorithm.
     * @param x The top-left x of the current rectangle.
     * @param y The top-left y of the current rectangle.
     * @param w The width.
     * @param h The height.
     * @return The coordinates in an array of the form {top-left x, top-left y, 
     * bottom-right x, bottom-right y}.
     */
    private int[] generateCoords(int x, int y, int w, int h){
        return new int[]{x + R.nextInt(w/2-3*MIN_WIDTH/2) + MIN_WIDTH, 
            y + R.nextInt(h/2-3*MIN_WIDTH/2) + MIN_WIDTH,
            x + R.nextInt(w/2-3*MIN_WIDTH/2) + w/2 + MIN_WIDTH/2,
            y + R.nextInt(h/2-3*MIN_WIDTH/2) + h/2 + MIN_WIDTH/2};
    }
    
    
    /**
     * Fills randomly generated Rooms into the rectangles generated by the 
     * algorithm.
     * @param selector The algorithm for generating new rooms.
     */
    @Unfinished("Complete implementation of doors and locked Rooms.")
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
