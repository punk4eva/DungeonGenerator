
package generation.rooms;

import components.Area;
import components.rooms.Room;
import gui.questions.RoomPlacerSpecifier;
import static utils.Utils.copy2DArray;
import java.util.List;
import java.util.function.Consumer;
import static utils.Utils.PERFORMANCE_LOG;
import static utils.Utils.R;

/**
 * Places Rooms randomly in an Area. If the Area is too small to support the 
 * Rooms, the generator tries to find a better solutions
 * @author Adam Whittaker
 */
public class RandomRoomPlacer extends AbstractRoomPlacer{

    
    /**
     * rooms: The list of Rooms that needs to be placed.
     * doorGenerator: The function that adds doors to a room.
     */
    private final List<Room> rooms;
    private final Consumer<Room> doorGenerator;
    
    /**
     * Internal limits on how many attempts can be made for (a) finding a space
     * to generate a Room (b) backtracking to find a better solution with more
     * Rooms.
     */
    private static final int PLACEMENT_ATTEMPT_LIMIT = 15, BACKTRACK_LIMIT = 600;
    
    
    /**
     * Creates an instance with a default door generation algorithm.
     * @param area The Area.
     * @param rooms The list of Rooms to be placed in the Area.
     */
    public RandomRoomPlacer(Area area, List<Room> rooms){
        this(area, rooms, room -> room.addDoorsSparcely(area));
    }
    
    /**
     * Creates an instance.
     * @param a The Area.
     * @param r The list of Rooms to be placed in the Area.
     * @param doorGen The function that adds doors to a room.
     */
    public RandomRoomPlacer(Area a, List<Room> r, Consumer<Room> doorGen){
        super(a);
        rooms = r;
        doorGenerator = doorGen;
    }
    
    
    @Override
    public void generate(){
        //Chooses a random orientation for each room.
        rooms.stream().forEach(r -> r.randomizeOrientation());
        //sorts rooms based on area
        rooms.sort(AbstractRoomPlacer::roomSizeComparator);
        
        int n = 0, bestRoomNum = -1; //the index of the room
        int i, width, height, placementFailCounter = 0; //incrementing variable
        int[][] coords = new int[rooms.size()][4], bestSolution = null;
        while(n<rooms.size()){
            width = rooms.get(n).getWidth();
            height = rooms.get(n).getHeight();
            
            for(i=0;i<PLACEMENT_ATTEMPT_LIMIT;i++){
                int[] point = generatePoint(width, height);
                if(spaceFree(point[0], point[1], width, height)){
                    mark(point, width, height, n, coords);
                    n++;
                    break;
                }
            }
            if(i==PLACEMENT_ATTEMPT_LIMIT){
                placementFailCounter++;
                if(bestRoomNum<n){
                    bestRoomNum = n;
                    bestSolution = copy2DArray(coords, n);
                }
                if(placementFailCounter>=BACKTRACK_LIMIT){
                    PERFORMANCE_LOG.dualPrint("RandomRoomPlacer cannot place this "
                            + "many rooms! " + bestRoomNum + "/" + rooms.size() + 
                            " rooms have been placed.");
                    coords = bestSolution;
                    remarkFromPreviousSolution(coords);
                    break;
                }
                for(int j=0;j<Math.min(placementFailCounter, n);j++){
                    n--;
                    unmark(coords[n], coords[n][2], coords[n][3], n, coords);
                }
            }
        }
        Room r;
        for(i=0;i<coords.length;i++){
            r = rooms.get(i);
            doorGenerator.accept(r);
            area.blitRoom(r, coords[i][0], coords[i][1]);
        }
    }
    
    /**
     * Remarks the positions of all Rooms from a previous solution.
     * @param c The solution.
     */
    private void remarkFromPreviousSolution(int[][] c){
        area.graph.flushRoomNumbers();
        for(int n=0;n<c.length;n++){
            for(int x=c[n][0];x<c[n][0]+c[n][2];x++)
                for(int y=c[n][1];y<c[n][1]+c[n][3];y++)
                    area.graph.map[y][x].roomNum = n;
        }
    }

    /**
     * Generates a coordinate for the top left corner of a Room so that it may
     * fit in the Area.
     * @param width The width of the Room.
     * @param height The height of the Room.
     * @return an int array representing the (x, y) coordinates.
     */
    protected int[] generatePoint(int width, int height){
        return new int[]{
            R.nextInt((area.graph.map[0].length-width-3)/2)*2+2,
            R.nextInt((area.graph.map.length-height-3)/2)*2+2};
    }

    /**
     * Marks the Area's graph with the position of a given Room.
     * @param c The x, y coordinates of the top left of the room.
     * @param width The width of the Room.
     * @param height The height of the Room.
     * @param n The room number.
     * @param coords The current coordinates of all Rooms.
     */
    protected void mark(int[] c, int width, int height, int n, int[][] coords){
        coords[n] = new int[]{c[0], c[1], width, height};
        mark(c[0], c[1], width, height, n);
    }

    /**
     * Removes a mark from the Area's graph of the position of a given Room.
     * @param c The x, y coordinates of the top left of the room.
     * @param width The width of the Room.
     * @param height The height of the Room.
     * @param n The room number.
     * @param coords The current coordinates of all Rooms.
     */
    protected void unmark(int[] c, int width, int height, int n, int[][] coords){
        for(int x=c[0];x<c[0]+width;x++)
            for(int y=c[1];y<c[1]+height;y++)
                area.graph.map[y][x].roomNum = -1;
        coords[n][0] = -1;
    }
    
    
    public static final RoomPlacerSpecifier<RandomRoomPlacer> RANDOM_ROOM_SPECIFIER;
    static{
        try{
            RANDOM_ROOM_SPECIFIER = new RoomPlacerSpecifier<>(
                    RandomRoomPlacer.class.getConstructor(Area.class, List.class),
                    RandomRoomPlacer.class, "Random Room Placer", 
                    "Design the random room placement algorithm");
        }catch(NoSuchMethodException e){
            throw new IllegalStateException(e);
        }
    }
    
}
