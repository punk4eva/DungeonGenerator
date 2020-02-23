
package gui.questions;

import components.Area;
import generation.PostCorridorPlacer;
import gui.pages.SelectionScreen;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Adam Whittaker
 * @param <T>
 */
public class CorridorSpecifier<T extends PostCorridorPlacer> extends ClassSpecifier<T>{

    
    public CorridorSpecifier(Constructor<T> con, 
            String algName, String ti){
        super(con, algName, ti);
    }
    
    
    public final PostCorridorPlacer apply(Area area){
        List<Object> params = boxes.entrySet().stream()
                .map(entry -> entry.getValue().getValue())
                .collect(Collectors.toList());
        params.add(0, area);
        return construct(params);
    }
    
    @Override
    public void process(SelectionScreen sc){
        sc.getInputCollector().collect(this);
    }

}
