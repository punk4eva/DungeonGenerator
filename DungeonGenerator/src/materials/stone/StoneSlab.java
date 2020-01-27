
package materials.stone;

import filterGeneration.DichromeFilter;
import filterGeneration.Filter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import java.util.function.BiFunction;
import materials.Material;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class StoneSlab extends Material{
    
    
    private static final BiFunction<Integer, Integer, Double> PROBABILITY_FUNCTION = 
            (x, y) -> (14D - (double)( min(min(abs(x-7), abs(x-8)), min(abs(y-7), abs(y-8)))))/14D;

    
    public StoneSlab(){
        super("@Unfinished", new Color(130, 130, 130), 55, 30, 400, -30, 60, true, false, true, true);
        filter = new DichromeFilter(Filter::defaultSupplier, color);
        filter.addInstruction(img -> paintSlabs(img, 12));
        filter.buildFilterImage();
    }
    
    
    private static void paintSlabs(BufferedImage img, int avgAlpha){
        WritableRaster raster = img.getRaster();
        int[] pixel = new int[4];
        double prob;
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                pixel = raster.getPixel(x, y, pixel);
                prob = PROBABILITY_FUNCTION.apply(x, y);
                if(R.nextDouble()<pow(prob, 12)){
                    pixel[3] = (int)(2D*avgAlpha*(pow(prob, 2)+0.03)*R.nextDouble());
                }
                raster.setPixel(x, y, pixel);
            }
        }
    }

}
