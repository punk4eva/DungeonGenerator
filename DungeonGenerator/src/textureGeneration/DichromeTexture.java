
package textureGeneration;

import static gui.pages.DungeonScreen.getSettings;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import textureGeneration.ImageBuilder.SerSupplier;

/**
 * A filter that combines two colors together based on the underlying noise.
 * @author Adam Whittaker
 */
public class DichromeTexture extends Texture{

    
    private static final long serialVersionUID = 15389L;
    
    /**
     * col1: One color.
     * col2: Another color.
     */
    private final Color col1, col2;
    
    
    /**
     * Creates a new instance.
     * @param s The image supplier of the filter.
     * @param c1 The one color.
     * @param c2 The second color.
     */
    public DichromeTexture(SerSupplier s, Color c1, Color c2){
        super(s);
        col1 = c1;
        col2 = c2;
    }
    
    /**
     * Creates an instance based around shades of one color.
     * @param s The image supplier of the filter.
     * @param color The color.
     */
    public DichromeTexture(SerSupplier s, Color color){
        this(s, color.brighter(), color.darker());
    }
    

    @Override
    public SerImage generateImage(int _x, int _y, double[][] map){
        SerImage ret = new SerImage(() -> new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB));
        ret.addInstruction(img -> applyFilter(img, _x, _y, map));
        return ret;
    }
    
    private void applyFilter(BufferedImage img, int _x, int _y, double[][] map){
        if(getSettings().GRAPHICS.code>=3) buildImage();
        WritableRaster raster = img.getRaster(), filterRaster = image.getAlphaRaster();
        int[] pixel = new int[3], filterPixel = new int[4];
        for(int y=0;y<img.getHeight();y++){
            for(int x=0;x<img.getWidth();x++){
                raster.setPixel(x, y, 
                        overlayColor(getColorAverage(pixel, col1, col2, map[_y+y][_x+x]),
                                filterRaster.getPixel(x, y, filterPixel)));
            }
        }
    }

}
