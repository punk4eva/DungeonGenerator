
package animation;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 * 
 * This class represents an animation, i.e: A task to be called each render-tick
 * until it is done.
 */
public abstract class Animation{

    /**
     * Lets the Animator know when to remove this Animation from the animating
     * queue. Set internally by the class.
     */
    protected boolean done = false;
    
    /**
     * Paints a frame of animation for this object. Can also be used for other
     * tasks as well, such as moving an object gradually.
     * @param g The Graphics to paint on.
     * @param focusX
     * @param focusY
     * @param frames The number of frames elapsed since the last render-tick.
     */
    public abstract void animate(Graphics2D g, int focusX, int focusY, int frames);
    
}
