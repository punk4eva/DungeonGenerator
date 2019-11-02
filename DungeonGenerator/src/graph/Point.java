
package graph;

/**
 *
 * @author Adam Whittaker
 */
public class Point{
    
    /**
     * This class represents the possible cardinal directions (N,E,S,W) and 
     * their respective graph transformations.
     */
    public static enum Direction{
        
        NORTH(0,-1), EAST(1,0), SOUTH(0,1), WEST(-1,0);
        
        /**
         * The coordinate shift for a particular Direction.
         */
        public final int x, y;
        
        private Direction(int _x, int _y){
            x = _x;
            y = _y;
        }
        
    }
    
    /**
     * The "type" information for a Tile used for path-finding. I.e: Whether it
     * is traversable, a door or a wall.
     */
    public static enum Type{
        WALL, FLOOR, DOOR, NULL;
    }
    
    /**
     * x, y: The coordinates of the Point.
     * checked: Whether this point has been checked by a flood-fill algorithm.
     *      This value is null if the tile does not need to be checked, i.e: it
     *      is a wall or NULL.
     * type: The type of the Point.
     * isCorridor: Whether this Point is part of a corridor.
     * roomNum: The label of the room that this tile is part of. The value is -1
     *      if the tile is not part of a room.
     * currentCost: The cost of navigating to this tile. (Used by path-finding
     *      algorithms).
     * cameFrom: The Point before this tile in a path-finding chain.
     */
    public int x, y;
    public Boolean checked; //refreshed
    public Type type = Type.NULL; //refreshed
    public boolean isCorridor = false;
    public int roomNum = -1;
    public double currentCost = Double.MAX_VALUE; //refreshed
    public Point cameFrom = null; //refreshed
    
    
    /**
     * Constructs a new Point.
     * @param _x The x coordinate.
     * @param _y The y coordinate.
     */
    public Point(int _x, int _y){
        x = _x;
        y = _y;
    }
    
    
    /**
     * Refreshes the values of this Point for after its corresponding Tile has 
     * been changed.
     * @param t The Point's new type.
     */
    public void refresh(Type t){
        type = t;
        if(t.equals(Type.NULL) || t.equals(Type.WALL)) checked = null;
        else checked = false;
        currentCost = Double.MAX_VALUE;
        cameFrom = null;
    }
    
    /**
     * Resets The pathfinding aspects of this Point.
     */
    public void resetFloodFill(){
        if(checked!=null) checked = false;
        cameFrom = null;
        currentCost = Double.MAX_VALUE;
    }

}
