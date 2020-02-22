
package gui.questions;

import components.Area;
import gui.core.DungeonViewer;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import static gui.core.Window.VIEWER;
import gui.pages.SelectionScreen;
import gui.tools.DoubleBox;
import gui.tools.IntegerBox;
import gui.tools.Toggle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import static utils.Utils.spaceCamelCase;

/**
 *
 * @author Adam Whittaker
 * @param <T>
 */
public class ClassSpecifier<T extends Object> extends Specifier implements Function<Area, T>{
    
    
    private final Constructor<T> construct;
    public final Class<T> type;
    private final String algorithmName;
    
    
    public ClassSpecifier(DungeonViewer v, Constructor<T> con, Class<T> typ, String algName, String ti, int width, int height){
        super(v, ti, width, height);
        construct = con;
        type = typ;
        algorithmName = algName;
        
        int yInc = MENU_HEIGHT + PADDING, _y = y;
        
        for(Parameter param : construct.getParameters()){
            if(!param.getType().equals(Area.class)){
                addToMap(param, _y);
                _y += yInc;
            }
        }
    }
    
    public ClassSpecifier(Constructor<T> con, Class<T> typ, String algName, String ti){
        this(VIEWER, con, typ, algName, ti, WIDTH/3, HEIGHT/3);
    }

    
    @Override
    public final T apply(Area area){
        List<Object> params = boxes.entrySet().stream()
                .map(entry -> entry.getValue().getValue())
                .collect(Collectors.toList());
        params.add(0, area);
        try{
            return construct.newInstance();
        }catch(InstantiationException e){
            System.out.println("Invalid parameters for " + algorithmName +": " + params);
            throw new IllegalStateException(e);
        }catch(IllegalAccessException | InvocationTargetException e){
            throw new IllegalStateException(e);
        }
    }
    
    @Override
    public String toString(){
        return algorithmName;
    }
    
    protected int[] getBounds(String name){
        switch(name){
            case "windyness":
            case "startChance": return new int[]{0,1};
            case "starvationLimit": 
            case "overpopulationLimit": 
            case "birthMinimum":
            case "birthMaximum": return new int[]{-1,9};
            case "iterationNumber":
            case "warMongering":
            case "technologyLevel":
            case "ruinationLevel": return new int[]{0,100};
        }
        throw new IllegalStateException("Unrecognised parameter: " + name);
    }
    
    private void addToMap(Parameter param, int y){
        if(param.getType().equals(boolean.class)){
            boxes.put(spaceCamelCase(param.getName()), 
                    new Toggle(BOX_X, y, MENU_HEIGHT, MENU_HEIGHT));
        }else if(param.getType().equals(int.class)){
            String name = param.getName();
            int[] bounds = getBounds(name);
            boxes.put(spaceCamelCase(name), new IntegerBox(BOX_X, y, 
                    BOX_WIDTH, MENU_HEIGHT, bounds[0], bounds[1]));
        }else if(param.getType().equals(double.class)){
            String name = param.getName();
            int[] bounds = getBounds(name);
            boxes.put(spaceCamelCase(name), new DoubleBox(BOX_X, y, 
                    BOX_WIDTH, MENU_HEIGHT, bounds[0], bounds[1]));
        }else throw new IllegalStateException("Unrecognized parameter: " + 
                param.getName() + " of type " + param.getType());
    }

    
    @Override
    public void process(SelectionScreen sc){
        sc.getInputCollector().collect(this);
    }

}
