
package components.decorations;

import components.mementoes.AreaInfo;
import filterGeneration.ImageBuilder;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import static utils.Utils.R;

/**
 *
 * @author Adam Whittaker
 */
public class Pot extends Decoration{
    
    
    private final int[] color;
    
    private final static int COLOR_VARIANCE = 49;

    
    public Pot(AreaInfo info){
        this(info, getRandomPotColor());
    }
    
    public Pot(AreaInfo info, int[] col){
        super("Pot", "@Unfinished");
        color = col;
    }

    
    @Override
    public void drawImage(Graphics2D g, int _x, int _y){
        BufferedImage img = ImageBuilder.getImageFromFile("pots/pot0.png");
        WritableRaster raster = img.getRaster();
        int[] pixel = new int[4];
        
        for(int y=0;y<16;y++){
            for(int x=0;x<16;x++){
                raster.getPixel(x, y, pixel);
                for(int n=0;n<3;n++) pixel[n] = color[n] * pixel[n]/255;
                raster.setPixel(x, y, pixel);
            }
        }
        
        g.drawImage(img, _x, _y, null);
    }
    
    
    public static int[] getRandomPotColor(){
        return new int[]{150 + R.nextInt(COLOR_VARIANCE+1)-COLOR_VARIANCE, 
                100 + R.nextInt(COLOR_VARIANCE+1)-COLOR_VARIANCE,
                50 + R.nextInt(COLOR_VARIANCE+1)-COLOR_VARIANCE};
        //For debugging vibrant colors.
        /*return new int[]{150 + (R.nextDouble()<0.5 ? COLOR_VARIANCE : -COLOR_VARIANCE),
                100 + (R.nextDouble()<0.5 ? COLOR_VARIANCE : -COLOR_VARIANCE),
                50 + (R.nextDouble()<0.5 ? COLOR_VARIANCE : -COLOR_VARIANCE)};*/
    }

}
