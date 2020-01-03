
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.function.Function;

/**
 *
 * @author Adam Whittaker
 */
public class TrailGenerator{

    
    private final LinkedList<TrailSpeck> trail = new LinkedList<>();
    private final Function<Particle, TrailSpeck> generator;
    private final double fadeSpeed;
    private final int intensity;
    private int currentTick = 0;
    
    
    public TrailGenerator(int i, double f, Function<Particle, TrailSpeck> gen){
        fadeSpeed = f;
        intensity = i;
        generator = gen;
    }
    
    
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
    
    
    public abstract static class TrailSpeck{
        
        protected final int x, y, width, height;
        protected int r, g, b, alpha;
        private double alphaRemainder = 0;
        
        
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
        
        public TrailSpeck(Particle p){
            this(p.x, p.y, p.width, p.height, p.r, p.g, p.b, p.alpha);
        }

        
        public void draw(Graphics2D gr, int focusX, int focusY, double fadeSpeed, double frames){
            gr.setColor(new Color(r,g,b,alpha));
            gr.fillRect(x+focusX, y+focusY, width, height);
            
            alphaRemainder += fadeSpeed*frames;
            if(alphaRemainder>=1){
                alpha -= alphaRemainder;
                alphaRemainder %= 1.0;
            }
            update(frames);
        }
        
        public abstract void update(double frames);
        
    }
    
}
