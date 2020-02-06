
package materials;

import biomes.Biome;
import biomes.Society;
import filterGeneration.DichromeFilter;
import filterGeneration.Filter;
import filterGeneration.ImageBuilder;
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
    
    
    public Material(String desc, Color col, double res, double comp, double mTemp, double minH, double maxH, 
                    boolean furn, boolean d, boolean fl, boolean wa){
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
    
    
    public boolean biomeCompatible(Biome b, Society s){
        return b.temperature<=maxTemp && b.hostility<=resilience && 
                minHeight <= b.height && b.height <= maxHeight && 
                s.technology>complexity;
    }
    
    protected final void setDefaultFilter(String filePath, int num){
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile("tiles/" +filePath + "/" + filePath + num + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 10, 4));
        filter.buildFilterImage();
    }
    
}
