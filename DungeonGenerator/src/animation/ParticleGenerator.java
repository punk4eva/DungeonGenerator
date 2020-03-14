
package animation;

import java.awt.Graphics2D;
import java.util.function.Supplier;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * Models the source of a stream of particles, such as a torch.
 */
public class ParticleGenerator extends Animation{

    
    /**
     * intensity: The delay between the creation of two particles.
     * currentTick: A counter for how long until the next particle gets created.
     * supplier: A function to generate new particles.
     * x, y, delayGenerator: Functions to generate the x,y coordinates of the 
     * new particle, as well as the delay until the next particle.
     */
    protected int intensity;
    protected int currentTick = 0;
    private final Supplier<Particle> supplier;
    private final Supplier<Integer> xGenerator, yGenerator, delayGenerator;
    
    
    /**
     * Creates a default particle generator with a fixed delay and uniform 
     * particle spread.
     * @param i the delay between particles.
     * @param sup the function to generate new particles.
     * @param x the top-left corner x of the generation rectangle.
     * @param y the top-left corner y of the generation rectangle.
     * @param w the width of the generation rectangle.
     * @param h the height of the generation rectangle.
     */
    public ParticleGenerator(int i, Supplier<Particle> sup, int x, int y, int w, int h){
        this(sup, x, y, w, h, () -> i);
    }
    
    /**
     * Creates a default particle generator with uniform particle spread.
     * @param sup the function to generate new particles.
     * @param x the top-left corner x of the generation rectangle.
     * @param y the top-left corner y of the generation rectangle.
     * @param w the width of the generation rectangle.
     * @param delay the function to generate the delay between two particles.
     * @param h the height of the generation rectangle.
     */
    public ParticleGenerator(Supplier<Particle> sup, int x, int y, int w, int h, Supplier<Integer> delay){
        this(sup, getUniformSupplier(x, w), getUniformSupplier(y, h), delay);
    }
    
    /**
     * Creates a Particle Generator by specifying each field.
     * @param sup
     * @param x
     * @param y
     * @param delay
     */
    public ParticleGenerator(Supplier<Particle> sup, Supplier<Integer> x, Supplier<Integer> y, Supplier<Integer> delay){
        intensity = delay.get();
        supplier = sup;
        xGenerator = x;
        yGenerator = y;
        delayGenerator = delay;
    }
    
    
    /**
     * Returns a function to generate integers uniformly within the generation
     * rectangle.
     * @param coord the x (y) coordinate of the top-left.
     * @param dimension the width (height) of the rectangle.
     * @return
     */
    public static Supplier<Integer> getUniformSupplier(int coord, int dimension){
        return () -> coord + R.nextInt(dimension+1);
    }
    
    
    /**
     * Increments the internal tick counter and decides whether to generate a 
     * new particle.
     * @param frames The frames elapsed since the last render-tick.
     * @return true if a particle is to be generated.
     */
    public boolean tickUp(int frames){
        currentTick += frames;
        if(currentTick>=intensity){
            currentTick = 0;
            intensity = delayGenerator.get();
            return true;
        }else return false;
    }
    
    /**
     * Generates a new particle and initializes its x, y coordinates from the
     * internal distributions.
     * @return
     */
    public Particle getParticle(){
        Particle p = supplier.get();
        p.x = xGenerator.get();
        p.y = yGenerator.get();
        return p;
    }

    
    @Override
    public void animate(Graphics2D g, int focusX, int focusY, int frames){}
    
}
