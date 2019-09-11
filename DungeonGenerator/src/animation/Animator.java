
package animation;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 *
 * @author Adam Whittaker
 * 
 * A simple hub class which encapsulates the list of all current Animations and
 * protects them from thread concurrency issues, as well as animating them from
 * a central point.
 */
public class Animator{
    
    private final LinkedList<Animation> animations = new LinkedList<>();

    public void animate(Graphics2D g, int focusX, int focusY, int frames){
        synchronized(animations){
            animations.stream().forEach(a -> {
                a.animate(g, focusX, focusY, frames);
            });
        }
    }
    
    public void add(Animation a){
        synchronized(animations){
            animations.add(a);
        }
    }
    
    public void remove(Animation a){
        synchronized(animations){
            animations.remove(a);
        }
    }

}
