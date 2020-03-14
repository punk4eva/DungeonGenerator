
package components.decorations.assets;

import components.decorations.ComplexDecoration;
import components.mementoes.AreaInfo;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import textureGeneration.ImageBuilder;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 * A decorative pot.
 */
public class Pot extends ComplexDecoration{

    
    private static final long serialVersionUID = 14532L;
    
    //The variation in the colors of the pots.
    private final static int COLOR_VARIANCE = 49;
    
    /**
     * color: The r,g,b color info of the pot.
     */
    private final int[] color;

    
    /**
     * Creates a new Pot for the given Area.
     * @param info The Area's information.
     */
    public Pot(AreaInfo info){
        this(info, getRandomPotColor());
    }
    
    /**
     * Creates a new Pot for the given Area.
     * @param info the Area information.
     * @param col The color of the pot.
     */
    public Pot(AreaInfo info, int[] col){
        super("Pot", "@Unfinished", true);
        color = col;
    }
    
    
    /**
     * Gets a random color for a Pot.
     * @return
     */
    public static int[] getRandomPotColor(){
        return new int[]{150 + R.nextInt(COLOR_VARIANCE+1)-COLOR_VARIANCE, 
                100 + R.nextInt(COLOR_VARIANCE+1)-COLOR_VARIANCE,
                50 + R.nextInt(COLOR_VARIANCE+1)-COLOR_VARIANCE};
    }
    
    
    @Override
    public void accept(BufferedImage t){
        //Prepares the uncolored pot image.
        BufferedImage img = ImageBuilder.getImageFromFile("tiles/pots/pot0.png");
        WritableRaster raster = img.getRaster();
        int[] pixel = new int[4];

        //Loops through all the pixels and converts them to color from 
        //greyscale.
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                raster.getPixel(x, y, pixel);
                for(int n=0;n<3;n++) pixel[n] = color[n] * pixel[n]/255;
                raster.setPixel(x, y, pixel);
            }
        }

        //Draws the image to the given receptor buffered image.
        t.getGraphics().drawImage(img, 0, 0, null);
    }

}
