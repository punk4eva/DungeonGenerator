
package gui.questions;

import components.Area;
import generation.PostCorridorPlacer;
import gui.pages.SelectionScreen;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The user specifies the parameters of the corridor algorithm.
 * @author Adam Whittaker
 * @param <T>
 */
public class CorridorSpecifier<T extends PostCorridorPlacer> extends ClassSpecifier<T>{

    
    /**
     * Creates a new instance by forwarding parameters.
     * @param con
     * @param algName
     * @param ti
     */
    public CorridorSpecifier(Constructor<T> con, 
            String algName, String ti){
        super(con, algName, ti);
    }
    
    
    /**
     * Creates a corridor algorithm using the given parameters.
     * @param area The area.
     * @return
     */
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
