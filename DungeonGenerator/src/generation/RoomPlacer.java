
package generation;

import components.RoomDesc;
import graph.Graph;
import java.util.LinkedList;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class RoomPlacer{

    private final LinkedList<RoomDesc> rooms;
    private final Graph graph;
    
    public RoomPlacer(Graph g, LinkedList<RoomDesc> r){
        graph = g;
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
            for(i=0;i<15;i++){
                Integer[] point = generatePoint(width, height);
                if(spaceFree(point, width, height)){
                    mark(point, width, height, n, coords);
                    pastDim = new Integer[]{width, height};
                    n++;
                    break;
                }
            }
            if(i==15){
                //System.out.println("Unmark: " + n);
                n--;
                unmark(coords[n], pastDim[0], pastDim[1], n, coords);
            }
        }
    }

    protected Integer[] generatePoint(int width, int height){
        return new Integer[]{R.nextInt((graph.map[0].length-width)/2)*2,
            R.nextInt((graph.map.length-height)/2)*2};
    }

    protected boolean spaceFree(Integer[] c, int width, int height){
        for(int x=c[0];x<c[0]+width;x++)
            if(graph.map[c[1]][x].isCorridor||graph.map[c[1]+height][x].isCorridor) return false;
        for(int y=c[1];y<c[1]+height;y++)
            if(graph.map[y][c[0]].isCorridor||graph.map[y][c[0]+width].isCorridor) return false;
        return true;
    }

    protected void mark(Integer[] c, int width, int height, Integer n, Integer[][] coords){
        System.out.println("Marking: " + n + ", " + width + ", " + height);
        coords[n] = c;
        for(int x=c[0];x<c[0]+width;x++)
            for(int y=c[1];y<c[1]+height;y++)
                graph.map[y][x].isCorridor = true;
    }

    protected void unmark(Integer[] c, int width, int height, Integer n, Integer[][] coords){
        System.out.println("Unmarking: " + n + ", " + width + ", " + height);
        for(int x=c[0];x<c[0]+width;x++)
            for(int y=c[1];y<c[1]+height;y++)
                graph.map[y][x].isCorridor = false;
        coords[n] = null;
    }
    
}
