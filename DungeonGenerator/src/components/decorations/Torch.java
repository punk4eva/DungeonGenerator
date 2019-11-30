
package components.decorations;

import components.mementoes.AreaInfo;
import filterGeneration.Filter;
import filterGeneration.ImageBuilder;
import static filterGeneration.ImageBuilder.colorToPixelArray;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author Adam Whittaker
 */
public class Torch extends Decoration implements WallDecoration{

    
    private final static int[] LIGHT_REGEX = {243, 115, 240},
            MEDIUM_REGEX = {215, 119, 223},
            DARK_REGEX = {199, 110, 210};
    
    private final int[] light, medium, dark;
    
    
    public Torch(AreaInfo info){
        super("torch", "A simple torch hanging from the wall.", false);
        medium = colorToPixelArray(info.architecture.furnitureMaterial.color, true);
        light = colorToPixelArray(info.architecture.furnitureMaterial.color.brighter(), true);
        dark = colorToPixelArray(info.architecture.furnitureMaterial.color.darker(), true);
    }

    
    @Override
    public void drawImage(Graphics2D g, int _x, int _y){
        BufferedImage torch = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = ImageBuilder.getImageFromFile("torch.png").getRaster(),
                torchRaster = torch.getRaster();
        int[] pixel = new int[4];
        int alpha;
        
        for(int y=0;y<16;y++) for(int x=0;x<16;x++){
            pixel = raster.getPixel(x, y, pixel);
            alpha = pixel[3];
            if(Filter.RGBPixelEquals(pixel, LIGHT_REGEX)){
                light[3] = alpha;
                torchRaster.setPixel(x, y, light);
            }else if(Filter.RGBPixelEquals(pixel, MEDIUM_REGEX)){
                medium[3] = alpha;
                torchRaster.setPixel(x, y, medium);
            }else if(Filter.RGBPixelEquals(pixel, DARK_REGEX)){
                dark[3] = alpha;
                torchRaster.setPixel(x, y, dark);
            }
        }
        
        g.drawImage(torch, _x, _y, null);
    }

}
