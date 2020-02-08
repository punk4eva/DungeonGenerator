
package materials.stone;

import textureGeneration.DichromeTexture;
import textureGeneration.Texture;
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
 * Represents slabs of stone (as may be found at the bottom of cliffs in scree).
 * @author Adam Whittaker
 */
public class StoneSlab extends Material{
    
    
    /**
     * The function affecting the probability of a particular pixel in the stone
     * slab icon to be shaded.
     */
    private static final BiFunction<Integer, Integer, Double> PROBABILITY_FUNCTION = 
            (x, y) -> (14D - (double)( min(min(abs(x-7), abs(x-8)), min(abs(y-7), abs(y-8)))))/14D;

    
    /**
     * Creates an instance.
     */
    public StoneSlab(){
        super("@Unfinished", new Color(130, 130, 130), 55, 30, 400, -30, 60, true, false, true, true);
        texture = new DichromeTexture(Texture::defaultSupplier, color);
        texture.addInstruction(img -> paintSlabs(img, 12));
        texture.buildFilterImage();
    }
    
    
    /**
     * Creates the texture for the slab image.
     * @param img The texture image.
     * @param avgAlpha The average alpha perturbation for the texture image.
     */
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
