
package animation;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * A simple hub class which encapsulates the list of all current Animations and
 * protects them from thread concurrency issues, as well as animating them from
 * a central point.
 * @author Adam Whittaker
 */
public class Animator{
    
    
    /**
     * animations: All non-particle animations currently being rendered.
     * particles: Handles animation of particles.
     * animatingParticles: Whether particles are being animated.
     */
    private final LinkedList<Animation> animations = new LinkedList<>();
    private final ParticleAnimator particles = new ParticleAnimator();
    private volatile boolean animatingParticles = true;
    {
        animations.add(particles);
        setAnimatingParticles(false);
    }

    
    /**
     * Animates all currently active animations.
     * @param g The Graphics
     * @param focusX
     * @param focusY
     * @param frames Frames elapsed since last render-tick.
     */
    public void animate(Graphics2D g, int focusX, int focusY, int frames){
        synchronized(animations){
            animations.stream().forEach(a -> {
                a.animate(g, focusX, focusY, frames);
            });
            animations.removeIf(a -> a.done);
        }
    }
    
    
    /**
     * Adds an animation to the list of animations to be rendered.
     * @param a
     */
    public void add(Animation a){
        synchronized(animations){
            if(a instanceof ParticleGenerator) particles.addGenerator((ParticleGenerator)a);
            else animations.add(a);
        }
    }
    
    /**
     * Removes an animation from the list of animations to be rendered.
     * @param a
     */
    public void remove(Animation a){
        synchronized(animations){
            if(a instanceof ParticleGenerator) particles.removeGenerator((ParticleGenerator)a);
            else animations.remove(a);
        }
    }
    
    /**
     * Controls whether Particles are currently being animated.
     * @param val
     */
    public void setAnimatingParticles(boolean val){
        if(animatingParticles){
            if(!val){
                animatingParticles = false;
                animations.remove(particles);
            }
        }else{
            if(val){
                animatingParticles = true;
                animations.add(particles);
            }
        }
    }
    
    /**
     * Retrieves the number of non-particle animations currently active for 
     * debugging purposes.
     * @return
     */
    public int getAnimationNum(){
        return animations.size();
    }
    
    /**
     * Retrieves the number of particles currently active for debugging purposes.
     * @return
     */
    public int getParticleNum(){
        return particles.getParticleNum();
    }

}
