/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import components.RoomDesc;
import generation.MidpointDisplacer;
import generation.PerlinNoiseGenerator;
import generation.RoomPlacer;
import graph.Graph;
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
    
    public static RoomDesc getRandomRoom(){
        return new RoomDesc("Random room", 5+R.nextInt(12), 5+R.nextInt(12));
    }
    
    public static void main(String... args) throws IOException{
        System.out.println("Running...");
        
        
        Graph graph = new Graph(80, 80);
        
        LinkedList<RoomDesc> list = new LinkedList<>();
        for(int n=0;n<40;n++) list.add(getRandomRoom());
        new RoomPlacer(graph, list).generate();
        
        //new PerlinNoiseGenerator(graph.map, 50, 4, 0.6, 0.6).apply();
        //new MidpointDisplacer(125, 80, 0.7, 255, false).apply(graph.map);
        
        graph.makePNG("saves/map.png", new DungeonColorer());
    }
    
}
