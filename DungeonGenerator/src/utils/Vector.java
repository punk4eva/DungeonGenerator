
package utils;

import java.util.Random;

/**
 *
 * @author Adam Whittaker
 */
public class Vector{

    public final double[] v;
    
    public Vector(Random r){
        v = new double[]{r.nextDouble()*2-1, r.nextDouble()*2-1};
        double len = Math.sqrt(v[0]*v[0] + v[1]*v[1]);
        v[0] /= len;
        v[1] /= len;
    }
    
}
