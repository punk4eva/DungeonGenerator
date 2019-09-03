
package materials;

import java.awt.Color;
import materials.composite.*;
import materials.improvised.*;
import materials.stone.*;
import materials.wood.*;

/**
 *
 * @author Adam Whittaker
 */
public class Material{
    
    public final String description;
    public final Color color;
    
    public final double resilience;
    public final double complexity;
    public final double maxTemp;
    public final double minHeight, maxHeight;
    
    
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
