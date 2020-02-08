
package materials;

import biomes.Biome;
import biomes.Society;
import java.awt.Color;

/**
 * An abstraction of real-life wood.
 * @author Adam Whittaker
 */
public abstract class Wood{
    
    
    /**
     * description: The description of this wood.
     * color: The color of the wood.
     * resilience: The maximum hostility this wood can tolerate.
     * maxTemp: The maximum temperature this wood can tolerate.
     * minHeight, maxHeight: The altitude range where this wood is found.
     */
    public final String description;
    public final Color color;
    
    public final double resilience;
    public final double maxTemp;
    public final double minHeight, maxHeight;
    
    
    /**
     * Creates an instance by setting the fields in order of declaration.
     * @param desc
     * @param col
     * @param res
     * @param maxT
     * @param minH
     * @param maxH
     */
    public Wood(String desc, Color col, double res, double maxT, double minH, double maxH){
        description = desc;
        color = col;
        resilience = res;
        maxTemp = maxT;
        minHeight = minH;
        maxHeight = maxH;
    }
    
    
    /**
     * Checks whether the wood is compatible with the given biome and usable by
     * the given society.
     * @param b The biome.
     * @param s The society.
     * @return
     */
    public boolean biomeCompatible(Biome b, Society s){
        return b.temperature<=maxTemp && b.hostility<=resilience && 
                minHeight <= b.height && b.height <= maxHeight && 
                s.technology>20;
    }

}
