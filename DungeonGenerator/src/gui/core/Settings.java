
package gui.core;

import animation.ParticleGenerator;
import animation.assets.FireParticle;

/**
 * The general settings of the program.
 * @author Adam Whittaker
 */
public class Settings{

    
    /**
     * The possible graphics quality settings.
     */
    public enum GraphicsQuality{
        STATIC(0, "Static", "No animations"), LOW(1, "Low", "Optimized for fast generation."),
        MEDIUM(2, "Medium", "Compromises graphics quality on less apparent features it decrease load time."),
        HIGH(3, "High", "Compromises load time for highest quality graphics, even on less apparent features.");
        
        /**
         * code: The int code of the setting (0 for static, 3 for high).
         * name: The name of the setting.
         * description: The description of the setting.
         */
        public final int code;
        public final String name, description;
        
        /**
         * Creates a new instance.
         * @param val The int setting code
         * @param n The name
         * @param desc The description
         */
        GraphicsQuality(int val, String n, String desc){
            code = val;
            name = n;
            description = desc;
        }
        
    }
    
    
    /**
     * GRAPHICS: The current graphics quality setting.
     * WATER_DELAY: The delay between each frame of the water animation.
     */
    public GraphicsQuality GRAPHICS = GraphicsQuality.HIGH;
    public int WATER_DELAY = (4-GRAPHICS.code) * 80;
    public boolean DM_MODE = false;
    
    
    /**
     * Gets an animation for a torch based on the graphics setting for the given
     * coordinates.
     * @param x The tile x.
     * @param y The tile y.
     * @return A ParticleGenerator.
     */
    public ParticleGenerator getTorchAnimation(int x, int y){
        switch(GRAPHICS){
            case STATIC: return new ParticleGenerator(FireParticle::getStaticGraphicsParticle, () -> x+6, () -> y+5, () -> Integer.MAX_VALUE);
            case LOW: return new ParticleGenerator(5, FireParticle::getLowGraphicsParticle, x+6, y+5, 4, 2);
            case MEDIUM: return new ParticleGenerator(2, FireParticle::getMediumGraphicsParticle, x+6, y+5, 4, 2);
            case HIGH: return new ParticleGenerator(10, FireParticle::getHighGraphicsParticle, x+7, y+5, 2, 2);
            default: throw new IllegalStateException();
        }
    }
    
}
