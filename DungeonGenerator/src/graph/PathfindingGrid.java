/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph;

import components.Area;
import components.tiles.PassageTile;
import java.util.Collections;
import java.util.LinkedList;

/**
 * A representation of a dungeon floor optimized for path-finding. This class
 * contains all the attributes of the floor needed for generation but not for
 * deserialization. This class is not Serializable and is transient to save
 * reduce file sizes of serialized dungeons.
 * @author Adam Whittaker
 */
public class PathfindingGrid{

    
    /**
     * map: The Point matrix representing the dungeon space.
     * doors: A list of all doors for path-finding purposes.
     */
    public final Point[][] map;
    public final LinkedList<Point> doors = new LinkedList<>();
    
    
    /**
     * Initializes an empty Graph object full of Points.
     * @param w The width of the floor.
     * @param h The height of the floor.
     */
    public PathfindingGrid(int w, int h){
        map = new Point[h][w];
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                map[y][x] = new Point(x, y);
            }
        }
    }
    
    
    /**
     * Ensures this Graph is ready for use by resetting all flood fill trails.
     */
    public void reset(){
        for(Point[] row : map)
            for(int x = 0; x<map[0].length; x++) row[x].resetFloodFill();
    }
    
    /**
     * Resets all room numbers of all points to the default -1.
     */
    public void flushRoomNumbers(){
        for(Point[] row : map){
            for(Point p : row){
                p.roomNum = -1;
            }
        }
    }
    
    /**
     * Recreates the "doors" list in case new doors have been added. This is the
     * method to call for first-time initialization as well.
     * @param area The Area owning this Graph.
     */
    public void recalculateDoors(Area area){
        doors.clear();
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                if(area.map[y][x]!=null && area.map[y][x] instanceof PassageTile
                        && ((PassageTile)area.map[y][x]).pathfind) doors.add(map[y][x]);
            }
        }
        Collections.shuffle(doors);
    }
    
}
