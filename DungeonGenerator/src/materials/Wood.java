
package materials;

import biomes.Biome;
import java.awt.Color;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Wood{
    
    
    public final String description;
    public final Color color;
    
    public final double resilience;
    public final double maxTemp;
    public final double minHeight, maxHeight;
    
    
    public Wood(String desc, Color col, double res, double maxT, double minH, double maxH){
        description = desc;
        color = col;
        resilience = res;
        maxTemp = maxT;
        minHeight = minH;
        maxHeight = maxH;
    }
    
    
    public boolean biomeCompatible(Biome b){
        return b.temperature<=maxTemp && b.hostility<=resilience && 
                minHeight <= b.height && b.height <= maxHeight;
    }

}
