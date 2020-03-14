
package animation.assets;

import animation.Particle;
import animation.TrailGenerator.TrailSpeck;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Function;
import static utils.Utils.R;

/**
 * Models a particle of soot coming for a fire.
 * @author Adam Whittaker
 */
public class FireParticle extends Particle{

    
    /**
     * Creates a simple FireParticle by auto-initializing some fields.
     * @param w The width
     * @param h The height
     */
    public FireParticle(int w, int h){
        super(w, h, 240, 50, 0);
        vely = -(R.nextDouble()*0.1+0.05);
        velx = 0;
    }
    
    /**
     * Creates a FireParticle with a trail.
     * @param w The width
     * @param h The height
     * @param intensity The intensity of the trail generator.
     * @param trailFade The fade speed of the trail.
     * @param tr The trail speck generator for the trail.
     */
    public FireParticle(int w, int h, int intensity, double trailFade, Function<Particle, TrailSpeck> tr){
        super(w, h, 240, 50, 0, intensity, trailFade, tr);
        vely = -(R.nextDouble()*0.1+0.05);
        velx = 0;
    }

    
    @Override
    public void update(int frames){
        motor(frames);
        
        //Randomly perturb x coordinate.
        double c = R.nextDouble();
        if(c<0.05) x--;
        else if(c<0.1) x++;
        
        //Make yellower and decrease visibility.
        if(g<120) g+=5;
        if(alpha>5) alpha-=3;
        else expired = true;
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
        return new FireParticle(2, 2){
            
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
        return new FireParticle(1+R.nextInt(2), 2);
    }
    
    /**
     * Generates a FireParticle for the medium graphics setting.
     * @return
     */
    public static FireParticle getMediumGraphicsParticle(){
        return new FireParticle(1, 1,  20, 1.0, p -> new TrailSpeck(p){
            
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
        return new FireParticle(1, 1,  1, 3.0, p -> new TrailSpeck(p){
            
            @Override
            public void update(double frames){
                if(b<150) b+=1;
            }
        
        }){
        
            @Override
            public void update(int frames){
                motor(frames);
        
                double c = R.nextDouble();
                if(c<0.03) x--;
                else if(c<0.06) x++;

                if(g<120) g+=5;
                if(alpha>10) alpha-=6;
                else expired = true;
            }
            
        };
    }

}
