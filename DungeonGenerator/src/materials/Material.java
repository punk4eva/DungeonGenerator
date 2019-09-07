
package materials;

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
    
    public Filter filter;
    
    
    public Material(String desc, Color col, double res, double comp, double mTemp, double minH, double maxH){
        description = desc;
        resilience = res;
        complexity = comp;
        color = col;
        maxTemp = mTemp;
        minHeight = minH;
        maxHeight = maxH;
    }
    
}
