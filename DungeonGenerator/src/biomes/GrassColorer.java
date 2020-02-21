
package biomes;

import java.awt.Color;
import static utils.Utils.indexAverage;
import static utils.Utils.interpolate;
import static utils.Utils.triangulate;

/**
 *
 * @author Adam Whittaker
 */
public final class GrassColorer{

    
    private GrassColorer(){}
    
    
    public static Color getGrassColor(Biome biome){
        int[] c0 = tempColor(biome.temperature), 
                c1 = accColor(biome.accommodation), 
                c2 = heightColor(biome.height);
        return new Color(indexAverage(0, c0,c1,c2), indexAverage(1, c0,c1,c2), 
                indexAverage(2, c0,c1,c2));
    }
    
    private static int[] tempColor(double temp){
        return new int[]{
            (int)triangulate(temp,  -80, 0, 80,  208, 22, 174),
            (int)triangulate(temp,  -80, 0, 80,  230, 86, 167),
            (int)triangulate(temp,  -80, 0, 80,  228, 13, 10)
        };
    }
    
    private static int[] accColor(double acc){
        return new int[]{
            (int)interpolate(92, 40, acc/100D),
            (int)interpolate(96, 80, acc/100D),
            (int)interpolate(88, 40, acc/100D)
        };
    }
    
    private static int[] heightColor(double height){
        return new int[]{
            (int)triangulate(height,  -100, 0, 100,  210, 22, 205),
            (int)triangulate(height,  -100, 0, 100,  210, 86, 209),
            (int)triangulate(height,  -100, 0, 100,  210, 13, 191)
        };
    }
    
}
