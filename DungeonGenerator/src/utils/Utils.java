/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import components.Area;
import components.LevelFeeling;
import components.rooms.Room;
import components.rooms.PlainRoom;
import components.tiles.Floor;
import generation.noise.MidpointDisplacer;
import generation.noise.PerlinNoiseGenerator;
import generation.rooms.RoomPlacer;
import graph.Graph;
import graph.Point;
import graph.Point.Type;
import gui.DungeonViewer;
import java.awt.Canvas;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Adam Whittaker
 */
public final class Utils{

    private Utils(){}
    
    public static final Random R = new Random();
    
    public static @interface Unfinished{
        String value() default "";
    }
    
    public static @interface ThreadUsed{
        String value() default "";
    }
    
    public static Room getRandomRoom(){
        return new PlainRoom(5+R.nextInt(5)*2, 5+R.nextInt(5)*2);
    }
    
    public static void print2DArray(int[][] ary){
        for(int[] row : ary){
            System.out.print("[");
            for(int x = 0; x<ary[0].length; x++){
                System.out.print(row[x] + " ");
            }
            System.out.println("]");
        }
    }
    
    public static void main(String... args) throws IOException{
        System.out.println("Running...");
        
        //Graph graph = new Graph(80, 80);
        /*Area area = new Area(80, 80, LevelFeeling.DEFAULT_FEELING);
        
        LinkedList<Room> list = new LinkedList<>();
        for(int n=0;n<20;n++) list.add(getRandomRoom());
        new RoomPlacer(area, list).generate();*/
        
        //double[][] map = new double[500][500];
        //new PerlinNoiseGenerator(map[0].length, map.length, 100, 5, 0.75, 0.75).apply(map);
        //new MidpointDisplacer(125, 80, 0.7, 255, true, true).apply(map);
        
        //Graph.makePNG(map, "saves/map.png");
        
    }
    
}
