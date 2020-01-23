
package components.decorations;

import animation.Animation;
import java.awt.Graphics2D;
import java.util.function.BiFunction;

/**
 *
 * @author Adam Whittaker
 * This class represents an aesthetic/functional addition to a tile.
 */
public abstract class Decoration{
    
    
    /**
     * name: The name of the decoration.
     * description: The description of the decoration.
     * aboveWater: Whether to render the decoration above water.
     * animation: A supplier for an animation based on the coordinates where the
     * decoration is.
     */
    public final String name;
    public final String description;
    public final boolean aboveWater;
    public final BiFunction<Integer, Integer, Animation> animation;
    
    
    /**
     * Creates a new instance.
     * @param na The name.
     * @param desc The description.
     * @param above whether the decoration is above water.
     * @param ani The animation of the decoration, null if there is none.
     */
    public Decoration(String na, String desc, boolean above, BiFunction<Integer, Integer, Animation> ani){
        name = na;
        description = desc;
        aboveWater = above;
        animation = ani;
    }
    
    
    /**
     * Draws the decoration onto the given graphics.
     * @param g
     * @param x
     * @param y
     */
    public abstract void drawImage(Graphics2D g, int x, int y);

}
