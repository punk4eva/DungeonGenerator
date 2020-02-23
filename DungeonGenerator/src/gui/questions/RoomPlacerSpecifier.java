
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
 *
 * @author Adam Whittaker
 * @param <T>
 */
public class RoomPlacerSpecifier<T extends RoomPlacer> extends ClassSpecifier<T>{
    
    
    public final Class<T> type;

    
    public RoomPlacerSpecifier(Constructor<T> con, 
            Class<T> typ, String algName, String ti){
        super(con, algName, ti);
        type = typ;
    }
    
    
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
