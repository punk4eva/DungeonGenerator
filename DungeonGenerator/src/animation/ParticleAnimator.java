
package animation;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 *
 * @author Adam Whittaker
 */
public class ParticleAnimator extends Animation{
    
    
    private final LinkedList<Particle> particles = new LinkedList<>();
    private final LinkedList<ParticleGenerator> generators = new LinkedList<>();

    
    public ParticleAnimator(){
        super(0, 0);
    }

    
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
    
    public void addGenerator(ParticleGenerator g){
        generators.add(g);
    }
    
    public void removeGenerator(ParticleGenerator g){
        generators.remove(g);
    }

}
