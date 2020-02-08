/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import utils.test.PerformanceLog;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Random;
import static textureGeneration.Texture.rgbPixelEquals;
import generation.noise.PerlinNoiseGenerator;
import graph.Graph;
import utils.test.SpeedTester;

/**
 *
 * @author Adam Whittaker
 */
public final class Utils{

    
    private Utils(){}
    
    
    public static final Random R = new Random();
    public transient static PerformanceLog PERFORMANCE_LOG;
    public static SpeedTester SPEED_TESTER = new SpeedTester();
    static{
        try{
            PERFORMANCE_LOG = new PerformanceLog();
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
    
    public static int[][] copy2DArray(int[][] ary, int upto){
        int[][] clone = new int[Math.min(upto, ary.length)][];
        for(int n=0;n<clone.length;n++){
            clone[n] = new int[ary[n].length];
            System.arraycopy(ary[n], 0, clone[n], 0, ary[n].length);
        }
        return clone;
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
        
    }
    
}
