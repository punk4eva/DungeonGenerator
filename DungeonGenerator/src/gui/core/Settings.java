
package gui.core;

import animation.ParticleGenerator;
import animation.assets.FireParticle;

/**
 *
 * @author Adam Whittaker
 */
public class Settings{

    public enum GraphicsQuality{
        STATIC(0, "Static", "No animations"), LOW(1, "Low", "Optimized for fast generation."),
        MEDIUM(2, "Medium", "Compromises graphics quality on less apparent features it decrease load time."),
        HIGH(3, "High", "Compromises load time for highest quality graphics, even on less apparent features.");
        
        public final int value;
        public final String name, description;
        
        
        GraphicsQuality(int val, String n, String desc){
            value = val;
            name = n;
            description = desc;
        }
        
    }
    
    
    public GraphicsQuality GRAPHICS = GraphicsQuality.MEDIUM;
    
    
    public ParticleGenerator getTorchAnimation(int x, int y){
        switch(GRAPHICS){
            case STATIC: return new ParticleGenerator(FireParticle::getStaticGraphicsParticle, () -> x+6, () -> y+5, () -> Integer.MAX_VALUE);
            case LOW: return new ParticleGenerator(5, FireParticle::getLowGraphicsParticle, x+6, y+5, 4, 2);
            case MEDIUM: return new ParticleGenerator(2, FireParticle::getMediumGraphicsParticle, x+6, y+5, 4, 2);
            case HIGH: return null;
            default: throw new IllegalStateException();
        }
    }
    
}
