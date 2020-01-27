
package filterGeneration;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import static filterGeneration.Filter.rgbPixelEquals;
import static utils.Utils.R;

/**
 * This class is designed to replace all instances of specified placeholder 
 * colors in an image with given replacement colors.
 * @author Adam Whittaker
 */
public class ImageRecolorer{

    
    /**
     * reject: The array of colors (represented as int arrays) to be replaced.
     * jitter: The 0-255 scale for random adjustment of the injected pixel.
     */
    private final int[][] reject;
    private final int jitter;
    
    
    /**
     * Creates an instance.
     * @param j The 0-255 scale for random adjustment of the injected pixel.
     * @param rej The list of colors (represented as pixel arrays) to be 
     * replaced.
     */
    public ImageRecolorer(int j, int[][] rej){
        reject = rej;
        jitter = j;
    }
    
    
    /**
     * Re-colors an image.
     * @param img The image.
     * @param replace The array of pixels to replace with (In same order as they
     * were declared in the constructor).
     */
    public void recolor(BufferedImage img, int[][] replace){
        WritableRaster raster = img.getRaster();
        int[] pixel = new int[4];
        int alpha, r, g, b;
        
        for(int y=0;y<img.getHeight();y++) for(int x=0;x<img.getWidth();x++){
            pixel = raster.getPixel(x, y, pixel);
            alpha = pixel[3];
            for(int n=0;n<reject.length;n++) if(rgbPixelEquals(reject[n], pixel)){
                replace[n][3] = alpha;
                r = replace[n][0];
                g = replace[n][1];
                b = replace[n][2];
                applyJitter(replace[n]);
                raster.setPixel(x, y, replace[n]);
                replace[n][0] = r;
                replace[n][1] = g;
                replace[n][2] = b;
                break;
            }
        }
    }
    
    /**
     * Randomly perturbs the given pixel by the jitter.
     */
    private void applyJitter(int[] pix){
        for(int n=0;n<3;n++){
            pix[n] += R.nextInt(2*jitter+1) - jitter;
            if(pix[n]>255) pix[n] = 255;
            else if(pix[n]<0) pix[n] = 0;
        }
    }
    
}
