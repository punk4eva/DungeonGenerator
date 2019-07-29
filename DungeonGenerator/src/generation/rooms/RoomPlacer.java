
package generation.rooms;

import components.Area;
import components.rooms.Room;
import java.util.LinkedList;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class RoomPlacer{

    private final LinkedList<Room> rooms;
    private final Area area;
    
    private static final int ATTEMPT_LIMIT = 15;
    
    public RoomPlacer(Area a, LinkedList<Room> r){
        area = a;
        rooms = r;
    }
    
    public void generate(){
        //sorts rooms based on area
        rooms.sort((r, r1) -> new Integer(r1.width*r1.height).compareTo(r.width*r.height));
        //Chooses a random orientation for each room.
        rooms.stream().forEach(r -> {
            r.orientation = R.nextInt(4);
        });
        
        Integer n = 0; //the index of the room
        int i, width, height; //incrementing variable
        Integer[][] coords = new Integer[rooms.size()][2];
        Integer[] pastDim = new Integer[2];
        while(n<rooms.size()){
            width = rooms.get(n).orientation%2==0 ? rooms.get(n).width : rooms.get(n).height;
            height = rooms.get(n).orientation%2!=0 ? rooms.get(n).width : rooms.get(n).height;
            for(i=0;i<ATTEMPT_LIMIT;i++){
                Integer[] point = generatePoint(width, height);
                if(spaceFree(point, width, height)){
                    mark(point, width, height, n, coords);
                    pastDim = new Integer[]{width, height};
                    n++;
                    break;
                }
            }
            if(i==ATTEMPT_LIMIT){
                //System.out.println("Unmark: " + n);
                n--;
                unmark(coords[n], pastDim[0], pastDim[1], n, coords);
            }
        }
        Room r;
        for(i=0;i<rooms.size();i++){
            r = rooms.get(i);
            area.blitRoom(r, coords[i][0], coords[i][1]);
        }
    }

    protected Integer[] generatePoint(int width, int height){
        return new Integer[]{R.nextInt((area.graph.map[0].length-width-4)/2)*2+2,
            R.nextInt((area.graph.map.length-height-4)/2)*2+2};
    }

    protected boolean spaceFree(Integer[] c, int width, int height){
        for(int x=c[0];x<c[0]+width;x++)
            if(area.graph.map[c[1]][x].roomNum!=-1||area.graph.map[c[1]+height][x].roomNum!=-1) return false;
        for(int y=c[1];y<c[1]+height;y++)
            if(area.graph.map[y][c[0]].roomNum!=-1||area.graph.map[y][c[0]+width].roomNum!=-1) return false;
        return true;
    }

    protected void mark(Integer[] c, int width, int height, Integer n, Integer[][] coords){
        System.out.println("Marking: " + n + ", " + width + ", " + height);
        coords[n] = c;
        for(int x=c[0];x<c[0]+width;x++)
            for(int y=c[1];y<c[1]+height;y++)
                area.graph.map[y][x].roomNum = n;
    }

    protected void unmark(Integer[] c, int width, int height, Integer n, Integer[][] coords){
        System.out.println("Unmarking: " + n + ", " + width + ", " + height);
        for(int x=c[0];x<c[0]+width;x++)
            for(int y=c[1];y<c[1]+height;y++)
                area.graph.map[y][x].roomNum = -1;
        coords[n] = null;
    }
    
}
