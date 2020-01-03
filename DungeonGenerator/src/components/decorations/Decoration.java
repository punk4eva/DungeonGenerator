
package components.decorations;

import animation.Animation;
import java.awt.Graphics2D;
import java.util.function.BiFunction;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Decoration{
    
    
    public final String name;
    public final String description;
    public final boolean aboveWater;
    public final BiFunction<Integer, Integer, Animation> animation;
    
    
    public Decoration(String na, String desc, boolean above, BiFunction<Integer, Integer, Animation> ani){
        name = na;
        description = desc;
        aboveWater = above;
        animation = ani;
    }
    
    
    public abstract void drawImage(Graphics2D g, int x, int y);

}
