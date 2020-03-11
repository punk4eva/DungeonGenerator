
package components.decorations.assets;

import animation.Animation;
import components.decorations.AnimatedDecoration;
import components.decorations.ComplexDecoration;
import components.mementoes.AreaInfo;
import static gui.pages.DungeonScreen.getSettings;
import java.awt.image.BufferedImage;
import textureGeneration.ImageBuilder;
import static textureGeneration.ImageBuilder.colorToPixelArray;
import textureGeneration.ImageRecolorer;

/**
 *
 * @author Adam Whittaker
 */
public class Torch extends ComplexDecoration implements AnimatedDecoration{

    
    private static final long serialVersionUID = 1589745L;
    
    private final int[][] palette;
    
    private final static ImageRecolorer RECOLORER = new ImageRecolorer(10, new int[][]{
            {243, 115, 240},
            {215, 119, 223},
            {199, 110, 210}
    });
    
    
    public Torch(AreaInfo info){
        super("torch", "A simple torch hanging from the wall.", false);
        palette = new int[][]{colorToPixelArray(info.architecture.furnitureMaterial.color.brighter(), true),
            colorToPixelArray(info.architecture.furnitureMaterial.color, true),
            colorToPixelArray(info.architecture.furnitureMaterial.color.darker(), true)};
    }
    

    @Override
    public Animation createAnimation(int x, int y){
        return getSettings().getTorchAnimation(x*16, y*16);
    }
    
    
    @Override
    public void accept(BufferedImage t){
        BufferedImage torch = ImageBuilder.getImageFromFile("tiles/torch.png");
        RECOLORER.recolor(torch, palette);
        t.getGraphics().drawImage(torch, 0, 0, null);
    }

}
