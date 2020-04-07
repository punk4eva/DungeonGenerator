
package gui.questions;

import components.Area;
import components.rooms.Room;
import generation.MultiPlacer;
import static generation.PostCorridorPlacer.CORRIDOR_MENU;
import generation.RoomPlacer;
import gui.pages.SelectionScreen;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Specifies the parameters of the given room placer.
 * @author Adam Whittaker
 * @param <T> The type of the room placer.
 */
public class RoomPlacerSpecifier<T extends RoomPlacer> extends ClassSpecifier<T>{
    
    
    /**
     * The room placer's type.
     */
    public final Class<T> type;

    
    /**
     * Creates an instance.
     * @param con The constructor.
     * @param algName The name of the algorithm.
     * @param ti The title.
     * @param typ The class type of the algorithm.
     */
    public RoomPlacerSpecifier(Constructor<T> con, 
            Class<T> typ, String algName, String ti){
        super(con, algName, ti);
        type = typ;
    }
    
    
    /**
     * Creates an instance of the room placer using the given parameters.
     * @param area The Area.
     * @param rooms The rooms to place.
     * @return A room placement algorithm.
     */
    public final RoomPlacer apply(Area area, List<Room> rooms){
        List<Object> params = boxes.entrySet().stream()
                .map(entry -> entry.getValue().getValue())
                .collect(Collectors.toList());
        params.add(0, area);
        params.add(1, rooms);
        return construct(params);
    }
    
    @Override
    public void process(SelectionScreen sc){
        if(!MultiPlacer.class.isAssignableFrom(type)) 
            sc.addQuestionBox(CORRIDOR_MENU);
        sc.getInputCollector().collect(this);
    }

}
