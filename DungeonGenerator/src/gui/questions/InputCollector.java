
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
 *
 * @author Adam Whittaker
 */
public class InputCollector{
    
    
    private Function<Area, PostCorridorPlacer> postCorridorPlacer;
    private Function<Area, MultiPlacer> multiPlacer;
    private Function<Area, RoomPlacer> roomPlacer;
    private Biome biome = Biome.DEFAULT_BIOME;
    private Society society = Society.DEFAULT_SOCIETY;
    private int[] dimensions = DimensionSpecifier.DEFAULT_DIMENSIONS;
    
    
    public void collect(Object obj){
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
    
    public Area createArea(){
        SPEED_TESTER.start();
        
        AreaInfo info = new AreaInfo(dimensions[0], dimensions[1], biome, society);
        Area area = new Area(info);
        
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
        
        area.decorate();
        SPEED_TESTER.test("Decorations added");
        area.initializeImages();
        SPEED_TESTER.test("Images initialized");

        SPEED_TESTER.report();
        
        return area;
    }

}
