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
        
        private final int x, y;
        
        private Direction(int _x, int _y){
            x = _x;
            y = _y;
        }
        
    }
    
    public static enum Type{
        WALL, FLOOR, DOOR, NULL
    }
    
    public int x, y;
    public Boolean checked;
    public Type type = Type.NULL;
    public boolean isCorridor = false;
    public int roomNum = -1;
    public double value = 0;
    
    public Point(int _x, int _y){
        x = _x;
        y = _y;
    }

}
