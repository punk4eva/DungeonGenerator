/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Random;
import static filterGeneration.Filter.rgbPixelEquals;
import generation.noise.PerlinNoiseGenerator;
import graph.Graph;

/**
 *
 * @author Adam Whittaker
 */
public final class Utils{

    
    private Utils(){}
    
    
    public static final Random R = new Random();
    public transient static PerformanceLog performanceLog;
    static{
        try{
            performanceLog = new PerformanceLog();
        }catch(FileNotFoundException e){
            System.err.println("PrintStream failed.");
        }
    }
    
    
    public static @interface Unfinished{
        String value() default "";
    }
    
    public static @interface ThreadUsed{
        String value() default "";
    }
    
    public static @interface Tested{
        String value() default "";
    }
    
    
    public static void printArray(int[] ary){
        System.out.print("[");
        for(int x : ary){
            System.out.print(x + " ");
        }
        System.out.println("]");
    }
    
    public static void print2DArray(int[][] ary){
        for(int[] row : ary){
            printArray(row);
        }
    }
    
    public static <T> boolean mapContainsArray(List<SimpleEntry<int[], T>> map, int[] ary){
        return map.stream().anyMatch((e) -> (rgbPixelEquals(e.getKey(), ary)));
    }
    
    public static <T> T getValueFromMap(List<SimpleEntry<int[], T>> map, int[] ary){
        for(SimpleEntry<int[], T> e : map){
            if(rgbPixelEquals(e.getKey(), ary)) return e.getValue();
        }
        return null;
    }
    
    
    public static void main(String... args) throws IOException{
        System.out.println("Running...");
        
        //Graph graph = new Graph(80, 80);
        /*Area area = new Area(80, 80, LevelFeeling.DEFAULT_FEELING);
        
        LinkedList<Room> list = new LinkedList<>();
        for(int n=0;n<20;n++) list.add(getRandomRoom());
        new RoomPlacer(area, list).generate();*/
        
        double map[][] = new double[528][528];
        new PerlinNoiseGenerator(528, 528,  60, 8,  0.5, 0.5).apply(map);
        Graph.makeWrappingPNG(map, "saves/map.png");
        
    }
    
}
