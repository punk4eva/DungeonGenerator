
package utils.test;

import graph.Point;
import java.util.function.BiFunction;

/**
 * A debug class to convert a given point into a color for output to an image.
 * @author Adam Whittaker
 */
public class DungeonColorer implements BiFunction<Point, int[], int[]>{

    //Colors for perlin noise.
    /*@Override
    public int[] apply(Point p, int[] pix) {
        pix[0] = (int)(2D*p.value);
        pix[1] = (int)(2D*p.value);
        pix[2] = (int)(2D*p.value);
        return pix;
    }*/
    
    //Colors for midpoint displacement.
    /*@Override
    public int[] apply(Point p, int[] pix){
        pix[0] = (int) p.value;
        pix[1] = (int) p.value;
        pix[2] = (int) p.value;
        return pix;
    }*/
    
    //Colors for room demonstration.
    @Override
    public int[] apply(Point p, int[] pix) {
        pix[0] = p.isCorridor ? 100 : 0;
        pix[1] = p.isCorridor ? 0 : 255;
        pix[2] = p.isCorridor ? 0 : 255;
        return pix;
    }

}
