
package utils;

import static utils.Utils.R;

/**
 * A randomized 2D double vector.
 * @author Adam Whittaker
 */
public class Vector{

    
    /**
     * v: The values of the vector in double format.
     */
    public final double[] v;
    
    
    /**
     * Creates a randomized new instance with unit magnitude.
     */
    public Vector(){
        v = new double[]{R.nextDouble()*2-1D, R.nextDouble()*2-1D};
        double len = Math.sqrt(v[0]*v[0] + v[1]*v[1]);
        v[0] /= len;
        v[1] /= len;
    }
    
}
