
package components.rooms;

import components.Area;
import components.tiles.Tile;
import graph.Point.Type;
import utils.Distribution;
import static utils.Utils.R;
import static utils.Utils.exceptionStream;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents a room in the dungeon.
 */
public abstract class Room{

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
    public final int width, height;
    public int orientation;
    private boolean generated = false, itemsPlopped = false;
    public final Tile[][] map;
    
    
    /**
     * Creates a new instance.
     * @param n
     * @param w
     * @param h
     */
    public Room(String n, int w, int h){
        name = n;
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
    
    protected abstract void plopItems(Area area);
    
    
    public final void setCoords(int x1, int y1){
        x = x1;
        y = y1;
    }
    
    /**
     * Populates this Room with Doors.
     * @param area The Area object that this Room is being created for.
     */
    public void addDoorsRandomly(Area area){
        addDoors(area, new Distribution(new double[]{3,4,6,4,2,1}).next()+1);
    }
    
    public void addDoorsSparcely(Area area){
        addDoors(area, new Distribution(new double[]{3,3,1}).next()+1);
    }
    
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
                    map[y][x] = Tile.genDoor(area, true);
                }else failed++;
            }
        }
        if(failed>=40) exceptionStream.println("Failed to add doors to room: " + name + "\n" +
                    "Dimensions: " + width + ", " + height + "\n" +
                    "Orientation: " + orientation + "\n" +
                    "Number of doors remaining: " + numDoors);
    }
    
    
    public static void assertDimensions(int w, int h, int minW, int minH){
        if(w<minW || h<minH) throw new IllegalArgumentException("Dimensions " + w + ", " + h + " are to small.");
    }
    
}
