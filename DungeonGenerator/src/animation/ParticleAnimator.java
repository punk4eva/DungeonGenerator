
package animation;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author Adam Whittaker
 * Keeps track of all particles and particle generators.
 */
public class ParticleAnimator extends Animation{
    
    
    /**
     * particles: All the currently active particles.
     * generators: All the currently active particle generators.
     */
    private final LinkedList<Particle> particles = new LinkedList<>();
    private final LinkedList<ParticleGenerator> generators = new LinkedList<>();

    
    @Override
    public void animate(Graphics2D g, int focusX, int focusY, int frames){
        generators.stream().filter(gen -> gen.tickUp(frames))
                .map(gen -> gen.getParticle()).collect(Collectors.toCollection(() -> particles));
        particles.stream().forEach(p -> {
            p.update(frames);
            p.draw(g, focusX, focusY, frames);
        });
        particles.removeIf(p -> p.expired);
        generators.removeIf(gen -> gen.done);
    }
    
    /**
     * Registers a new particle generator.
     * @param g
     */
    public void addGenerator(ParticleGenerator g){
        generators.add(g);
    }
     
    /**
     * De-registers a particle generator.
     * @param g
     */
    public void removeGenerator(ParticleGenerator g){
        generators.remove(g);
    }
    
    /**
     * Returns the number of currently active particles for debugging purposes.
     * @return
     */
    public int getParticleNum(){
        return particles.size();
    }

}
