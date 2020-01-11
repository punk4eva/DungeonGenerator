
package materials;

import biomes.Biome;
import filterGeneration.Filter;
import java.awt.Color;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Material{
    
    
    public final String description;
    public final Color color;
    
    public final double resilience;
    public final double complexity;
    public final double maxTemp;
    public final double minHeight, maxHeight;
    
    public final boolean furniture, door, floor, wall;
    
    public Filter filter;
    
    
    public Material(String desc, Color col, double res, double comp, double mTemp, double minH, double maxH, boolean furn, boolean d, boolean fl, boolean wa){
        description = desc;
        color = col;
        furniture = furn;
        door = d;
        floor = fl;
        wall = wa;
        resilience = res;
        complexity = comp;
        maxTemp = mTemp;
        minHeight = minH;
        maxHeight = maxH;
    }
    
    
    public boolean biomeCompatible(Biome b, int s){
        return b.temperature<=maxTemp && b.hostility<=resilience && 
                minHeight <= b.height && b.height <= maxHeight && s>complexity;
    }
    
}
