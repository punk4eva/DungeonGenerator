
package gui.questions;

import biomes.Biome;
import biomes.Society;
import components.Area;
import components.LevelFeeling;
import components.mementoes.AreaInfo;
import generation.*;
import java.util.function.Function;

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
    private int[] dimensions = new int[]{80, 80};
    private LevelFeeling feeling = LevelFeeling.DEFAULT_FEELING;
    
    
    public void collect(Object obj){
        if(obj instanceof ClassSpecifier){
            if(MultiPlacer.class.isAssignableFrom(((ClassSpecifier) obj).type))
                multiPlacer = (area) -> (MultiPlacer)((ClassSpecifier) obj).apply(area);
            else if(PostCorridorPlacer.class.isAssignableFrom(((ClassSpecifier) obj).type))
                postCorridorPlacer = (area) -> (PostCorridorPlacer)((ClassSpecifier) obj).apply(area);
            else if(RoomPlacer.class.isAssignableFrom(((ClassSpecifier) obj).type))
                roomPlacer = (area) -> (RoomPlacer)((ClassSpecifier) obj).apply(area);
        }else if(obj instanceof Biome) biome = (Biome) obj;
        else if(obj instanceof Society) society = (Society) obj;
        else if(obj instanceof int[]) dimensions = (int[]) obj;
    }
    
    public Area createArea(){
        AreaInfo info = new AreaInfo(dimensions[0], dimensions[1], feeling, biome, society);
        Area area = new Area(info);
        
        if(multiPlacer != null) multiPlacer.apply(area).generate();
        else{
            roomPlacer.apply(area).generate();
            postCorridorPlacer.apply(area).generate();
        }
        return area;
    }

}
