
package components.rooms;

import components.Area;
import components.tiles.PassageTile;
import components.tiles.Tile;
import graph.Point.Type;
import utils.Distribution;
import static utils.Utils.R;
import static utils.Utils.PERFORMANCE_LOG;

/**
 * This class represents a room in the dungeon.
 * @author Adam Whittaker
 */
public abstract class Room{
    
    
    /**
     * The possible styles of door placement.
     */
    public enum DoorStyle{
        ANY, ONE, SOUTH;
    }
    

    /**
     * x, y: The x and y coordinates.
     * Name: The name of this room.
     * Description: The description of this room.
     * width, height: The dimension of the room.
     * Generated: Whether the Room has been generated or not (lazy generation).
     * Map: The tile map.
     */
    public int x, y;
    public final String name;
    public String description;
    protected final int width, height;
    public int orientation;
    private boolean generated = false, itemsPlopped = false;
    public final Tile[][] map;
    public final DoorStyle doorStyle;
    
    
    /**
     * Creates a new instance.
     * @param dS The style of doors.
     * @param n The name
     * @param w The width
     * @param h The height
     */
    public Room(DoorStyle dS, String n, int w, int h){
        name = n;
        doorStyle = dS;
        width = w;
        height = h;
        map = new Tile[height][width];
    }
    
    
    /**
     * Generates this Room if it is not already generated.
     * @param area The Area that the room is being generated for.
     */
    public void ensureGenerated(Area area){
        if(!generated){
            generate(area);
            generated = true;
        }
    }
    
    /**
     * Checks whether this Room has been populated with Items and populates it
     * if it hasn't.
     * @param area The Area that this Room belongs to.
     */
    public void ensurePopulated(Area area){
        if(!itemsPlopped){
            plopItems(area);
            itemsPlopped = true;
        }
    }
    
    /**
     * Generates this room.
     * @param area The Area that the room is being generated for.
     */
    protected abstract void generate(Area area);
    
    /**
     * Populates this Room with Items.
     * @param area
     */
    protected abstract void plopItems(Area area);
    
    public abstract PassageTile getEntrance(Area area);
    
    
    /**
     * Sets the coordinates of this Room relative to its Area.
     * @param x1
     * @param y1
     */
    public final void setCoords(int x1, int y1){
        x = x1;
        y = y1;
    }
    
    /**
     * Populates this Room with a medium to high random number of Doors.
     * @param area The Area object that this Room is being created for.
     */
    public void addDoorsRandomly(Area area){
        addDoors(area, new Distribution(new double[]{3,4,6,4,2,1}).next()+1);
    }
    
    /**
     * Populates this Room with a small random number of Doors.
     * @param area The Area object that this Room is being created for.
     */
    public void addDoorsSparcely(Area area){
        addDoors(area, new Distribution(new double[]{3,3,1}).next()+1);
    }
    
    /**
     * Populates this Room with Doors randomly.
     * @param area The Area object that this Room is being created for.
     * @param numDoors The number of doors to add.
     */
    protected void addDoors(Area area, int numDoors){
        ensureGenerated(area);
        int failed = 0;
        while(numDoors>0||failed>=40){
            int x, y;
            if(R.nextDouble()<0.5){
                x = 1 +  R.nextInt(width-2);
                y = R.nextDouble()<0.5 ? 0 : height-1;
            }else{
                y = 1 + R.nextInt(height-2);
                x = R.nextDouble()<0.5 ? 0 : width-1;
            }
            if(map[y][x].equals(Type.WALL)){
                if(
                        (y!=0||(x!=0&&x!=width-1)&&(y != height-1 || (x != 0 && x != width-1)))&&
                        ((y!=0&&y!=height-1)||(!map[y][x+1].equals(Type.DOOR)&&!map[y][x-1].equals(Type.DOOR)))&&
                        ((x!=0&&x!=width-1)||(!map[y+1][x].equals(Type.DOOR)&&!map[y-1][x].equals(Type.DOOR)))
                ){
                    numDoors--;
                    map[y][x] = getEntrance(area);
                }else failed++;
            }
        }
        if(failed>=40) PERFORMANCE_LOG.println("Failed to add doors to room: " + name + "\n" +
                    "Dimensions: " + width + ", " + height + "\n" +
                    "Orientation: " + orientation + "\n" +
                    "Number of doors remaining: " + numDoors);
    }
    
    /**
     * Returns the effective width of the room given its orientation.
     * @return
     */
    public int getWidth(){
        return orientation%2==0 ? width : height;
    }
    
    /**
     * Returns the effective height of the room given its orientation.
     * @return
     */
    public int getHeight(){
        return orientation%2==0 ? height : width;
    }
    
    /**
     * Sets the room to a random orientation.
     */
    public void randomizeOrientation(){
        orientation = R.nextInt(4);
    }
    
    
    /**
     * Checks that the given width and height for the room meet the Room's 
     * minimum requirements for generation. Throws an IllegalArgumentException
     * if the dimensions are too small.
     * @param w The proposed width.
     * @param h The proposed height.
     * @param minW The minimum width.
     * @param minH The minimum height.
     */
    public static void assertDimensions(int w, int h, int minW, int minH){
        if(w<minW || h<minH) throw new IllegalArgumentException("Dimensions " + w + ", " + h + " are to small.");
    }
    
}
