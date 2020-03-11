/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph;

import components.Area;
import components.tiles.PassageTile;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BiFunction;
import javax.imageio.ImageIO;
import utils.Utils.Unfinished;

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
    
    
    @Unfinished("Debug")
    public void makePNG(String filepath, BiFunction<Point, int[], int[]> colorGetter) throws IOException{
        BufferedImage img = new BufferedImage(map[0].length, map.length, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = img.getRaster();
        int[] pixel = new int[3];
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[y].length;x++){
                pixel = colorGetter.apply(map[y][x], pixel);
                raster.setPixel(x, y, pixel);
            }
        }
        ImageIO.write(img, "png", new File(filepath));
    }
    
    @Unfinished("Debug")
    public static void makePNG(double[][] map, String filepath) throws IOException{
        BufferedImage img = new BufferedImage(map[0].length, map.length, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = img.getRaster();
        int[] pixel = new int[3];
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[y].length;x++){
                pixel[0] = (int)map[y][x];
                pixel[1] = (int)map[y][x];
                pixel[2] = (int)map[y][x];
                raster.setPixel(x, y, pixel);
            }
        }
        ImageIO.write(img, "png", new File(filepath));
    }
    
}