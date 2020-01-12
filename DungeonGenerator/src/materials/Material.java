
package materials;

import biomes.Biome;
import filterGeneration.DichromeFilter;
import filterGeneration.Filter;
import filterGeneration.ImageBuilder;
import java.awt.Color;
import static utils.Utils.R;

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
    
    protected final void setDefaultFilter(String filePath, int numPngs){
        filter = new DichromeFilter(() -> ImageBuilder.getImageFromFile(filePath + "/" + filePath + R.nextInt(numPngs) + ".png"), color);
        filter.addInstruction(img -> ImageBuilder.applyAlphaNoise(img, 10, 4));
        filter.buildFilter();
    }
    
}
