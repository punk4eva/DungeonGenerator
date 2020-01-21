
package animation.assets;

import animation.Particle;
import animation.TrailGenerator.TrailSpeck;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Function;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * Models a particle of soot coming for a fire.
 */
public class FireParticle extends Particle{
    
    
    private final double decayFactor;

    
    /**
     * Creates a simple FireParticle by auto-initializing some fields.
     * @param w The width
     * @param h The height
     * @param decay A factor inversely proportional to the probability that a 
     * particle will expire in a given tick.
     */
    public FireParticle(int w, int h, double decay){
        super(w, h, 240, 50, 0);
        decayFactor = decay;
        vely = -(R.nextDouble()*0.1+0.05);
        velx = 0;
    }
    
    /**
     * Creates a FireParticle with a trail.
     * @param w The width
     * @param h The height
     * @param decay A factor inversely proportional to the probability that a 
     * particle will expire in a given tick.
     * @param intensity The intensity of the trail generator.
     * @param trailFade The fade speed of the trail.
     * @param tr The trail speck generator for the trail.
     */
    public FireParticle(int w, int h, double decay, int intensity, double trailFade, Function<Particle, TrailSpeck> tr){
        super(w, h, 240, 50, 0, intensity, trailFade, tr);
        decayFactor = decay;
        vely = -(R.nextDouble()*0.1+0.05)*1.2D;
        velx = 0;
    }

    
    @Override
    public void update(int frames){
        motor(frames);
        
        double c = R.nextDouble();
        if(c<0.05) x--;
        else if(c<0.1) x++;
        
        if(g<120) g+=5;
        if(alpha>5) alpha-=3;
        expired = R.nextDouble() < (1.0 - (double)alpha/255.0)*decayFactor;
    }

    @Override
    public void draw(Graphics2D gr, int focusX, int focusY, int frames){
        defaultDraw(gr, focusX, focusY, frames);
    }
    
    
    /**
     * Generates a FireParticle for the static graphics setting.
     * @return
     */
    public static FireParticle getStaticGraphicsParticle(){
        return new FireParticle(2, 2, -1){
            
            private final Color col = new Color(r, g, b);
            
            @Override
            public void update(int f){}
            
            @Override
            public void draw(Graphics2D gr, int focusX, int focusY, int f){
                gr.setColor(col);
                gr.fillRect(x+focusX, y+focusY, width, height);
            }
            
        };
    }
    
    /**
     * Generates a FireParticle for the low graphics setting.
     * @return
     */
    public static FireParticle getLowGraphicsParticle(){
        return new FireParticle(1+R.nextInt(2), 2, 0.05);
    }
    
    /**
     * Generates a FireParticle for the medium graphics setting.
     * @return
     */
    public static FireParticle getMediumGraphicsParticle(){
        return new FireParticle(1, 1, 0.1,   20, 1.0, p -> new TrailSpeck(p){
            
            @Override
            public void update(double frames){
                if(b<150) b+=2;
            }
        
        });
    }
    
    /**
     * Generates a FireParticle for the high graphics setting.
     * @return
     */
    public static FireParticle getHighGraphicsParticle(){
        return new FireParticle(1, 1, 0.05,   3, 3.0, p -> new TrailSpeck(p){
            
            @Override
            public void update(double frames){
                if(b<150) b+=2;
            }
        
        }){
        
            @Override
            public void update(int frames){
                motor(frames);
        
                double c = R.nextDouble();
                if(c<0.05) x--;
                else if(c<0.1) x++;

                if(g<120) g+=5;
                if(alpha>10) alpha-=2;
                expired = R.nextDouble() < (1.0 - (double)alpha/255.0)*0.1;
            }
            
        };
    }

}
