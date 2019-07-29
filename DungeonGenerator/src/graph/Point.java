/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph;

/**
 *
 * @author Adam Whittaker
 */
public class Point{
    
    public static enum Direction{
        
        NORTH(0,-1), EAST(1,0), SOUTH(0,1), WEST(-1,0);
        
        public final int x, y;
        
        private Direction(int _x, int _y){
            x = _x;
            y = _y;
        }
        
    }
    
    public static enum Type{
        WALL, FLOOR, DOOR, NULL;
    }
    
    public int x, y;
    public Boolean checked; //refreshed
    public Type type = Type.NULL; //refreshed
    public boolean isCorridor = false;
    public int roomNum = -1;
    public double currentCost = Double.MAX_VALUE; //refreshed
    public Point cameFrom = null; //refreshed
    
    public Point(int _x, int _y){
        x = _x;
        y = _y;
    }
    
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
