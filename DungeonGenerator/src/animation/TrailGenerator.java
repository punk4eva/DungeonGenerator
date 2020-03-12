
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.function.Function;

/**
 *
 * @author Adam Whittaker
 * Generates a trail effect for a particle.
 */
public class TrailGenerator{

    
    /**
     * trail: the list of all current trail elements.
     * generator: The function to convert a particle to a trail effect.
     * fadeSpeed: the speed at which the trail tapers off.
     * intensity: the delay between the generation of two trail specks.
     * currentTick: internal counter tracking when to generate a new trail 
     * speck.
     */
    private final LinkedList<TrailSpeck> trail = new LinkedList<>();
    private final Function<Particle, TrailSpeck> generator;
    private final double fadeSpeed;
    private final int intensity;
    private int currentTick = 0;
    
    
    /**
     * Creates a new instance by initializing the fields.
     * @param i
     * @param f
     * @param gen
     */
    public TrailGenerator(int i, double f, Function<Particle, TrailSpeck> gen){
        fadeSpeed = f;
        intensity = i;
        generator = gen;
    }
    
    
    /**
     * Draws the trail.
     * @param gr
     * @param focusX
     * @param focusY
     * @param frames
     * @param p The particle owner of this trail.
     */
    public void draw(Graphics2D gr, int focusX, int focusY, int frames, Particle p){
        currentTick += frames;
        if(currentTick>=intensity){
            trail.add(generator.apply(p));
            currentTick = 0;
        }
        trail.stream().forEach(sp -> {
            sp.draw(gr, focusX, focusY, fadeSpeed, frames);
        });
        trail.removeIf(sp -> sp.alpha<=0);
    }
    
    
    /**
     * Models a particulate of the trail.
     */
    public abstract static class TrailSpeck{
        
        
        /**
         * x, y, width, height: The dimensions of the speck.
         * r,g,b,alpha: The color of the speck.
         * alphaRemainder: The remainder of alpha calculations so that the fade
         * speed can have double precision.
         */
        protected final int x, y, width, height;
        protected int r, g, b, alpha;
        private double alphaRemainder = 0;
        
        
        /**
         * Creates an instance by initializing the fields.
         * @param _x
         * @param _y
         * @param w
         * @param h
         * @param R
         * @param G
         * @param B
         * @param alp
         */
        public TrailSpeck(int _x, int _y, int w, int h, int R, int G, int B, int alp){
            alpha = alp;
            x = _x;
            y = _y;
            width = w;
            height = h;
            r = R;
            g = G;
            b = B;
        }
        
        /**
         * Creates a default instance from a Particle.
         * @param p
         */
        public TrailSpeck(Particle p){
            this(p.x, p.y, p.width, p.height, p.r, p.g, p.b, p.alpha);
        }

        
        /**
         * Draws this speck.
         * @param gr
         * @param focusX
         * @param focusY
         * @param fadeSpeed The speed of the fading of the alpha.
         * @param frames
         */
        public void draw(Graphics2D gr, int focusX, int focusY, double fadeSpeed, double frames){
            gr.setColor(new Color(r,g,b,alpha));
            gr.fillRect(x+focusX, y+focusY, width, height);
            
            //Updates the alpha value and stores the overflow in the remainder
            //variable.
            alphaRemainder += fadeSpeed*frames;
            if(alphaRemainder>=1){
                alpha -= alphaRemainder;
                alphaRemainder %= 1.0;
            }
            update(frames);
        }
        
        /**
         * Updates this trail speck.
         * @param frames
         */
        public abstract void update(double frames);
        
    }
    
}
