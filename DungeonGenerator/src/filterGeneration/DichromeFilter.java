
package filterGeneration;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import utils.Utils;

/**
 *
 * @author Adam Whittaker
 */
public class DichromeFilter extends Filter{
    
    private final Color col1, col2;
    
    public DichromeFilter(Color c1, Color c2){
        col1 = c1;
        col2 = c2;
    }
    

    @Override
    public BufferedImage generateImage(int _x, int _y, double[][] map){
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = img.getRaster(), filterRaster = filter.getAlphaRaster();
        int[] pixel = new int[3], filterPixel = new int[4];
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                raster.setPixel(x, y, 
                        overlayColor(getColorAverage(pixel, col1, col2, map[_y+y][_x+x]),
                                filterRaster.getPixel(x, y, filterPixel)));
            }
        }
        return img;
    }

}
