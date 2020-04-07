
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
import java.util.stream.Collectors;
import static utils.Utils.spaceCamelCase;

/**
 * A question that lets the user choose all the attributes of a class based on a
 * given constructor.
 * @author Adam Whittaker
 * @param <T> The type of the object that will be constructed.
 */
public class ClassSpecifier<T extends Object> extends Specifier{
    
    
    /**
     * construct: The constructor to ask the user about.
     * algorithmName: The user friendly name of the class to display.
     */
    private final Constructor<T> construct;
    private final String algorithmName;
    
    
    /**
     * Creates an instance of the specifier.
     * @param v The viewer.
     * @param con The constructor.
     * @param algName The name of the algorithm.
     * @param ti The title.
     * @param width The width of the menu.
     * @param height The height of the menu.
     */
    public ClassSpecifier(DungeonViewer v, Constructor<T> con, String algName, String ti, int width, int height){
        super(v, ti, width, height);
        construct = con;
        algorithmName = algName;
        //Creates questions for all the parameters of the constructor.
        int yInc = MENU_HEIGHT + PADDING, _y = y;
        for(Parameter param : construct.getParameters()){
            if(!Area.class.isAssignableFrom(param.getType()) 
                    && !List.class.isAssignableFrom(param.getType())){
                addQuestionToMap(param, _y);
                _y += yInc;
            }
        }
    }
    
    /**
     * Creates an instance with the default width and height (Assumes the 
     * dungeon viewer exists).
     * @param con The constructor.
     * @param algName The name of the algorithm.
     * @param ti The title.
     */
    public ClassSpecifier(Constructor<T> con, String algName, String ti){
        this(VIEWER, con, algName, ti, WIDTH/3, HEIGHT/3);
    }

    
    /**
     * Constructs an instance of the given type using the list of parameters.
     * @param params The list of user specified parameters.
     * @return A new instance of the object.
     */
    protected T construct(List<Object> params){
        try{
            return construct.newInstance(params.toArray());
            //Error handling
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
    
    /**
     * Translates a given parameter name into the boundary values that it can
     * take.
     * @param name The name of the parameter.
     * @return An integer array in the form {min, max}.
     */
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
            case "maxCorridorLength": return new int[]{0,10};
            case "decayLimit": return new int[]{0,9};
        }
        throw new IllegalStateException("Unrecognised parameter: " + name);
    }
    
    /**
     * Creates and adds a question to the map of all questions.
     * @param param The parameter.
     * @param y The y coordinate at which the question should be placed.
     */
    private void addQuestionToMap(Parameter param, int y){
        //Checks that the parameter was compiled with its name.
        if(!param.isNamePresent()) throw new IllegalStateException("The project"
                + " has been erroneously compiled! Please include the "
                + "\"-parameter\" when compiling.");
        
        //Classifies the parameter based on its type and generates the 
        //appropriate question.
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
        //Collects the paramtere and constructs a new instance with them, adding
        //it to the input collector.
        List<Object> params = boxes.entrySet().stream()
                .map(entry -> entry.getValue().getValue())
                .collect(Collectors.toList());
        sc.getInputCollector().collect(construct(params));
    }

}
