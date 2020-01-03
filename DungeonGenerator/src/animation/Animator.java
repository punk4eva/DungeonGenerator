
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
    private final ParticleAnimator particles = new ParticleAnimator();
    private volatile boolean animatingParticles = true;
    {
        animations.add(particles);
    }

    
    public void animate(Graphics2D g, int focusX, int focusY, int frames){
        synchronized(animations){
            animations.stream().forEach(a -> {
                a.animate(g, focusX, focusY, frames);
            });
            animations.removeIf(a -> a.done);
        }
    }
    
    
    public void add(Animation a){
        synchronized(animations){
            if(a instanceof ParticleGenerator) particles.addGenerator((ParticleGenerator)a);
            else animations.add(a);
        }
    }
    
    public void remove(Animation a){
        synchronized(animations){
            if(a instanceof ParticleGenerator) particles.removeGenerator((ParticleGenerator)a);
            else animations.remove(a);
        }
    }
    
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

}
