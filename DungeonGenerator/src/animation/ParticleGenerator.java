
package animation;

import java.util.function.Supplier;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class ParticleGenerator{

    
    protected int intensity;
    protected int currentTick = 0;
    private final Supplier<Particle> supplier;
    private final Supplier<Integer> xGenerator, yGenerator, delayGenerator;
    
    
    public ParticleGenerator(int i, Supplier<Particle> sup, int x, int y, int w, int h){
        this(sup, getUniformSupplier(x, w), getUniformSupplier(y, h), () -> i);
    }
    
    public ParticleGenerator(Supplier<Particle> sup, Supplier<Integer> x, Supplier<Integer> y, Supplier<Integer> delay){
        intensity = delay.get();
        supplier = sup;
        xGenerator = x;
        yGenerator = y;
        delayGenerator = delay;
    }
    
    
    public static Supplier<Integer> getUniformSupplier(int coord, int dimension){
        return () -> coord + R.nextInt(dimension+1);
    }
    
    
    public boolean tickUp(int frames){
        currentTick += frames;
        if(currentTick>=intensity){
            currentTick = 0;
            intensity = delayGenerator.get();
            return true;
        }else return false;
    }
    
    public Particle getParticle(){
        Particle p = supplier.get();
        p.x = xGenerator.get();
        p.y = yGenerator.get();
        return p;
    }
    
}
