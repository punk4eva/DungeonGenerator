
package textureGeneration;

import java.awt.Color;
import java.awt.image.BufferedImage;
import textureGeneration.ImageBuilder.SerSupplier;

/**
 * This class represents a list of commands to generate an image.
 * @author Adam Whittaker
 */
public abstract class Texture extends SerImage{

    
    private static final long serialVersionUID = 427675489602L;
    
    
    /**
     * Creates an instance.
     * @param s The supplier of the initial image.
     */
    public Texture(SerSupplier s){
        super(s);
    }
    
    
    /**
     * Generates the tile image from the internal filter image.
     * @param _x The pixel x of the top left of the tile. (tile x * 16)
     * @param _y The pixel y of the top left of the tile. (tile y * 16)
     * @param map The noise map.
     * @return
     */
    public abstract SerImage generateImage(int _x, int _y, double[][] map);
    
    
    /**
     * A default method to act as the supplier for the filter.
     * @return A blank 16x16 ARGB buffered image.
     */
    public static BufferedImage defaultSupplier(){
        return new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }
    
    /**
     * Overlays the color of the target pixel with the color of the "col" pixel.
     * @param target The target pixel (RGB)
     * @param col The color to overlay onto the target (ARGB).
     * @return The modified target array.
     */
    public static int[] overlayColor(int[] target, int[] col){
        target[0] = getChannelAverage(target[0], col[1], (double)col[0]/255D);
        target[1] = getChannelAverage(target[1], col[2], (double)col[0]/255D);
        target[2] = getChannelAverage(target[2], col[3], (double)col[0]/255D);
        return target;
    }
    
    /**
     * Averages two colors based on a given weight (RGB only).
     * @param pixel The int array for the color to be stored in.
     * @param c1 The first color.
     * @param c2 The second color.
     * @param weight The relative weight of the colors 
     * (0 = color 1, 255 = color 2)
     * @return The pixel int array representing the averaged color.
     */
    public static int[] getColorAverage(int[] pixel, Color c1, Color c2, double weight){
        pixel[0] = getChannelAverage(c1.getRed(), c2.getRed(), weight/255);
        pixel[1] = getChannelAverage(c1.getGreen(), c2.getGreen(), weight/255);
        pixel[2] = getChannelAverage(c1.getBlue(), c2.getBlue(), weight/255);
        return pixel;
    }
    
    /**
     * Returns the value for an individual RGB channel during color averaging.
     * @param v1 the value of the channel in color 1.
     * @param v2 the value of the channel in color 2.
     * @param weight the weight of color 1 in the average (0 -> 1).
     */
    private static int getChannelAverage(double v1, double v2, double weight){
        return (int)(v1*(1D-weight) + v2*weight);
    }
    
    /**
     * Compares the first 3 color channels of a pixel for equality.
     * Compatible only with RGB and not ARGB pixels.
     * @param p1 pixel 1
     * @param p2 pixel 2
     * @return true if the pixels are the same color.
     */
    public static boolean rgbPixelEquals(int[] p1, int[] p2){
        for(int n=0;n<3;n++) if(p1[n] != p2[n]) return false;
        return true;
    }
    
    /**
     * Compares the last 3 color channels of a pixel for equality.
     * Compatible only with ARGB and not RGB pixels.
     * @param p1 pixel 1
     * @param p2 pixel 2
     * @return true if the pixels are the same color.
     */
    public static boolean ARGBPixelEquals(int[] p1, int[] p2){
        for(int n=1;n<4;n++) if(p1[n] != p2[n]) return false;
        return true;
    }
    
}
