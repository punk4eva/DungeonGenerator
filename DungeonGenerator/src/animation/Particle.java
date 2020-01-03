
package animation;

import animation.TrailGenerator.TrailSpeck;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Function;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Particle{
    
    
    protected int x, y, width, height;
    protected int r, g, b, alpha = 255;
    protected boolean expired = false;
    private final TrailGenerator trail;
    
    protected double velx, vely;
    private double dx = 0, dy = 0;
    
    
    public Particle(int w, int h, int r, int g, int b, int intensity, double fade, Function<Particle, TrailSpeck> tra){
        width = w;
        height = h;
        this.r = r;
        this.g = g;
        this.b = b;
        trail = new TrailGenerator(intensity, fade, tra);
    }
    
    public Particle(int w, int h, int r, int g, int b){
        width = w;
        height = h;
        this.r = r;
        this.g = g;
        this.b = b;
        trail = null;
    }
    
    
    public abstract void update(int frames);
    
    public abstract void draw(Graphics2D g, int foxusX, int focusY, int frames);
    
    
    protected void motor(int frames){
        dx += velx*frames;
        dy += vely*frames;
        if(Math.abs(dx)>=1){
            x += dx;
            dx %= 1.0;
        }
        if(Math.abs(dy)>=1){
            y += dy;
            dy %= 1.0;
        }
    }
    
    protected void defaultDraw(Graphics2D gr, int focusX, int focusY, int frames){
        gr.setColor(new Color(r, g, b, alpha));
        gr.fillRect(x+focusX, y+focusY, width, height);
        if(trail!=null) trail.draw(gr, focusX, focusY, frames, this);
    }

}
