
package gui.tools.assets;

import components.Area;
import gui.core.DungeonViewer;
import static gui.core.DungeonViewer.HEIGHT;
import static gui.core.DungeonViewer.WIDTH;
import gui.tools.DoubleBox;
import gui.tools.InputBox;
import gui.tools.IntegerBox;
import gui.tools.Toggle;
import gui.tools.UIPanel;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static utils.Utils.spaceCamelCase;

/**
 *
 * @author Adam Whittaker
 * @param <T>
 */
public class AlgorithmSpecifier<T extends Object> extends InputBox{
    
    
    protected final static int INPUT_NAME_X = WIDTH/4, BOX_X = 5*WIDTH/8,
            BOX_WIDTH = WIDTH/8;
    
    private final String title;
    private final String algorithmName;
    protected final HashMap<String, InputBox> boxes = new HashMap<>();
    private final Constructor<T> construct;
    
    
    public AlgorithmSpecifier(DungeonViewer v, Constructor<T> con, String algName, String ti, int width, int height){
        super((WIDTH-width)/2, (HEIGHT-height)/2, width, height);
        title = ti;
        algorithmName = algName;
        construct = con;
        
        int yInc = MENU_HEIGHT + PADDING, _y = y;
        
        for(Parameter param : construct.getParameters()){
            if(!param.getType().equals(Area.class)){
                addToMap(param, _y);
                _y += yInc;
            }
        }
        
        registerKeys(v);
    }
    
    
    @Override
    public void click(int mx, int my){
        boxes.entrySet().forEach((entry) -> {
            entry.getValue().click(mx, my);
        });
    }
    
    @Override
    public String toString(){
        return algorithmName;
    }
    
    @Override
    public void paint(Graphics2D g){
        paintTitle(g);
        boxes.entrySet().stream().forEach((entry) -> {
            entry.getValue().paint(g);
            paintText(g, entry.getKey(), INPUT_NAME_X, entry.getValue().getY(),
                    WIDTH/8, MENU_HEIGHT, UIPanel.BUTTON_TEXT_FONT, TEXT_COLOR);
        });
    }
    
    private void paintTitle(Graphics2D g){
        g.setFont(TITLE_FONT);
        g.setColor(TITLE_COLOR);
        FontMetrics f = g.getFontMetrics();
        g.drawString(title, x+(width - f.stringWidth(title))/2, 
                y-f.getHeight()-PADDING + f.getDescent());
    }
    
    public final void registerKeys(DungeonViewer v){
        boxes.entrySet().stream().map(entry -> entry.getValue())
                .filter(inp -> inp instanceof KeyListener).forEach(key -> {
                    v.addKeyListener((KeyListener)key);
                });
    }
    
    public final void deregisterKeys(DungeonViewer v){
        boxes.entrySet().stream().map(entry -> entry.getValue())
                .filter(inp -> inp instanceof KeyListener).forEach(key -> {
                    v.removeKeyListener((KeyListener)key);
                });
    }
    
    public final T get(Area area){
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
    
    protected int[] getBounds(String name){
        switch(name){
            case "windyness":
            case "startChance": return new int[]{0,1};
            case "starvationLimit": 
            case "overpopulationLimit": 
            case "birthMinimum":
            case "birthMaximum": return new int[]{-1,9};
            case "iterationNumber": return new int[]{0,100};
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
            boxes.put(spaceCamelCase(name), 
                    new IntegerBox(BOX_X, y, MENU_HEIGHT, MENU_HEIGHT, bounds[0], bounds[1]));
        }else if(param.getType().equals(double.class)){
            String name = param.getName();
            int[] bounds = getBounds(name);
            boxes.put(spaceCamelCase(name), 
                    new DoubleBox(BOX_X, y, MENU_HEIGHT, MENU_HEIGHT, bounds[0], bounds[1]));
        }else throw new IllegalStateException("Unrecognized parameter: " + 
                param.getName() + " of type " + param.getType());
    }
    
    @Override
    public Object getValue(){
        throw new IllegalStateException("Not to be callsed in algorithm specifier.");
    }

}
