
package gui.questions;

import biomes.Biome;
import biomes.Society;
import components.Area;
import components.mementoes.AreaInfo;
import generation.*;
import static generation.rooms.RoomSelector.getDefaultRoomList;
import java.util.function.Function;
import static utils.Utils.SPEED_TESTER;

/**
 * Collects all input given by the user so far about the dungeon parameters.
 * @author Adam Whittaker
 */
public class InputCollector{
    
    
    /**
     * postCorridorPlacer: The corridor-only generation algorithm, if there is 
     * one.
     * multiPlacer: The area-generation algorithm, if there is one.
     * roomPlacer: The room placement algorithm, if there is one.
     * biome: The biome.
     * society: The society.
     * dimensions: The dimensions of the Area.
     */
    private Function<Area, PostCorridorPlacer> postCorridorPlacer;
    private Function<Area, MultiPlacer> multiPlacer;
    private Function<Area, RoomPlacer> roomPlacer;
    private Biome biome = Biome.DEFAULT_BIOME;
    private Society society = Society.DEFAULT_SOCIETY;
    private int[] dimensions = DimensionSpecifier.DEFAULT_DIMENSIONS;
    
    
    /**
     * Passes an object into the collector.
     * @param obj
     */
    public void collect(Object obj){
        //Finds the type of object and stores it in the appropriate place.
        if(obj instanceof CorridorSpecifier){
            postCorridorPlacer = (area) -> ((CorridorSpecifier) obj).apply(area);
        }else if(obj instanceof RoomPlacerSpecifier){
            
            if(MultiPlacer.class.isAssignableFrom(((RoomPlacerSpecifier) obj).type)){
                multiPlacer = (area) -> (MultiPlacer)((RoomPlacerSpecifier) obj)
                        .apply(area, getDefaultRoomList(dimensions[2], area));
            }else{
                roomPlacer = (area) -> ((RoomPlacerSpecifier) obj)
                        .apply(area, getDefaultRoomList(dimensions[2], area));
            }
            
        }else if(obj instanceof Biome) biome = (Biome) obj;
        else if(obj instanceof Society) society = (Society) obj;
        else if(obj instanceof int[]) dimensions = (int[]) obj;
    }
    
    /**
     * Creates the Area using the parameters.
     * @return The Area.
     */
    public Area createArea(){
        SPEED_TESTER.start();
        //Creates the area save information and the blank area.
        AreaInfo info = new AreaInfo(dimensions[0], dimensions[1], biome, society);
        Area area = new Area(info);
        //Populates the area with rooms and corridors using the algorithms.
        if(multiPlacer != null){
            multiPlacer.apply(area).generate();
            area.refreshGraph();
            SPEED_TESTER.test("Rooms placed");
        }else{
            roomPlacer.apply(area).generate();
            area.refreshGraph();
            SPEED_TESTER.test("Rooms placed");
            postCorridorPlacer.apply(area).generate();
            SPEED_TESTER.test("Corridors built");
        }
        //Adds decorations to the Area.
        area.decorate();
        SPEED_TESTER.test("Decorations added");
        area.initializeImages();
        SPEED_TESTER.test("Images initialized");

        SPEED_TESTER.report();
        
        return area;
    }

}
