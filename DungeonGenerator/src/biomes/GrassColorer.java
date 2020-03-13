
package biomes;

import java.awt.Color;
import static utils.Utils.indexAverage;
import static utils.Utils.interpolate;
import static utils.Utils.triangulate;

/**
 * Gets the color of grass based on the biome.
 * @author Adam Whittaker
 */
public final class GrassColorer{

    
    /**
     * A private constructor combined with the final modifier in the class
     * declaration represents the "singleton" design pattern, preventing the 
     * non-instantiable class from being instantiated.
     */
    private GrassColorer(){}
    
    
    /**
     * Gets the color of the grass by averaging 3 colors based on the 
     * temperature, accommodation and height.
     * @param biome The biome.
     * @return
     */
    public static Color getGrassColor(Biome biome){
        int[] c0 = tempColor(biome.temperature), 
                c1 = accColor(biome.accommodation), 
                c2 = heightColor(biome.height);
        return new Color(indexAverage(0, c0,c1,c2), indexAverage(1, c0,c1,c2), 
                indexAverage(2, c0,c1,c2));
    }
    
    /**
     * Gets the color of the grass according to the temperature.
     * @param temp
     * @return
     */
    private static int[] tempColor(double temp){
        return new int[]{
            (int)triangulate(temp,  -80, 0, 80,  208, 22, 174),
            (int)triangulate(temp,  -80, 0, 80,  230, 86, 167),
            (int)triangulate(temp,  -80, 0, 80,  228, 13, 10)
        };
    }
    
    /**
     * Gets the color of the grass according to the accommodation.
     * @param acc
     * @return
     */
    private static int[] accColor(double acc){
        return new int[]{
            (int)interpolate(92, 40, acc/100D),
            (int)interpolate(96, 80, acc/100D),
            (int)interpolate(88, 40, acc/100D)
        };
    }
    
    /**
     * Gets the color of the grass according to the height.
     * @param acc
     * @return
     */
    private static int[] heightColor(double height){
        return new int[]{
            (int)triangulate(height,  -100, 0, 100,  210, 22, 205),
            (int)triangulate(height,  -100, 0, 100,  210, 86, 209),
            (int)triangulate(height,  -100, 0, 100,  210, 13, 191)
        };
    }
    
}
