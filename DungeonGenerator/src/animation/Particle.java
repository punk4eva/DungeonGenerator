
package animation;

import animation.TrailGenerator.TrailSpeck;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Function;

/**
 *
 * @author Adam Whittaker
 * Represents a particle, e.g: aerosol.
 */
public abstract class Particle{
    
    
    /**
     * x, y: The coordinates of the particle.
     * width, height: The dimensions of the particle.
     * r,g,b: The color of the particle.
     * alpha: The visibility of the particle.
     * expired: Whether the particle has finished animating.
     * trail: generates the trail effect of this particle.
     * velx, vely: The velocity of this particle.
     * dx, dy: The remainders of x and y, allowing for a velocity of double 
     * precision.
     */
    protected int x, y, width, height;
    protected int r, g, b, alpha = 255;
    protected boolean expired = false;
    private final TrailGenerator trail;
    
    protected double velx, vely;
    private double dx = 0, dy = 0;
    
    
    /**
     * Creates an instance.
     * @param w
     * @param h
     * @param r
     * @param g
     * @param b
     * @param intensity The intensity of the trail generator.
     * @param fade The fade speed of the trail.
     * @param tra The trail speck generator for the trail.
     */
    public Particle(int w, int h, int r, int g, int b, int intensity, double fade, Function<Particle, TrailSpeck> tra){
        this(w, h, r, g, b, new TrailGenerator(intensity, fade, tra));
    }
    
    /**
     * Creates an instance.
     * @param w
     * @param h
     * @param r
     * @param g
     * @param b
     */
    public Particle(int w, int h, int r, int g, int b){
        this(w, h, r, g, b, null);
    }
    
    /**
     * Creates an instance.
     * @param w
     * @param h
     * @param r
     * @param g
     * @param b
     * @param tr
     */
    public Particle(int w, int h, int r, int g, int b, TrailGenerator tr){
        width = w;
        height = h;
        this.r = r;
        this.g = g;
        this.b = b;
        trail = tr;
    }
    
    
    /**
     * Updates this particle (called once per render-tick).
     * @param frames
     */
    public abstract void update(int frames);
    
    /**
     * Draws this particle onto the given graphics.
     * @param g
     * @param foxusX
     * @param focusY
     * @param frames
     */
    public abstract void draw(Graphics2D g, int foxusX, int focusY, int frames);
    
    
    /**
     * Moves the particle.
     * @param frames
     */
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
    
    /**
     * Draws the particle as a rectangle. For use with child classes.
     * @param gr
     * @param focusX
     * @param focusY
     * @param frames
     */
    protected void defaultDraw(Graphics2D gr, int focusX, int focusY, int frames){
        gr.setColor(new Color(r, g, b, alpha));
        gr.fillRect(x+focusX, y+focusY, width, height);
        if(trail!=null) trail.draw(gr, focusX, focusY, frames, this);
    }

}
